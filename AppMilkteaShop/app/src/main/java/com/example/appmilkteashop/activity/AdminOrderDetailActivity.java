package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.OrderDetailAdapter;
import com.example.appmilkteashop.databinding.ActivityAdminOrderDetailBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderDetailActivity extends AppCompatActivity {
    ActivityAdminOrderDetailBinding activityAdminOrderDetailBinding;
    List<CustomMilkteaDto> listMilktea;
    OrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityAdminOrderDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_order_detail);
        SetEvent();

        String orderId = getIntent().getStringExtra("order_key");
        loadOrderDetail(orderId);
    }

    private void loadOrderDetail(String orderId) {
        activityAdminOrderDetailBinding.rcViewOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetOrderDetail("Bearer " + token, orderId);
    }

    private void callApiGetOrderDetail(String token, String orderId) {
        ApiHelper.apiService.getMilkteaInOrder(token, orderId).enqueue(new Callback<List<CustomMilkteaDto>>() {
            @Override
            public void onResponse(Call<List<CustomMilkteaDto>> call, Response<List<CustomMilkteaDto>> response) {
                if (response.isSuccessful()) {
                    listMilktea = response.body();
                    adapter = new OrderDetailAdapter(listMilktea);
                    activityAdminOrderDetailBinding.rcViewOrder.setAdapter(adapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(AdminOrderDetailActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(AdminOrderDetailActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CustomMilkteaDto>> call, Throwable t) {
                Toast.makeText(AdminOrderDetailActivity.this, "Get order detail unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetEvent() {
        activityAdminOrderDetailBinding.imvBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminOrderDetailActivity.this, AdminActivity.class));
            }
        });
    }
}