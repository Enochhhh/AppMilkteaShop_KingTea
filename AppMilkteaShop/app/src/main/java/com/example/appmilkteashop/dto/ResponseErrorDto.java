package com.example.appmilkteashop.dto;

import java.util.Date;

public class ResponseErrorDto {
    private String message;
    private String path;
    String status;
    Date date;

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }
}
