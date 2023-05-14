package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.CartLineitemBinding;
import com.example.appmilkteashop.databinding.CategoryLineitemBinding;
import com.example.appmilkteashop.databinding.SpecialLineitemBinding;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Milktea> milkteaList;

    public CartAdapter(List<Milktea> milkteaList) {
        this.milkteaList = milkteaList;
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
        holder.setBinding(milkteaList.get(position));
    }

    @Override
    public int getItemCount() {
        return milkteaList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> name= new ObservableField<>();
        public ObservableField<String> price = new ObservableField<>();
        private CartLineitemBinding cartLineitemBinding;

        public CartViewHolder(CartLineitemBinding itemView) {
            super(itemView.getRoot());
            this.cartLineitemBinding = itemView;
        }

        public void setBinding(Milktea milktea) {
            if (cartLineitemBinding.getViewHolderCart() == null) {
                cartLineitemBinding.setViewHolderCart(this);
            }
            imgUrl.set(milktea.getImgUrl());
            name.set(milktea.getName());
            price.set(String.valueOf(milktea.getPrice()) + "\n  VND");
        }
    }
}

