package com.example.appmilkteashop.listener;

import com.example.appmilkteashop.model.Order;

public interface OrderAdminReceiveListener {
    void showDetailOrder(String orderId);
    void receiveOrder(String orderId);
    void cancelOrder(String orderId);
    String getImgUrlOrder(String orderId);
}
