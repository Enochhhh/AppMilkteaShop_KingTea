package com.example.appmilkteashop.helper;

import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.OtpDto;
import com.example.appmilkteashop.dto.RequestLoginDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.dto.ResponseTokenDto;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Contact;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.Order;
import com.example.appmilkteashop.model.Topping;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {
    public static final String domainApi = "http://192.168.113.1:8080/";
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

    @GET("milkteashop/kingtea/milktea/getcategory")
    Call<Category> getCategoryOfMilktea(@Header("Authorization") String token,
                                        @Query("milkteaId") String milkTeaId);

    @GET("milkteashop/kingtea/topping/getall")
    Call<List<Topping>> getAllToppings(@Header("Authorization") String token);

    @POST("milkteashop/kingtea/paypal/pay")
    Call<ResponseStringDto> paypalPayment(@Header("Authorization") String token, @Body Order order);

    @GET("milkteashop/kingtea/order/getbytoken")
    Call<List<Order>> getAllOrderOfUser(@Header("Authorization") String token);

    @GET("milkteashop/kingtea/order/getimageorder")
    Call<ResponseStringDto> getImageOrder(@Header("Authorization") String token, @Query("orderId") String orderId);

    @PUT("milkteashop/kingtea/order/cancel/{orderId}")
    Call<ResponseStringDto> cancelOrder(@Header("Authorization") String token, @Path("orderId") String orderId);

    @PUT("milkteashop/kingtea/order/accept/{orderId}")
    Call<ResponseStringDto> acceptOrder(@Header("Authorization") String token, @Path("orderId") String orderId);

    @GET("milkteashop/kingtea/order/getmilktea")
    Call<List<CustomMilkteaDto>> getMilkteaInOrder(@Header("Authorization") String token, @Query("orderId") String orderId);

    @PUT("milkteashop/kingtea/user/otp/createotp/{email}")
    Call<ResponseStringDto> createOtpAndCheckEmail(@Header("Authorization") String token, @Path("email") String email);

    @POST("milkteashop/kingtea/user/otp/checkotp")
    Call<ResponseStringDto> checkOtpAndCreateNewPass(@Header("Authorization") String token, @Body OtpDto otpDto);

    @GET("milkteashop/kingtea/user/otp/sendotpmail")
    Call<ResponseStringDto> sendOtpCodeToEmailUser(@Header("Authorization") String token, @Query("email") String email);

    @GET("milkteashop/kingtea/milktea/getkeyword")
    Call<List<Milktea>> getByKeyword(@Header("Authorization") String token, @Query("keyword") String keyword);

    @POST("milkteashop/kingtea/contact/create")
    Call<Contact> createContact(@Header("Authorization") String token, @Body Contact contact);

    @PUT("milkteashop/kingtea/user/update/profile")
    Call<User> updateProfile(@Header("Authorization") String token, @Body User user);

    @PUT("milkteashop/kingtea/user/update/image")
    Call<User> updateImage(@Header("Authorization") String token, @Body User user);

    @GET("milkteashop/kingtea/order/getbystate")
    Call<List<Order>> getOrderByState(@Header("Authorization") String token, @Query("state") String state);
}
