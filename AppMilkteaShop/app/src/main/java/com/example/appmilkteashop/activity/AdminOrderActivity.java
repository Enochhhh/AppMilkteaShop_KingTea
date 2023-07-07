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
import com.example.appmilkteashop.adapter.OrderAdminReceiveAdapter;
import com.example.appmilkteashop.adapter.OrderManagementAdapter;
import com.example.appmilkteashop.databinding.ActivityAdminOrderBinding;
import com.example.appmilkteashop.databinding.ActivityOrderManagementBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.OrderAdminReceiveListener;
import com.example.appmilkteashop.listener.OrderManagementListener;
import com.example.appmilkteashop.model.Order;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderActivity extends AppCompatActivity {

    private ActivityAdminOrderBinding activityAdminOrderBinding;
    private OrderAdminReceiveAdapter orderAdminReceiveAdapter;
    private String imageOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_order);
        setEvent();
        loadDataRcViewOrder();
    }

    private void loadDataRcViewOrder() {
        activityAdminOrderBinding.rcViewOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");

        callApiGetAllOrder("Bearer " +token);
    }

    private void callApiGetAllOrder(String token) {
        ApiHelper.apiService.getOrderByState(token, "Waiting Accept").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();

                    orderAdminReceiveAdapter = new OrderAdminReceiveAdapter(orders, new OrderAdminReceiveListener() {
                        @Override
                        public void showDetailOrder(String orderId) {
                            Intent intent = new Intent(AdminOrderActivity.this, AdminOrderDetailActivity.class);
                            intent.putExtra("order_key", orderId);
                            startActivity(intent);
                        }

                        @Override
                        public void receiveOrder(String orderId) {
                            SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
                            String token = "Bearer " + sharedPreferences.getString("token", "");
                            callApiAcceptOrder(token, orderId);
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

                    activityAdminOrderBinding.rcViewOrder.setAdapter(orderAdminReceiveAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(AdminOrderActivity.this, ExceptionActivity.class));
                    }
                    activityAdminOrderBinding.rcViewOrder.setVisibility(View.GONE);
                    activityAdminOrderBinding.tvEmpty.setVisibility(View.VISIBLE);
                    Toast.makeText(AdminOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(AdminOrderActivity.this, "Get orders unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiCancelOrder(String token, String orderId) {
        ApiHelper.apiService.cancelOrder(token, orderId).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    dialogSuccess("Order Is Cancelled", "Cancel Order Successfully");
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(AdminOrderActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(AdminOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    dialogCancelFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                dialogCancelFail();
                Toast.makeText(AdminOrderActivity.this, "Cancel order unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callApiAcceptOrder(String token, String orderId) {
        ApiHelper.apiService.acceptOrder(token, orderId).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    dialogSuccess("Order Is Accepted", "Accepted Order Successfully");
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(AdminOrderActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(AdminOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    dialogCancelFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                dialogCancelFail();
                Toast.makeText(AdminOrderActivity.this, "Accept order unsuccessfully",
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
                        startActivity(new Intent(AdminOrderActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(AdminOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(AdminOrderActivity.this, "Get orders unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
        return imageOrder;
    }

    private void setEvent() {
        activityAdminOrderBinding.imvBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminOrderActivity.this, AdminActivity.class));
            }
        });
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

    private void dialogSuccess(String title, String infor) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.validate_success_dialog);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvTitle = dialog.findViewById(R.id.tvTitleErr);
        TextView tvTitleInfor = dialog.findViewById(R.id.tvInforErr);

        tvTitle.setText(title);
        tvTitleInfor.setText(infor);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(AdminOrderActivity.this, AdminOrderActivity.class));
            }
        });

        dialog.show();
    }
}