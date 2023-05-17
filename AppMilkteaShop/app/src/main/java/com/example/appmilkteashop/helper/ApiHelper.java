package com.example.appmilkteashop.helper;

import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.RequestLoginDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.dto.ResponseTokenDto;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.Order;
import com.example.appmilkteashop.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("milkteashop/kingtea/category/getall")
    Call<List<Category>> getAllCategory(@Header("Authorization") String token);

    @GET("milkteashop/kingtea/milktea/getbycategoryname")
    Call<List<Milktea>> getMilkteaSpecial(@Header("Authorization") String token,
                                          @Query("name") String name);

    @POST("milkteashop/kingtea/cart/addtocart")
    Call<ResponseStringDto> addMilkteaToCart(@Header("Authorization") String token,
                                             @Body CustomMilkteaDto customMilkteaDto);

    @GET("milkteashop/kingtea/cart/getmilktea")
    Call<List<CustomMilkteaDto>> getCart(@Header("Authorization") String token);

    @POST("milkteashop/kingtea/cart/increaseitemoncart")
    Call<ResponseStringDto> increaseMilkteaItem(@Header("Authorization") String token,
                                             @Body CustomMilkteaDto customMilkteaDto);

    @POST("milkteashop/kingtea/cart/decreaseitemoncart")
    Call<ResponseStringDto> decreaseMilkteaItem(@Header("Authorization") String token,
                                                @Body CustomMilkteaDto customMilkteaDto);

    @DELETE("milkteashop/kingtea/cart/deleteitem")
    Call<ResponseStringDto> deleteCartLine(@Header("Authorization") String token,
                                                @Query("customMilkteaDto") String customMilkteaDto);

    @POST("milkteashop/kingtea/order/create")
    Call<Order> createOrder(@Header("Authorization") String token,
                            @Body Order order);

    @GET("milkteashop/kingtea/order/sendemail")
    Call<ResponseStringDto> sendMail(@Header("Authorization") String token);

    @GET("milkteashop/kingtea/milktea/getall")
    Call<List<Milktea>> getAllMilktea(@Header("Authorization") String token);

    @GET("milkteashop/kingtea/milktea/getbycategory")
    Call<List<Milktea>> getByCategoryName(@Header("Authorization") String token,
                                      @Query("categoryName") String name);
}
