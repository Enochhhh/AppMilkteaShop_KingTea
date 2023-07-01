package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmilkteashop.R;
import com.example.appmilkteashop.adapter.ToppingAdapter;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.dto.ResponseStringDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;
import com.example.appmilkteashop.model.Topping;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailActivity extends AppCompatActivity {
    Spinner spinner_size;
    Spinner spinner_ice;
    Spinner spinner_sugar;
    Spinner spinner_topping;
    TextView txtDescrip;
    ImageView foodPic;
    TextView totalPriceDetail;
    TextView txtNumberItem;
    ImageView plusItem;
    ImageView subItem;
    TextView priceDetail;
    TextView title;
    ImageView imvBottomCart;
    ImageView imvBottomSetting;
    ImageView imvBottomHome;
    TextView btnAddToCart;
    Milktea milktea;

    List<Topping> toppings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        Mapping();
        setEvent();
        setAdapterToSpinner();


        milktea = (Milktea) getIntent().getSerializableExtra("tea_key");
        // Load image Milktea
        Glide.with(this)
                .load(milktea.getImgUrl())
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(80)))
                .into(foodPic);
        // Load description
        int quantity = Integer.parseInt(txtNumberItem.getText().toString());
        totalPriceDetail.setText(String.valueOf(milktea.getPrice() * quantity) + " VND");
        priceDetail.setText(String.valueOf(milktea.getPrice()) + " VND");
        title.setText(milktea.getName());

        toppings = callApiGetAllTopping();
        ToppingAdapter toppingAdapter = new ToppingAdapter(ShowDetailActivity.this, 0, toppings);
        spinner_topping.setAdapter(toppingAdapter);
        callApiGetCategoryOfMilktea(milktea.getMilkTeaId());
    }

    public void callApiAddToCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        CustomMilkteaDto customMilkteaDto = new CustomMilkteaDto();
        customMilkteaDto.setMilkTeaId(milktea.getMilkTeaId());
        customMilkteaDto.setSize(spinner_size.getSelectedItem().toString());
        customMilkteaDto.setIceAmount(spinner_ice.getSelectedItem().toString());
        customMilkteaDto.setSugarAmount(spinner_sugar.getSelectedItem().toString());
        customMilkteaDto.setQuantity(Integer.parseInt(txtNumberItem.getText().toString()));
        customMilkteaDto.setEnabled(true);

        List<String> toppingSelected = new ArrayList<>();
        for (Topping topping : toppings) {
            if (topping.isSelected()) {
                toppingSelected.add(topping.getName());
            }
        }
        customMilkteaDto.setListTopping(toppingSelected);

        ApiHelper.apiService.addMilkteaToCart(token, customMilkteaDto).enqueue(new Callback<ResponseStringDto>() {
            @Override
            public void onResponse(Call<ResponseStringDto> call, Response<ResponseStringDto> response) {
                if (response.isSuccessful()) {
                    ResponseStringDto stringDto = response.body();
                    Toast.makeText(ShowDetailActivity.this, stringDto.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error.getStatus().equals("INTERNAL_SERVER_ERROR") || error == null) {
                        startActivity(new Intent(ShowDetailActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ShowDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStringDto> call, Throwable t) {
                Toast.makeText(ShowDetailActivity.this, "Add milktea to cart unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Topping> callApiGetAllTopping() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");
        final List<Topping> toppingList = new ArrayList<>();

        ApiHelper.apiService.getAllToppings(token).enqueue(new Callback<List<Topping>>() {
            @Override
            public void onResponse(Call<List<Topping>> call, Response<List<Topping>> response) {
                if (response.isSuccessful()) {
                    List<Topping> temp = response.body();
                    for (Topping item : temp) {
                        item.setSelected(false);
                        toppingList.add(item);
                    }
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ShowDetailActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ShowDetailActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Topping>> call, Throwable t) {
                Toast.makeText(ShowDetailActivity.this, "Find topping unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });

        return toppingList;
    }

    public void callApiGetCategoryOfMilktea(String milkteaId) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.getCategoryOfMilktea(token, milkteaId).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    Category category = response.body();
                    txtDescrip.setText("Description: " + category.getDescription());
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ShowDetailActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ShowDetailActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(ShowDetailActivity.this, "Find category unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAdapterToSpinner() {
        ArrayAdapter<CharSequence> adapter_size = ArrayAdapter.createFromResource(this,
                R.array.size_array, R.layout.spinner_item);
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_size.setAdapter(adapter_size);

        ArrayAdapter<CharSequence> adapter_ice = ArrayAdapter.createFromResource(this,
                R.array.amount_array, R.layout.spinner_item);
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ice.setAdapter(adapter_ice);

        ArrayAdapter<CharSequence> adapter_sugar = ArrayAdapter.createFromResource(this,
                R.array.amount_array, R.layout.spinner_item);
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sugar.setAdapter(adapter_sugar);
    }

    public void Mapping() {
        spinner_size = (Spinner) findViewById(R.id.spinnerSize);
        spinner_ice = (Spinner) findViewById(R.id.spinnerIce);
        spinner_sugar = (Spinner) findViewById(R.id.spinnerSugar);
        spinner_topping = (Spinner) findViewById(R.id.spinnerTopping);
        txtDescrip = (TextView) findViewById(R.id.txtDescripDetail);
        foodPic = (ImageView) findViewById(R.id.foodPic);
        totalPriceDetail = (TextView) findViewById(R.id.totalPriceDetail);
        txtNumberItem = (TextView) findViewById(R.id.tvNumberItem);
        plusItem = (ImageView) findViewById(R.id.plusCartBtn);
        subItem = (ImageView) findViewById(R.id.minusCartBtn);
        priceDetail = (TextView) findViewById(R.id.priceDetailTxt);
        title = (TextView) findViewById(R.id.txtTitleDetail);
        imvBottomCart = (ImageView) findViewById(R.id.imvBottomCart);
        imvBottomSetting = (ImageView) findViewById(R.id.imvBottomSetting);
        imvBottomHome = (ImageView) findViewById(R.id.imvBottomHome);
        btnAddToCart = (TextView) findViewById(R.id.btnAddToCartDetail);
    }

    public void setEvent() {
        plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(txtNumberItem.getText().toString()) + 1;
                txtNumberItem.setText(String.valueOf(quantity));

                String price_string = priceDetail.getText().toString();
                int price = Integer.parseInt(price_string.substring(0, price_string.length() - 4));
                totalPriceDetail.setText(String.valueOf(price * quantity) + " VND");
            }
        });

        subItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(txtNumberItem.getText().toString()) - 1;
                if (quantity == 0) {
                    return;
                }
                txtNumberItem.setText(String.valueOf(quantity));
                String price_string = priceDetail.getText().toString();
                int price = Integer.parseInt(price_string.substring(0, price_string.length() - 4));
                totalPriceDetail.setText(String.valueOf(price * quantity) + " VND");
            }
        });

        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String size = (String) adapterView.getItemAtPosition(i);
                String price_string = priceDetail.getText().toString();
                int price = Integer.parseInt(price_string.substring(0, price_string.length() - 4));
                int quantity = Integer.parseInt(txtNumberItem.getText().toString());

                switch (size) {
                    case "L": price = milktea.getPrice() +  10000; break;
                    case "XL": price = milktea.getPrice() + 20000; break;
                    default: price = milktea.getPrice();
                }
                totalPriceDetail.setText(String.valueOf(price * quantity) + " VND");
                priceDetail.setText(String.valueOf(price ) + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        imvBottomCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetailActivity.this, CartActivity.class));
            }
        });

        imvBottomSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting();
            }
        });

        imvBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetailActivity.this, HomeActivity.class));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiAddToCart();
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
                startActivity(new Intent(ShowDetailActivity.this, LoginActivity.class));
            }
        });
        dialog.show();
    }
}