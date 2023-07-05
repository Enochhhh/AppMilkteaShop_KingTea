package com.example.appmilkteashop.dto;

import java.io.Serializable;

public class OtpDto implements Serializable {
    private String email;
    private String otpCode;
    private long dateNowMilisecond;
    private String newPass;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public long getDateNowMilisecond() {
        return dateNowMilisecond;
    }

    public void setDateNowMilisecond(long dateNowMilisecond) {
        this.dateNowMilisecond = dateNowMilisecond;
    }
}
