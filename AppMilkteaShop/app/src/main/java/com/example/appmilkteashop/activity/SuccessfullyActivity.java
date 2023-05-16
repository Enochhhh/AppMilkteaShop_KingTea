package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appmilkteashop.R;

public class SuccessfullyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully);

        ImageView btnHome = (ImageView) findViewById(R.id.btnHomeSuccess);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessfullyActivity.this, HomeActivity.class));
            }
        });
    }
}