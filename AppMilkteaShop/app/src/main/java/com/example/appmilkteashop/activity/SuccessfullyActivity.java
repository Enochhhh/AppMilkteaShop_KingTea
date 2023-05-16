package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.CartAdapter;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.model.Milktea;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessfullyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully);

        ImageView btnHome = (ImageView) findViewById(R.id.btnHomeSuccess);
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiSendEmail("Bearer " + token);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessfullyActivity.this, HomeActivity.class));
            }
        });
    }

    private void callApiSendEmail(String token) {
        ApiHelper.apiService.sendMail(token).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                ResponseStringDto dto = response.body();
                return;
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(SuccessfullyActivity.this, "Send email unsuccessfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}