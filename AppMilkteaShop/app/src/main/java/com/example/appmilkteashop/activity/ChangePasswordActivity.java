package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.databinding.ActivityChangepassBinding;
import com.example.appmilkteashop.R;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.helper.OTPVerificationDialog;
import com.example.appmilkteashop.listener.VerificationOtpListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangepassBinding activityChangepassBinding;
    private boolean isForgot = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityChangepassBinding = DataBindingUtil.setContentView(this, R.layout.activity_changepass);

        activityChangepassBinding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityChangepassBinding.tvInvalidEmail.setVisibility(View.GONE);
                activityChangepassBinding.tvInvalidPass.setVisibility(View.GONE);
                activityChangepassBinding.tvInvalidPassRe.setVisibility(View.GONE);


                if (activityChangepassBinding.edtEmail.getText().toString().equals("")) {
                    activityChangepassBinding.tvInvalidEmail.setVisibility(View.VISIBLE);
                    activityChangepassBinding.edtEmail.requestFocus();
                    return;
                }
                if (activityChangepassBinding.editTextPass.getText().toString().equals("")) {
                    activityChangepassBinding.tvInvalidPass.setVisibility(View.VISIBLE);
                    activityChangepassBinding.editTextPass.requestFocus();
                    return;
                }
                if (activityChangepassBinding.editTextPassRepeat.getText().toString().equals("") ||
                        !activityChangepassBinding.editTextPassRepeat.getText().toString().equals(activityChangepassBinding.editTextPass.getText().toString())) {
                    activityChangepassBinding.tvInvalidPassRe.setVisibility(View.VISIBLE);
                    activityChangepassBinding.editTextPassRepeat.requestFocus();
                    return;
                }

                callApiCheckEmailAndCreateOTP(activityChangepassBinding.edtEmail.getText().toString());
            }
        });
    }

    public void resentOtp(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.createOtpAndCheckEmail(token, email).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    callApiSendOtpEmail(email);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ChangePasswordActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ChangePasswordActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Create OTP unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiCheckEmailAndCreateOTP(String email) {

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.createOtpAndCheckEmail(token, email).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    callApiSendOtpEmail(email);
                    OTPVerificationDialog otpVerificationDialog = new OTPVerificationDialog(ChangePasswordActivity.this, email,
                            activityChangepassBinding.editTextPass.getText().toString(), new VerificationOtpListener() {
                        @Override
                        public void resendOtp(String email) {
                            resentOtp(email);
                        }

                        @Override
                        public void announceSuccess() {
                            dialogChangeSuccess();
                        }
                    });
                    otpVerificationDialog.setCancelable(true);
                    otpVerificationDialog.show();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ChangePasswordActivity.this, ExceptionActivity.class));
                        return;
                    }
                    Toast.makeText(ChangePasswordActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    activityChangepassBinding.tvInvalidEmail.setVisibility(View.VISIBLE);
                    activityChangepassBinding.edtEmail.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Create OTP unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiSendOtpEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");
        ApiHelper.apiService.sendOtpCodeToEmailUser(token, email).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    ResponseStringDto responseStringDto = response.body();
                    Toast.makeText(ChangePasswordActivity.this, responseStringDto.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ChangePasswordActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ChangePasswordActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "OTP will send to your's email soon",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void dialogChangeSuccess() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.validate_success_dialog);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (isForgot) {
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(ChangePasswordActivity.this, HomeActivity.class));
                }
            }
        });

        TextView tvTitle = dialog.findViewById(R.id.tvTitleErr);
        TextView tvInfor = dialog.findViewById(R.id.tvInforErr);
        tvTitle.setText("Password Is Changed");
        tvInfor.setText("Change Password Successfully");

        dialog.show();
    }
}