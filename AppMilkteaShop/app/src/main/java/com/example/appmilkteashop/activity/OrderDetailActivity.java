package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.OrderDetailAdapter;
import com.example.appmilkteashop.databinding.ActivityOrderDetailBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    ActivityOrderDetailBinding activityOrderDetailBinding;
    List<CustomMilkteaDto> listMilktea;
    OrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityOrderDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        SetEvent();

        String orderId = getIntent().getStringExtra("order_key");
        loadOrderDetail(orderId);
    }

    private void loadOrderDetail(String orderId) {
        activityOrderDetailBinding.rcViewOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
                    activityOrderDetailBinding.rcViewOrder.setAdapter(adapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(OrderDetailActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(OrderDetailActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CustomMilkteaDto>> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this, "Get order detail unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetEvent() {
        activityOrderDetailBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this, HomeActivity.class));
            }
        });

        activityOrderDetailBinding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activityOrderDetailBinding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this, CartActivity.class));
            }
        });

        activityOrderDetailBinding.supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this, ContactActivity.class));
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
                startActivity(new Intent(OrderDetailActivity.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this, ChangePasswordActivity.class));
            }
        });

        dialog.show();
    }
}