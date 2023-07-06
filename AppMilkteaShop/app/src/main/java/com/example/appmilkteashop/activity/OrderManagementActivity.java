package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.OrderManagementAdapter;
import com.example.appmilkteashop.databinding.ActivityOrderManagementBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.OrderManagementListener;
import com.example.appmilkteashop.model.Order;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagementActivity extends AppCompatActivity {

    private ActivityOrderManagementBinding activityOrderManagementBinding;
    private OrderManagementAdapter orderManagementAdapter;
    private String imageOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderManagementBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_management);
        setEvent();
        loadDataRcViewOrder();
    }

    private void loadDataRcViewOrder() {
        activityOrderManagementBinding.rcViewOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");

        callApiGetAllOrder("Bearer " +token);
    }

    private void callApiGetAllOrder(String token) {
        ApiHelper.apiService.getAllOrderOfUser(token).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();

                    orderManagementAdapter = new OrderManagementAdapter(orders, new OrderManagementListener() {
                        @Override
                        public void showDetailOrder(String orderId) {
                            Intent intent = new Intent(OrderManagementActivity.this, OrderDetailActivity.class);
                            intent.putExtra("order_key", orderId);
                            startActivity(intent);
                        }

                        @Override
                        public void paymentOrder(Order order) {
                            callApiPayPal(token, order);
                        }

                        @Override
                        public void cancelOrder(String orderId) {
                            callApiCancelOrder(token, orderId);
                        }

                        @Override
                        public String getImgUrlOrder(String orderId) {
                            return callApiGetImgUrlOrder(token, orderId);
                        }
                    });

                    activityOrderManagementBinding.rcViewOrder.setAdapter(orderManagementAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(OrderManagementActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(OrderManagementActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderManagementActivity.this, "Get orders unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiPayPal(String token, Order order) {
        ApiHelper.apiService.paypalPayment(token, order).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    ResponseStringDto stringDto = response.body();
                    Uri uri = Uri.parse(stringDto.getMessage()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(OrderManagementActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(OrderManagementActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(OrderManagementActivity.this, "Payment unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiCancelOrder(String token, String orderId) {
        ApiHelper.apiService.cancelOrder(token, orderId).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    dialogCancelSuccess();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(OrderManagementActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(OrderManagementActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    dialogCancelFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                dialogCancelFail();
                Toast.makeText(OrderManagementActivity.this, "Cancel order unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public String callApiGetImgUrlOrder(String token, String orderId) {
        ApiHelper.apiService.getImageOrder(token, orderId).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    ResponseStringDto responseStringDto = response.body();
                    imageOrder = responseStringDto.getMessage();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(OrderManagementActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(OrderManagementActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(OrderManagementActivity.this, "Get orders unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
        return imageOrder;
    }

    private void setEvent() {
        activityOrderManagementBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagementActivity.this, HomeActivity.class));
            }
        });

        activityOrderManagementBinding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagementActivity.this, CartActivity.class));
            }
        });

        activityOrderManagementBinding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activityOrderManagementBinding.supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagementActivity.this, ContactActivity.class));
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
                startActivity(new Intent(OrderManagementActivity.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagementActivity.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderManagementActivity.this, ChangePasswordActivity.class));
            }
        });
        dialog.show();
    }

    private void dialogCancelFail() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.login_fail_dialog);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvTitle = dialog.findViewById(R.id.tvTitleErr);
        TextView tvTitleInfor = dialog.findViewById(R.id.tvInforErr);

        tvTitle.setText("Cancel Order\nUnsuccessfully");
        tvTitleInfor.setText("Your's order has been made, so you can't cancel it");

        dialog.show();
    }

    private void dialogCancelSuccess() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.validate_success_dialog);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(OrderManagementActivity.this, OrderManagementActivity.class));
            }
        });

        dialog.show();
    }
}