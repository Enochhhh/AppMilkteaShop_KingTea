package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import com.example.appmilkteashop.databinding.ActivityHomeBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ImageView imvAva;
    private ImageView imvBanner;
    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Mapping();

        // Round corner image Banner
        Glide.with(this)
                .load(R.drawable.milktea_banner)
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(80)))
                .into(imvBanner);

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetUserByToken(token, activityHomeBinding);
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
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error.getStatus() == null) {
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