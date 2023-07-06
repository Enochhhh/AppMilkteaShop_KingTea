package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import com.example.appmilkteashop.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.databinding.ActivityChangeImageBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUpdateImage extends AppCompatActivity {

    private ActivityChangeImageBinding activityChangeImageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityChangeImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_image);
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
                    activityChangeImageBinding.setUser(user);
                    String date =new SimpleDateFormat("dd/MM/yyyy").format(user.getDateOfBirth());
                    activityChangeImageBinding.edtImageUrl.setText(date);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if ( error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ActivityUpdateImage.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ActivityUpdateImage.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityUpdateImage.this, "Find user unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiUpdateImage(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.updateImage(token, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    dialogSendSuccess();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if ( error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ActivityUpdateImage.this, ExceptionActivity.class));
                        return;
                    }
                    Toast.makeText(ActivityUpdateImage.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityUpdateImage.this, "Update profile unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setEvent() {
        activityChangeImageBinding.btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();

                user.setImgUrl(activityChangeImageBinding.edtImageUrl.getText().toString());
                callApiUpdateImage(user);
            }
        });

        activityChangeImageBinding.imgProfileChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, CartActivity.class));
            }
        });

        activityChangeImageBinding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, CartActivity.class));
            }
        });

        activityChangeImageBinding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activityChangeImageBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, HomeActivity.class));
            }
        });

        activityChangeImageBinding.btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, ContactActivity.class));
            }
        });

        activityChangeImageBinding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, ActivityUpdateProfile.class));
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
                startActivity(new Intent(ActivityUpdateImage.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUpdateImage.this, ChangePasswordActivity.class));
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

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(ActivityUpdateImage.this, ActivityUpdateProfile.class));
            }
        });

        dialog.show();
    }
}
