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
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.model.Topping;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CustomMilkteaDto> customMilkteaDtoList;
    private ChangeNumberItemListener changeNumberItemListener;

    public CartAdapter(List<CustomMilkteaDto> customMilkteaDtoList, ChangeNumberItemListener changeNumberItemListener) {
        this.customMilkteaDtoList = customMilkteaDtoList;
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        CartLineitemBinding cartLineitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.cart_lineitem, parent, false);
        return new CartAdapter.CartViewHolder(cartLineitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        holder.setBinding(customMilkteaDtoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return customMilkteaDtoList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> title= new ObservableField<>();
        public ObservableField<String> quantity= new ObservableField<>();
        public ObservableField<String> toppings= new ObservableField<>();
        public ObservableField<String> price = new ObservableField<>();
        private CartLineitemBinding cartLineitemBinding;

        public CartViewHolder(CartLineitemBinding itemView) {
            super(itemView.getRoot());
            this.cartLineitemBinding = itemView;
        }

        public void setBinding(CustomMilkteaDto customMilkteaDto, int position) {
            if (cartLineitemBinding.getViewHolderCart() == null) {
                cartLineitemBinding.setViewHolderCart(this);
            }
            imgUrl.set(customMilkteaDto.getImgUrl());
            title.set(customMilkteaDto.getNameMilktea());
            quantity.set(String.valueOf(customMilkteaDto.getQuantity()));

            String detail = "Price: " + String.valueOf(customMilkteaDto.getTotalPriceOfItem()) + " VND"
                    + "\nSize " + customMilkteaDto.getSize()
                    + ", Ice: " + customMilkteaDto.getIceAmount() + ", Sugar: "
                    + customMilkteaDto.getSugarAmount() +
                    "\n" + "Toppings: ";
            List<String> toppingsList = customMilkteaDto.getListTopping();
            for (String name : toppingsList) {
                detail = detail + name + ", ";
            }
            toppings.set(detail.substring(0, detail.length() - 2));
            
            price.set(String.valueOf(customMilkteaDto.getTotalPriceOfItem() * customMilkteaDto.getQuantity()) + " VND");

            cartLineitemBinding.plusCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.change(true, customMilkteaDtoList.get(position));
                }
            });

            cartLineitemBinding.minusCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.change(false, customMilkteaDtoList.get(position));
                }
            });
            cartLineitemBinding.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.delete(customMilkteaDtoList.get(position));
                }
            });
        }
    }
}

