package com.example.appmilkteashop.model;

import java.io.Serializable;
import java.util.List;

public class Milktea implements Serializable {
    private String milkTeaId;
    private String name;
    private int quantity;
    private int price;
    private int cost;
    private String imgUrl;
    private Category category;
    private boolean enabled;

    public String getMilkTeaId() {
        return milkTeaId;
    }

    public void setMilkTeaId(String milkTeaId) {
        this.milkTeaId = milkTeaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
