package com.example.appmilkteashop.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String categoryId;
    private String name;
    private String description;
    private String imgUrl;
    private boolean enabled;

    public Category() {
    }

    public Category(String categoryId, String name, String description, String imgUrl, boolean enabled) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.enabled = enabled;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
