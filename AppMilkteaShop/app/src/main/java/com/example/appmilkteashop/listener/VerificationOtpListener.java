package com.example.appmilkteashop.listener;


public interface VerificationOtpListener {
    void resendOtp(String email);
    void announceSuccess();
}
