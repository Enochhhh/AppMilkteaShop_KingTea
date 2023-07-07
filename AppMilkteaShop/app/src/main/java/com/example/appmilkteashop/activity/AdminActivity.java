package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import  com.example.appmilkteashop.R;

public class AdminActivity extends AppCompatActivity {

    ConstraintLayout btnOrder;
    ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Mapping();
        setEvent();
    }

    public void Mapping() {
        btnOrder = (ConstraintLayout) findViewById(R.id.orderReceiveBtn);
        btnLogout = (ImageView) findViewById(R.id.imvLogout);
    }

    public void setEvent() {
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminOrderActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefTokens = getSharedPreferences("TokenValue", 0);
                prefTokens.edit().clear().commit();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            }
        });
    }
}