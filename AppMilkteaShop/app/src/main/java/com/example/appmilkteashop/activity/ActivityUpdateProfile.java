package com.example.appmilkteashop.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.ActivityProfileBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUpdateProfile extends AppCompatActivity {
    private ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        setEvent();
        getUserByToken();
    }

    public void getUserByToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        token = "Bearer " + token;
        ApiHelper.apiService.getUserByToken(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    activityProfileBinding.setUser(user);
                    String date =new SimpleDateFormat("dd/MM/yyyy").format(user.getDateOfBirth());
                    activityProfileBinding.edtDateOfBirth.setText(date);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if ( error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ActivityUpdateProfile.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ActivityUpdateProfile.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityUpdateProfile.this, "Find user unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiUpdateProfile(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.updateProfile(token, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    activityProfileBinding.edtName.setText(user.getFullName());
                    activityProfileBinding.edtEmail.setText(user.getEmail());
                    activityProfileBinding.edtAddress.setText(user.getAddress());
                    activityProfileBinding.editTextPhone.setText(user.getPhone());
                    String date =new SimpleDateFormat("dd/MM/yyyy").format(user.getDateOfBirth());
                    activityProfileBinding.edtDateOfBirth.setText(date);
                    dialogSendSuccess();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if ( error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ActivityUpdateProfile.this, ExceptionActivity.class));
                        return;
                    }
                    Toast.makeText(ActivityUpdateProfile.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityUpdateProfile.this, "Update profile unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setEvent() {
        activityProfileBinding.btnUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(activityProfileBinding.edtDateOfBirth.getText().toString());
                    user.setDateOfBirth(date);
                } catch (Exception e) {
                    Toast.makeText(ActivityUpdateProfile.this, "Please type Date of birth with correct format", Toast.LENGTH_SHORT).show();
                    return;
                }

                user.setFullName(activityProfileBinding.edtName.getText().toString());
                user.setEmail(activityProfileBinding.edtEmail.getText().toString());
                user.setPhone(activityProfileBinding.editTextPhone.getText().toString());
                user.setAddress(activityProfileBinding.edtAddress.getText().toString());
                callApiUpdateProfile(user);
            }
        });

        activityProfileBinding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, ActivityUpdateImage.class));
            }
        });

        activityProfileBinding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, CartActivity.class));
            }
        });

        activityProfileBinding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activityProfileBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, HomeActivity.class));
            }
        });

        activityProfileBinding.btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, ContactActivity.class));
            }
        });

        activityProfileBinding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, ActivityUpdateProfile.class));

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
                startActivity(new Intent(ActivityUpdateProfile.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateProfile.this, ChangePasswordActivity.class));
            }
        });

        dialog.show();
    }

    public void dialogSendSuccess() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.validate_success_dialog);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvTitle = dialog.findViewById(R.id.tvTitleErr);
        TextView tvInfor = dialog.findViewById(R.id.tvInforErr);
        tvTitle.setText("Update Profile Successfully");
        tvInfor.setText("Your information has been updated");

        dialog.show();
    }
}
