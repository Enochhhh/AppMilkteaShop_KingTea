package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.dto.RequestLoginDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseTokenDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.helper.JwtHelper;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton btnLogin;
    private TextView tvRegister;
    private EditText edUsername;
    private EditText edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiLogin();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void callApiLogin() {
        String username = edUsername.getText().toString();
        String password = edPass.getText().toString();

        if (username.equals("")) {
            Toast.makeText(LoginActivity.this, "Please type Username",
                    Toast.LENGTH_SHORT).show();
            edUsername.requestFocus();
            return;
        }

        RequestLoginDto request = new RequestLoginDto();
        request.setUsername(username);
        request.setPassword(password);

        ApiHelper.apiService.loginUser(request).enqueue(new Callback<ResponseTokenDto>() {
            @Override
            public void onResponse(Call<ResponseTokenDto> call, Response<ResponseTokenDto> response) {
                if (response.isSuccessful()) {
                    ResponseTokenDto token = response.body();

                    // Save token in Shared Preferences
                    SharedPreferences prefTokens = getSharedPreferences("TokenValue", 0);
                    SharedPreferences.Editor editor = prefTokens.edit();
                    editor.putString("token", token.getToken());
                    editor.commit();

                    String role = JwtHelper.getRoleFromToken(token.getToken());
                    if (role.equals("CUSTOMER")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        if (role.equals("ADMIN")) {
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        }
                    }
                }
                else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus() .equals("INTERNAL_SERVER_ERROR") || error.getStatus() == null) {
                        startActivity(new Intent(LoginActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseTokenDto> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Unsuccessfully\nPlease check username and password.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Mapping() {
        btnLogin = (AppCompatButton) findViewById(R.id.btnLoginn);
        tvRegister = (TextView) findViewById(R.id.tv_signup);
        edUsername = (EditText) findViewById(R.id.editTextUsernameLogin);
        edPass = (EditText) findViewById(R.id.editTextPassLogin);
    }
}