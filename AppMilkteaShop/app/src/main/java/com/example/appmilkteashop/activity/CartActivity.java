package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.CartAdapter;
import com.example.appmilkteashop.databinding.ActivityCartBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.TotalPrice;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding activityCartBinding;
    private CartAdapter cartAdapter;
    private List<CustomMilkteaDto> customMilkteaDtoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        activityCartBinding.setCart(this);
        SetEvent();

        loadDataRcViewCart();
    }

    private void loadDataCheckOut(List<CustomMilkteaDto> customMilkteaDtoList) {
        TotalPrice totalPrice = new TotalPrice();

        int totalPriceItem = 0;
        for(CustomMilkteaDto customMilkteaDto : customMilkteaDtoList) {
            totalPriceItem += customMilkteaDto.getTotalPriceOfItem() * customMilkteaDto.getQuantity();
        }
        totalPrice.setTotalItems(String.valueOf(totalPriceItem) + "₫");
        totalPrice.setDeliveryService("30000₫");
        totalPrice.setTax(String.valueOf((int)(0.05 * totalPriceItem)) + "₫");
        totalPrice.setTotal(String.valueOf(totalPriceItem + 30000 + (int)(totalPriceItem * 0.05)) + "₫");

        activityCartBinding.setTotalPrice(totalPrice);
    }

    private void loadDataRcViewCart() {
        activityCartBinding.rcViewCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        callApiGetCart("Bearer " + token);
    }

    private void callApiGetCart(String token) {
        ApiHelper.apiService.getCart(token).enqueue(new Callback<List<CustomMilkteaDto>>() {
            @Override
            public void onResponse(Call<List<CustomMilkteaDto>> call, Response<List<CustomMilkteaDto>> response) {
                if (response.isSuccessful()) {
                    customMilkteaDtoList = response.body();

                    cartAdapter = new CartAdapter(customMilkteaDtoList, new ChangeNumberItemListener() {
                        @Override
                        public void change(boolean isPlus, Milktea milktea) {
                            return;
                        }

                        @Override
                        public void change(boolean isPlus, CustomMilkteaDto milktea) {
                            callApiUpdateToCart(token, milktea, isPlus);
                        }

                        @Override
                        public void delete(CustomMilkteaDto customMilkteaDto) {
                            callApiDeleteToCart(token, customMilkteaDto);
                        }
                    });

                    activityCartBinding.rcViewCart.setAdapter(cartAdapter);
                    if (customMilkteaDtoList.isEmpty()) {
                        activityCartBinding.scrollView.setVisibility(View.GONE);
                        activityCartBinding.tvEmpty.setVisibility(View.VISIBLE);
                        activityCartBinding.imvCartEmpty.setVisibility(View.VISIBLE);
                    } else {
                        activityCartBinding.scrollView.setVisibility(View.VISIBLE);
                        activityCartBinding.tvEmpty.setVisibility(View.GONE);
                        activityCartBinding.imvCartEmpty.setVisibility(View.GONE);
                        loadDataCheckOut(customMilkteaDtoList);
                    }
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(CartActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CustomMilkteaDto>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Get milktea on cart unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetEvent() {
        activityCartBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
            }
        });

        activityCartBinding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activityCartBinding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                String temp = activityCartBinding.tvFeeTotal.getText().toString();
                intent.putExtra("totalPrice", Integer.parseInt(temp.substring(0, temp.length() - 1)));
                startActivity(intent);
            }
        });

        activityCartBinding.supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, ContactActivity.class));
            }
        });
    }

    private void callApiUpdateToCart(String token, CustomMilkteaDto cusRequest, boolean isPlus) {
        CustomMilkteaDto customMilkteaDto = new CustomMilkteaDto();
        customMilkteaDto.setCustomMilkteaId(cusRequest.getCustomMilkteaId());
        customMilkteaDto.setQuantity(1);
        if (isPlus) {
            ApiHelper.apiService.increaseMilkteaItem(token, customMilkteaDto).enqueue(new Callback<ResponseStringDto>() {
                @Override
                public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                    if (response.isSuccessful()) {
                        ResponseStringDto mes = response.body();
                        Toast.makeText(CartActivity.this, mes.getMessage(), Toast.LENGTH_SHORT).show();
                        loadDataRcViewCart();
                    } else {
                        ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                        if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                            startActivity(new Intent(CartActivity.this, ExceptionActivity.class));
                        }
                        Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Increase quantity milktea unsuccessfully",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ApiHelper.apiService.decreaseMilkteaItem(token, customMilkteaDto).enqueue(new Callback<ResponseStringDto>() {
                @Override
                public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                    if (response.isSuccessful()) {
                        loadDataRcViewCart();
                    } else {
                        ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                        if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                            startActivity(new Intent(CartActivity.this, ExceptionActivity.class));
                        }
                        Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Decrease quantity milktea unsuccessfully",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void callApiDeleteToCart(String token, CustomMilkteaDto customMilkteaDto) {
        ApiHelper.apiService.deleteCartLine(token, customMilkteaDto.getCustomMilkteaId()).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    loadDataRcViewCart();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(CartActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Delete item unsuccessfully",
                        Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(CartActivity.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, ChangePasswordActivity.class));
            }
        });

        dialog.show();
    }
}