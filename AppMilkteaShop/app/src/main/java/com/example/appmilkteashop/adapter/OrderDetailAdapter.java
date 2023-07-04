package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.CartLineitemBinding;
import com.example.appmilkteashop.databinding.OrderDetailItemBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    private List<CustomMilkteaDto> customMilkteaDtoList;

    public OrderDetailAdapter(List<CustomMilkteaDto> customMilkteaDtoList) {
        this.customMilkteaDtoList = customMilkteaDtoList;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        OrderDetailItemBinding orderDetailItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_detail_item, parent, false);
        return new OrderDetailAdapter.OrderDetailViewHolder(orderDetailItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        holder.setBinding(customMilkteaDtoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return customMilkteaDtoList.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> title= new ObservableField<>();
        public ObservableField<String> quantity= new ObservableField<>();
        public ObservableField<String> toppings= new ObservableField<>();
        public ObservableField<String> price = new ObservableField<>();
        private OrderDetailItemBinding orderDetailItemBinding;

        public OrderDetailViewHolder(OrderDetailItemBinding itemView) {
            super(itemView.getRoot());
            this.orderDetailItemBinding = itemView;
        }

        public void setBinding(CustomMilkteaDto customMilkteaDto, int position) {
            if (orderDetailItemBinding.getOrderDetailItem() == null) {
                orderDetailItemBinding.setOrderDetailItem(this);
            }
            imgUrl.set(customMilkteaDto.getImgUrl());
            title.set(customMilkteaDto.getNameMilktea());
            quantity.set("x" + String.valueOf(customMilkteaDto.getQuantity()));

            String detail = "Price: " + String.valueOf(customMilkteaDto.getTotalPriceOfItem()) + "₫"
                    + "\nSize " + customMilkteaDto.getSize()
                    + "\nIce: " + customMilkteaDto.getIceAmount() + ", Sugar: "
                    + customMilkteaDto.getSugarAmount() +
                    "\n" + "Toppings: ";
            List<String> toppingsList = customMilkteaDto.getListTopping();
            for (String name : toppingsList) {
                detail = detail + name + ", ";
            }
            toppings.set(detail.substring(0, detail.length() - 2));
            price.set("Total: "+ String.valueOf(customMilkteaDto.getTotalPriceOfItem() * customMilkteaDto.getQuantity()) + "₫");
        }
    }
}
