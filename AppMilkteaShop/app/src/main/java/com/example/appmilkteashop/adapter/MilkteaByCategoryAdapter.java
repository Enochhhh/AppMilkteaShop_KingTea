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
import com.example.appmilkteashop.databinding.MilkteaLineitemBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.listener.ChangeToDetailActivityListener;
import com.example.appmilkteashop.model.Milktea;

import java.util.List;

public class MilkteaByCategoryAdapter extends RecyclerView.Adapter<MilkteaByCategoryAdapter.MilkteaViewHolder> {
    private List<Milktea> milkteaList;
    private ChangeNumberItemListener changeNumberItemListener;
    private ChangeToDetailActivityListener changeToDetailActivityListener;

    public MilkteaByCategoryAdapter(List<Milktea> milktea, ChangeNumberItemListener changeNumberItemListener,
                                    ChangeToDetailActivityListener changeToDetailActivityListener) {
        this.milkteaList = milktea;
        this.changeNumberItemListener = changeNumberItemListener;
        this.changeToDetailActivityListener = changeToDetailActivityListener;
    }

    @NonNull
    @Override
    public MilkteaByCategoryAdapter.MilkteaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        MilkteaLineitemBinding milkteaLineitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.milktea_lineitem, parent, false);
        return new MilkteaByCategoryAdapter.MilkteaViewHolder(milkteaLineitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkteaByCategoryAdapter.MilkteaViewHolder holder, int position) {
        holder.setBinding(milkteaList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return milkteaList.size();
    }

    public class MilkteaViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> title= new ObservableField<>();
        public ObservableField<String> quantity= new ObservableField<>();
        public ObservableField<String> price = new ObservableField<>();
        private MilkteaLineitemBinding milkteaLineitemBinding;

        public MilkteaViewHolder(MilkteaLineitemBinding itemView) {
            super(itemView.getRoot());
            this.milkteaLineitemBinding = itemView;
        }

        public void setBinding(Milktea milktea, int position) {
            if (milkteaLineitemBinding.getMilkteaViewHolder() == null) {
                milkteaLineitemBinding.setMilkteaViewHolder(this);
            }
            imgUrl.set(milktea.getImgUrl());
            title.set(milktea.getName());
            quantity.set(String.valueOf(milktea.getQuantity()));
            price.set(String.valueOf(milktea.getPrice()) + "₫");

            milkteaLineitemBinding.btnAddMilkteaByCate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.change(true, milkteaList.get(position));
                }
            });

            milkteaLineitemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeToDetailActivityListener.changeActivity(milkteaList.get(position));
                }
            });
        }
    }
}

