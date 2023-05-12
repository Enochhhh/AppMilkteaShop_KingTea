package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.model.User;
import com.example.appmilkteashop.helper.ApiHelper;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Mapping();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiRegister();
            }
        });
    }

    private void callApiRegister() {
        User user = new User();

        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhoneNumber.getText().toString();

        if (userName.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please type Username",
                    Toast.LENGTH_SHORT).show();
            etUserName.requestFocus();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please type Password",
                    Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }
        if (email.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please type Email",
                    Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }
        if (phone.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please type Phone number",
                    Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return;
        }

        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);


        ApiHelper.apiService.registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    ResponseErrorDto message = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (message.getStatus() == "INTERNAL_SERVER_ERROR" || message.getStatus() == null) {
                        startActivity(new Intent(RegisterActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(RegisterActivity.this, "" + message.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Error ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Mapping() {
        etUserName = (EditText) findViewById(R.id.editTextUsername);
        etPassword = (EditText) findViewById(R.id.editTextPass);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPhoneNumber = (EditText) findViewById(R.id.editTextPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }
}