package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.CartAdapter;
import com.example.appmilkteashop.adapter.CategoryAdapter;
import com.example.appmilkteashop.adapter.MilkteaByCategoryAdapter;
import com.example.appmilkteashop.adapter.SpecialAdapter;
import com.example.appmilkteashop.databinding.ActivityCartBinding;
import com.example.appmilkteashop.databinding.ActivityMilkteabycategoryBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.ChangeActivityListener;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.listener.ChangeToDetailActivityListener;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.TotalPrice;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MilkteaByCategoryActivity extends AppCompatActivity {
    private ActivityMilkteabycategoryBinding activityMilkteabycategoryBinding;
    private MilkteaByCategoryAdapter milkteaByCategoryAdapter;
    private List<Milktea> milkteaList;
    public String nameCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMilkteabycategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_milkteabycategory);

        nameCate = getIntent().getStringExtra("categoryId");
        activityMilkteabycategoryBinding.setMilktea(this);

        SetEvent();

        loadDataRcViewMilktea();
    }

    private void loadDataRcViewMilktea() {
        activityMilkteabycategoryBinding.rcViewMilktea.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");

        if (nameCate.equals("All")) {
            callApiGetAllMilktea("Bearer " +token);
        } else {
            callApiGetMilkteaByCategory("Bearer " + token, nameCate);
        }
    }

    private void callApiGetAllMilktea(String token) {
        ApiHelper.apiService.getAllMilktea(token).enqueue(new Callback<List<Milktea>>() {
            @Override
            public void onResponse(Call<List<Milktea>> call, Response<List<Milktea>> response) {
                if (response.isSuccessful()) {
                    List<Milktea> milkteas = response.body();
                    milkteaByCategoryAdapter = new MilkteaByCategoryAdapter(milkteas, new ChangeNumberItemListener() {
                        @Override
                        public void change(boolean isPlus, Milktea milktea) {
                            callApiUpdateToCart(token, milktea, isPlus);
                        }

                        @Override
                        public void change(boolean isPlus, CustomMilkteaDto milktea) {return;}

                        @Override
                        public void delete(CustomMilkteaDto customMilkteaDto) {return;}
                    }, new ChangeToDetailActivityListener(){
                        @Override
                        public void changeActivity(Milktea milktea) {
                            Intent intent = new Intent(MilkteaByCategoryActivity.this, ShowDetailActivity.class);
                            intent.putExtra("tea_key", milktea);
                            startActivity(intent);
                        }
                    });
                    activityMilkteabycategoryBinding.rcViewMilktea.setAdapter(milkteaByCategoryAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(MilkteaByCategoryActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(MilkteaByCategoryActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Milktea>> call, Throwable t) {
                Toast.makeText(MilkteaByCategoryActivity.this, "Get milktea unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void callApiGetMilkteaByCategory(String token, String categoryname) {
        ApiHelper.apiService.getByCategoryName(token, categoryname).enqueue(new Callback<List<Milktea>>() {
            @Override
            public void onResponse(Call<List<Milktea>> call, Response<List<Milktea>> response) {
                if (response.isSuccessful()) {
                    List<Milktea> milkteaList = response.body();
                    milkteaByCategoryAdapter = new MilkteaByCategoryAdapter(milkteaList, new ChangeNumberItemListener() {
                        @Override
                        public void change(boolean isPlus, Milktea milktea) {
                            callApiUpdateToCart(token, milktea, true);
                        }

                        @Override
                        public void delete(CustomMilkteaDto customMilkteaDto) {
                            return;
                        }

                        @Override
                        public void change(boolean isPlus, CustomMilkteaDto milktea) {
                            return;
                        }
                    }, new ChangeToDetailActivityListener(){
                        @Override
                        public void changeActivity(Milktea milktea) {
                            Intent intent = new Intent(MilkteaByCategoryActivity.this, ShowDetailActivity.class);
                            intent.putExtra("tea_key", milktea);
                            startActivity(intent);
                        }
                    });
                    activityMilkteabycategoryBinding.rcViewMilktea.setAdapter(milkteaByCategoryAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(MilkteaByCategoryActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(MilkteaByCategoryActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Milktea>> call, Throwable t) {
                Toast.makeText(MilkteaByCategoryActivity.this, "Find category unsuccessfully",
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
                        Toast.makeText(MilkteaByCategoryActivity.this, stringDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                        if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                            startActivity(new Intent(MilkteaByCategoryActivity.this, ExceptionActivity.class));
                        }
                        Toast.makeText(MilkteaByCategoryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                    Toast.makeText(MilkteaByCategoryActivity.this, "Add milktea to cart unsuccessfully",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void SetEvent() {
        activityMilkteabycategoryBinding.tvBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MilkteaByCategoryActivity.this, HomeActivity.class));
            }
        });
        activityMilkteabycategoryBinding.imvBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MilkteaByCategoryActivity.this, HomeActivity.class));
            }
        });

        activityMilkteabycategoryBinding.imvBottomCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MilkteaByCategoryActivity.this, CartActivity.class));
            }
        });

        activityMilkteabycategoryBinding.imvBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

    }

    private void dialogSetting() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.setting_dialog);

        dialog.setCanceledOnTouchOutside(true);

        ConstraintLayout btnLogout = (ConstraintLayout) dialog.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefTokens = getSharedPreferences("TokenValue", 0);
                prefTokens.edit().clear().commit();
                startActivity(new Intent(MilkteaByCategoryActivity.this, LoginActivity.class));
            }
        });
        dialog.show();
    }
}