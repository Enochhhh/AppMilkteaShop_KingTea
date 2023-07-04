package com.example.appmilkteashop.listener;

import com.example.appmilkteashop.model.Order;

public interface OrderManagementListener {
    void showDetailOrder(String orderId);
    void paymentOrder(Order order);
    void cancelOrder(String orderId);
    String getImgUrlOrder(String orderId);
}
