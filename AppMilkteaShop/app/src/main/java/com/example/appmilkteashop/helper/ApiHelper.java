package com.example.appmilkteashop.helper;

import com.example.appmilkteashop.dto.RequestLoginDto;
import com.example.appmilkteashop.dto.ResponseTokenDto;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiHelper {
    public static final String domainApi = "http://192.168.1.10:8080/";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiHelper apiService = new Retrofit.Builder()
            .baseUrl(domainApi)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiHelper.class);

    @POST("milkteashop/kingtea/authentication/register")
    Call<User> registerUser(@Body User user);

    @POST("milkteashop/kingtea/authentication/login")
    Call<ResponseTokenDto> loginUser(@Body RequestLoginDto requestLogin);

    @GET("milkteashop/kingtea/user/getbytoken")
    Call<User> getUserByToken(@Header("Authorization") String token);
}
