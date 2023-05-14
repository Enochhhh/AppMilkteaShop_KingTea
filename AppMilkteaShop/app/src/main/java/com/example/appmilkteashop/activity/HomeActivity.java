package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.CategoryAdapter;
import com.example.appmilkteashop.adapter.SpecialAdapter;
import com.example.appmilkteashop.databinding.ActivityHomeBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class HomeActivity extends AppCompatActivity {
    private ImageView imvAva;
    private ImageView imvBanner;
    private ActivityHomeBinding activityHomeBinding;
    private SpecialAdapter specialAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setHome(this);
        Mapping();

        // Round corner image Banner
        Glide.with(this)
                .load(R.drawable.milktea_banner)
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(80)))
                .into(imvBanner);

        loadProfileUser(activityHomeBinding);

        loadDataForRecyclerViewCategory();
        loadDataForRecyclerViewSpecial();
    }

    private void loadDataForRecyclerViewSpecial() {
        activityHomeBinding.rcViewSpecial.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetSpecial("Bearer " + token);
    }

    private void callApiGetSpecial(String token) {
        ApiHelper.apiService.getMilkteaSpecial(token, "Đặc Biệt").enqueue(new Callback<List<Milktea>>() {
            @Override
            public void onResponse(Call<List<Milktea>> call, Response<List<Milktea>> response) {
                if (response.isSuccessful()) {
                    List<Milktea> milkteaList = response.body();
                    specialAdapter = new SpecialAdapter(milkteaList, new ChangeNumberItemListener() {
                        @Override
                        public void change(boolean isPlus, Milktea milktea) {
                            callApiUpdateToCart(token, milktea, isPlus);
                        }
                    });
                    activityHomeBinding.rcViewSpecial.setAdapter(specialAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(HomeActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(HomeActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Milktea>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Find category unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiUpdateToCart(String token, Milktea milktea, boolean isPlus) {
        if (isPlus) {
            CustomMilkteaDto customMilkteaDto = new CustomMilkteaDto();
            customMilkteaDto.setCustomMilkteaId(milktea.getMilkTeaId());
            customMilkteaDto.setSize("M");
            customMilkteaDto.setSugarAmount("100%");
            customMilkteaDto.setIceAmount("100%");
            customMilkteaDto.setEnabled(true);
            customMilkteaDto.setListTopping(new ArrayList<String>());
            customMilkteaDto.setQuantity(1);
            customMilkteaDto.setMilkTeaId(milktea.getMilkTeaId());

            ApiHelper.apiService.addMilkteaToCart(token, customMilkteaDto).enqueue(new Callback<ResponseStringDto>() {
                @Override
                public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                    if (response.isSuccessful()) {
                        ResponseStringDto stringDto = response.body();
                        Toast.makeText(HomeActivity.this, stringDto.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                        if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                            startActivity(new Intent(HomeActivity.this, ExceptionActivity.class));
                        }
                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Add milktea to cart unsuccessfully",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void loadDataForRecyclerViewCategory() {
        activityHomeBinding.rcViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetCategory("Bearer " + token);
    }

    private void loadProfileUser(ActivityHomeBinding activityHomeBinding) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetUserByToken(token, activityHomeBinding);
    }

    private void callApiGetCategory(String token) {
        ApiHelper.apiService.getAllCategory(token).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> listCate = response.body();
                    categoryAdapter = new CategoryAdapter(listCate);
                    activityHomeBinding.rcViewCategory.setAdapter(categoryAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(HomeActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(HomeActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Find category unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiGetUserByToken(String token, ActivityHomeBinding activityHomeBinding) {
        token = "Bearer " + token;
        ApiHelper.apiService.getUserByToken(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    activityHomeBinding.setUser(user);
                    if (user.getFullName() == null) {
                        activityHomeBinding.setMessageHello("Hi " + user.getUserName());
                    } else {
                        String[] nameArr = user.getFullName().split(" ");
                        activityHomeBinding.setMessageHello("Hi " + nameArr[nameArr.length-1]);
                    }
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(HomeActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(HomeActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Find user unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Mapping() {
        imvAva = (ImageView) findViewById(R.id.imvAvatar);
        imvBanner = (ImageView) findViewById(R.id.imvBanner);
    }
}
