package com.example.appmilkteashop.service;

import com.example.appmilkteashop.dto.RequestLoginDto;
import com.example.appmilkteashop.dto.ResponseTokenDto;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    public static final String domainApi = "http://192.168.1.10:8080/";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(domainApi)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("milkteashop/kingtea/authentication/register")
    Call<User> registerUser(@Body User user);

    @POST("milkteashop/kingtea/authentication/login")
    Call<ResponseTokenDto> loginUser(@Body RequestLoginDto requestLogin);
}
