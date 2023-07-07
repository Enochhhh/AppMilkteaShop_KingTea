package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.OrderAdminLineItemBinding;
import com.example.appmilkteashop.databinding.OrderLineItemBinding;
import com.example.appmilkteashop.listener.OrderAdminReceiveListener;
import com.example.appmilkteashop.listener.OrderManagementListener;
import com.example.appmilkteashop.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderAdminReceiveAdapter extends RecyclerView.Adapter<OrderAdminReceiveAdapter.OrderViewHolder> {
    private List<Order> orders;
    private OrderAdminReceiveListener orderAdminReceiveListener;

    public OrderAdminReceiveAdapter(List<Order> orders, OrderAdminReceiveListener orderAdminReceiveListener) {
        this.orders = orders;
        this.orderAdminReceiveListener = orderAdminReceiveListener;
    }

    @NonNull
    @Override
    public OrderAdminReceiveAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        OrderAdminLineItemBinding orderAdminLineItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_admin_line_item, parent, false);
        return new OrderAdminReceiveAdapter.OrderViewHolder(orderAdminLineItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdminReceiveAdapter.OrderViewHolder holder, int position) {
        holder.setBinding(orders.get(position), position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> paymentMethod = new ObservableField<>();
        public ObservableField<String> dateCreatedOrder = new ObservableField<>();
        public ObservableField<String> totalPrice = new ObservableField<>();

        private OrderAdminLineItemBinding orderAdminLineItemBinding;

        public OrderViewHolder(OrderAdminLineItemBinding orderAdminLineItemBinding) {
            super(orderAdminLineItemBinding.getRoot());
            this.orderAdminLineItemBinding = orderAdminLineItemBinding;
        }

        public void setBinding(Order order, int position) {
            if (orderAdminLineItemBinding.getOrderViewHolder() == null) {
                orderAdminLineItemBinding.setOrderViewHolder(this);
            }
            imgUrl.set(orderAdminReceiveListener.getImgUrlOrder(order.getOrderId()));
            paymentMethod.set("Payments Method: " + order.getPaymentMethod());

            String formatStr = formatDate(order.getDateCreated());
            dateCreatedOrder.set(formatStr);
            totalPrice.set("Total Price: " + String.valueOf(order.getTotalPrice()) + "₫");

            orderAdminLineItemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderAdminReceiveListener.showDetailOrder(order.getOrderId());
                }
            });

            orderAdminLineItemBinding.btnReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderAdminReceiveListener.receiveOrder(order.getOrderId());
                }
            });

            orderAdminLineItemBinding.tvCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderAdminReceiveListener.cancelOrder(order.getOrderId());
                }
            });
        }

        private String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            sdf.applyPattern("EEE, d MMM yyyy");
            return sdf.format(date);
        }
    }
}
