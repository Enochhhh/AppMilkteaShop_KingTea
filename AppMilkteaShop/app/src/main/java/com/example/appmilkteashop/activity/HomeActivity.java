package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appmilkteashop.R;

public class HomeActivity extends AppCompatActivity {
    private ImageView imvAva;
    private ImageView imvBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Mapping();

        // Round corner image Avatar
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Android%2Fprofile_admin.jpg?alt=media&token=58bb49bf-18d0-4272-877e-96ca4d872c40")
                .circleCrop()
                .into(imvAva);

        // Round corner image Banner
        Glide.with(this)
                .load(R.drawable.milktea_banner)
                .apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(80)))
                .into(imvBanner);
        /*
        TextView tv = findViewById(R.id.textView11);
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = sharedPreferences.getString("token", "");
        tv.setText(token);*/
    }

    private void Mapping() {
        imvAva = (ImageView) findViewById(R.id.imvAvatar);
        imvBanner = (ImageView) findViewById(R.id.imvBanner);
    }
}