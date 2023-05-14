package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.appmilkteashop.R;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rcViewCart;
    private TextView tvFeeItemTotal, tvFeeTax, tvFeeDelivery, tvFeeTotal;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    private void Mapping() {

    }
}