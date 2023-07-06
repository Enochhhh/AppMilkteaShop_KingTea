package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.MilkteaByCategoryAdapter;
import com.example.appmilkteashop.adapter.SearchAdapter;
import com.example.appmilkteashop.databinding.ActivityMilkteabycategoryBinding;
import com.example.appmilkteashop.databinding.ActivitySearchBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.listener.ChangeToDetailActivityListener;
import com.example.appmilkteashop.model.Milktea;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding activitySearchBinding;
    private SearchAdapter searchAdapter;
    private List<Milktea> milkteaList;
    public String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        keyWord = getIntent().getStringExtra("key_word");

        SetEvent();
        loadDataRcViewMilktea();
    }

    private void loadDataRcViewMilktea() {
        activitySearchBinding.rcViewMilktea.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");


        callApiGetMilkteaByKeyword("Bearer " + token, keyWord);
    }

    private void callApiGetMilkteaByKeyword(String token, String keyWord) {
        ApiHelper.apiService.getByKeyword(token, keyWord).enqueue(new Callback<List<Milktea>>() {
            @Override
            public void onResponse(Call<List<Milktea>> call, Response<List<Milktea>> response) {
                if (response.isSuccessful()) {
                    List<Milktea> milkteaList = response.body();
                    searchAdapter = new SearchAdapter(milkteaList, new ChangeNumberItemListener() {
                        @Override
                        public void change(boolean isPlus, Milktea milktea) {
                            callApiUpdateToCart(token, milktea, true);
                        }

                        @Override
                        public void delete(CustomMilkteaDto customMilkteaDto) {
                            return;
                        }

                        @Override
                        public void change(boolean isPlus, CustomMilkteaDto milktea) {
                            return;
                        }
                    }, new ChangeToDetailActivityListener(){
                        @Override
                        public void changeActivity(Object milktea) {
                            Intent intent = new Intent(SearchActivity.this, ShowDetailActivity.class);
                            intent.putExtra("tea_key", (Milktea) milktea);
                            startActivity(intent);
                        }
                    });
                    activitySearchBinding.rcViewMilktea.setAdapter(searchAdapter);
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(SearchActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(SearchActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Milktea>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Find milktea by keyword unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiUpdateToCart(String token, Milktea milktea, boolean isPlus) {
        if (isPlus) {
            CustomMilkteaDto customMilkteaDto = new CustomMilkteaDto();
            customMilkteaDto.setCustomMilkteaId(milktea.getMilkTeaId());
            customMilkteaDto.setSize("M");
            customMilkteaDto.setSugarAmount("100%");
            customMilkteaDto.setIceAmount("100%");
            customMilkteaDto.setEnabled(true);
            customMilkteaDto.setListTopping(new ArrayList<String>());
            customMilkteaDto.setQuantity(1);
            customMilkteaDto.setMilkTeaId(milktea.getMilkTeaId());

            ApiHelper.apiService.addMilkteaToCart(token, customMilkteaDto).enqueue(new Callback<ResponseStringDto>() {
                @Override
                public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                    if (response.isSuccessful()) {
                        ResponseStringDto stringDto = response.body();
                        Toast.makeText(SearchActivity.this, stringDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                        if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                            startActivity(new Intent(SearchActivity.this, ExceptionActivity.class));
                        }
                        Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                    Toast.makeText(SearchActivity.this, "Add milktea to cart unsuccessfully",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void SetEvent() {
        activitySearchBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
            }
        });

        activitySearchBinding.btnCartBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, CartActivity.class));
            }
        });

        activitySearchBinding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        activitySearchBinding.searchBar2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
                    String token = "Bearer " + sharedPreferences.getString("token", "");
                    String keyword = activitySearchBinding.searchBar2.getText().toString();
                    activitySearchBinding.searchBar2.setText("");

                    callApiGetMilkteaByKeyword(token, keyword);
                    return true;
                }
                return false;
            }
        });

        activitySearchBinding.btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, ContactActivity.class));
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
                startActivity(new Intent(SearchActivity.this, LoginActivity.class));
            }
        });

        ConstraintLayout btnOrder = (ConstraintLayout) dialog.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, OrderManagementActivity.class));
            }
        });

        ConstraintLayout btnChangePass = (ConstraintLayout) dialog.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, ChangePasswordActivity.class));
            }
        });

        dialog.show();
    }
}