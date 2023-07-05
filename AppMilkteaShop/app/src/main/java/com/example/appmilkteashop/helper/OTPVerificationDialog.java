package com.example.appmilkteashop.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.dto.OtpDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.listener.VerificationOtpListener;

import androidx.annotation.NonNull;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationDialog extends Dialog {
    private EditText otpET1, otpET2, otpET3, otpET4;
    private TextView resendBtn;
    private Button verifyBtn;
    private TextView invalidOtp;

    private int resendTime = 300;
    private boolean resendEnabled = false;
    private int selectedETPosition = 0;
    private final String email;
    private String newPass;
    private VerificationOtpListener verificationOtpListener;

    public OTPVerificationDialog(@NonNull Context context, String email, String newPass, VerificationOtpListener verificationOtpListener) {
        super(context);
        this.email = email;
        this.newPass = newPass;
        this.verificationOtpListener = verificationOtpListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.otp_dialog);

        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);

        resendBtn = findViewById(R.id.resendBtn);
        verifyBtn = findViewById(R.id.verifyBtn);
        invalidOtp = (TextView) findViewById(R.id.tvInvalid);
        final TextView emailTv = (TextView) findViewById(R.id.emailTxt);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);

        // By default open keyboard on first EditText
        showKeyboard(otpET1);
        startCountDownTimer();
        emailTv.setText(email);

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resendEnabled) {
                    verificationOtpListener.resendOtp(email);
                    otpET1.setText(""); otpET2.setText(""); otpET3.setText(""); otpET4.setText("");
                    otpET1.requestFocus();
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpInput = otpET1.getText().toString() + otpET2.getText().toString() + otpET3.getText().toString() + otpET4.getText().toString().trim();

                if(otpInput.length() == 4) {
                    long dateMiliNow = new Date().getTime();
                    callApiCheckOtp(otpInput, dateMiliNow);
                } else {
                    invalidOtp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void callApiCheckOtp(String otpInput, long dateMili) {
        OtpDto otpDto = new OtpDto();
        otpDto.setOtpCode(otpInput);
        otpDto.setDateNowMilisecond(dateMili);
        otpDto.setEmail(email);
        otpDto.setNewPass(newPass);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");

        ApiHelper.apiService.checkOtpAndCreateNewPass("Bearer " + token, otpDto).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    invalidOtp.setVisibility(View.GONE);
                    verificationOtpListener.announceSuccess();
                    dismissDialog();
                } else {
                    invalidOtp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(getContext(), "Check OTP Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dismissDialog() {
        this.dismiss();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                if (selectedETPosition == 0) {
                    selectedETPosition = 1;
                    showKeyboard(otpET2);
                } else if (selectedETPosition == 1) {
                    selectedETPosition = 2;
                    showKeyboard(otpET3);
                } else if (selectedETPosition == 2) {
                    selectedETPosition = 3;
                    showKeyboard(otpET4);
                } else {
                    verifyBtn.setBackgroundColor(R.drawable.round_back_red_100);
                }
            }
        }
    };

    private void showKeyboard(EditText otpET) {
        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code (" + (millisUntilFinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
            }
        }.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (selectedETPosition == 3) {
                selectedETPosition = 2;
                showKeyboard(otpET3);
            } else if (selectedETPosition == 2) {
                selectedETPosition = 1;
                showKeyboard(otpET2);
            } else if (selectedETPosition == 1) {
                selectedETPosition = 0;
                showKeyboard(otpET1);
            }

            verifyBtn.setBackgroundResource(R.drawable.round_back_grow_100);
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}