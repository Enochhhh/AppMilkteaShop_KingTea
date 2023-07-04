package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.ActivityHomeBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.Order;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    private AppCompatButton btnCheckout;
    private EditText edtReceiverName;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtNote;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Mapping();
        setEvent();
        callApiGetUserByToken();
    }

    private void setEvent() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
                String token = sharedPreferences.getString("token", "");
                Order order = createOrderRequest();
                if (order == null) return;

                callApiCreateOrder("Bearer " + token, order);
            }
        });
    }

    private Order createOrderRequest() {
        Order order = new Order();

        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        if (edtReceiverName.getText().toString().equals("") || edtPhone.getText().toString().equals("")
                    || edtAddress.getText().toString().equals("")) {
            Toast.makeText(CheckOutActivity.this, "Please complete all information", Toast.LENGTH_SHORT).show();
            return null;
        }
        order.setReceiverName(edtReceiverName.getText().toString());
        order.setPhone(edtPhone.getText().toString());
        order.setAddress(edtAddress.getText().toString());

        RadioButton radioSelected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        order.setPaymentMethod(radioSelected.getText().toString());
        order.setNote(edtNote.getText().toString());
        order.setTotalPrice(totalPrice);
        return order;
    }

    private void callApiGetUserByToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        token = "Bearer " + token;
        ApiHelper.apiService.getUserByToken(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    edtReceiverName.setText(user.getFullName());
                    edtPhone.setText(user.getPhone());
                    edtAddress.setText(user.getAddress());
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(CheckOutActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(CheckOutActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Find user unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiCreateOrder(String token, Order order) {
        ApiHelper.apiService.createOrder(token, order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order orderRes = response.body();
                    Intent intent = new Intent(CheckOutActivity.this, SuccessfullyActivity.class);
                    intent.putExtra("order_key", orderRes);
                    startActivity(intent);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(CheckOutActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(CheckOutActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Create Order unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Mapping() {
        btnCheckout = (AppCompatButton) findViewById(R.id.btnCheckoutOrder);
        edtReceiverName = (EditText) findViewById(R.id.edtReceiverName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        edtNote = (EditText) findViewById(R.id.edtNote);
    }
}