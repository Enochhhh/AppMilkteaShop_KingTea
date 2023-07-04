package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.MilkteaLineitemBinding;
import com.example.appmilkteashop.databinding.OrderLineItemBinding;
import com.example.appmilkteashop.listener.ChangeActivityListener;
import com.example.appmilkteashop.listener.ChangeToDetailActivityListener;
import com.example.appmilkteashop.listener.OrderManagementListener;
import com.example.appmilkteashop.model.Order;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OrderManagementAdapter extends RecyclerView.Adapter<OrderManagementAdapter.OrderViewHolder> {
    private List<Order> orders;
    private OrderManagementListener orderManagementListener;

    public OrderManagementAdapter(List<Order> orders, OrderManagementListener orderManagementListener) {
        this.orders = orders;
        this.orderManagementListener = orderManagementListener;
    }

    @NonNull
    @Override
    public OrderManagementAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        OrderLineItemBinding orderLineItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_line_item, parent, false);
        return new OrderManagementAdapter.OrderViewHolder(orderLineItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderManagementAdapter.OrderViewHolder holder, int position) {
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

        private OrderLineItemBinding orderLineItemBinding;

        public OrderViewHolder(OrderLineItemBinding orderLineItemBinding) {
            super(orderLineItemBinding.getRoot());
            this.orderLineItemBinding = orderLineItemBinding;
        }

        public void setBinding(Order order, int position) {
            if (orderLineItemBinding.getOrderViewHolder() == null) {
                orderLineItemBinding.setOrderViewHolder(this);
            }
            imgUrl.set(orderManagementListener.getImgUrlOrder(order.getOrderId()));
            paymentMethod.set("Payments Method: " + order.getPaymentMethod());

            String formatStr = formatDate(order.getDateCreated());
            dateCreatedOrder.set(formatStr);
            totalPrice.set("Total Price: " + String.valueOf(order.getTotalPrice()) + "₫");

            orderLineItemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderManagementListener.showDetailOrder(order.getOrderId());
                }
            });

            orderLineItemBinding.btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderManagementListener.paymentOrder(order);
                }
            });

            orderLineItemBinding.tvCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderManagementListener.cancelOrder(order.getOrderId());
                }
            });

            if (order.getState().equals("Waiting Accept")) {
                orderLineItemBinding.tvStateWaiting.setVisibility(View.VISIBLE);
                orderLineItemBinding.tvCancelBtn.setVisibility(View.VISIBLE);

                orderLineItemBinding.btnPayment.setVisibility(View.GONE);
                orderLineItemBinding.tvStateAccepted.setVisibility(View.GONE);
                orderLineItemBinding.tvStateUnpaid.setVisibility(View.GONE);
                orderLineItemBinding.tvStateCancelled.setVisibility(View.GONE);
            } else if (order.getState().equals("Accepted")) {
                orderLineItemBinding.tvStateAccepted.setVisibility(View.VISIBLE);

                orderLineItemBinding.tvCancelBtn.setVisibility(View.GONE);
                orderLineItemBinding.btnPayment.setVisibility(View.GONE);
                orderLineItemBinding.tvStateWaiting.setVisibility(View.GONE);
                orderLineItemBinding.tvStateUnpaid.setVisibility(View.GONE);
                orderLineItemBinding.tvStateCancelled.setVisibility(View.GONE);
            } else if(order.getState().equals("Unpaid Order")) {
                orderLineItemBinding.tvStateUnpaid.setVisibility(View.VISIBLE);
                orderLineItemBinding.tvCancelBtn.setVisibility(View.VISIBLE);
                orderLineItemBinding.btnPayment.setVisibility(View.VISIBLE);

                orderLineItemBinding.tvStateWaiting.setVisibility(View.GONE);
                orderLineItemBinding.tvStateAccepted.setVisibility(View.GONE);
                orderLineItemBinding.tvStateCancelled.setVisibility(View.GONE);
            } else {
                orderLineItemBinding.tvStateCancelled.setVisibility(View.VISIBLE);

                orderLineItemBinding.tvCancelBtn.setVisibility(View.GONE);
                orderLineItemBinding.btnPayment.setVisibility(View.GONE);
                orderLineItemBinding.tvStateWaiting.setVisibility(View.GONE);
                orderLineItemBinding.tvStateUnpaid.setVisibility(View.GONE);
                orderLineItemBinding.tvStateAccepted.setVisibility(View.GONE);
            }
        }

        private String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            sdf.applyPattern("EEE, d MMM yyyy");
            return sdf.format(date);
        }
    }
}
