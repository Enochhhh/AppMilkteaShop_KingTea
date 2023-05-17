package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.CategoryLineitemBinding;
import com.example.appmilkteashop.databinding.SpecialLineitemBinding;
import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.Milktea;

import java.util.List;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.SpecialViewHolder> {
    private List<Milktea> milkteaList;
    private ChangeNumberItemListener changeNumberItemListener;

    public SpecialAdapter(List<Milktea> milkteaList, ChangeNumberItemListener changeNumberItemListener) {
        this.milkteaList = milkteaList;
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public SpecialAdapter.SpecialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        SpecialLineitemBinding specialLineitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.special_lineitem, parent, false);
        return new SpecialAdapter.SpecialViewHolder(specialLineitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialAdapter.SpecialViewHolder holder, int position) {
        holder.setBinding(milkteaList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return milkteaList.size();
    }

    public class SpecialViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();

        public ObservableField<String> name= new ObservableField<>();
        public ObservableField<String> price = new ObservableField<>();
        private SpecialLineitemBinding specialLineitemBinding;

        // private ChangeNumberItemListener changeNumberItemListener;

        public SpecialViewHolder(SpecialLineitemBinding itemView/*ChangeNumberItemListener changeNumberItemListener*/) {
            super(itemView.getRoot());
            this.specialLineitemBinding = itemView;
            //this.changeNumberItemListener = changeNumberItemListener;
        }

        public void setBinding(Milktea milktea, int position) {
            if (specialLineitemBinding.getViewHolderSpecial() == null) {
                specialLineitemBinding.setViewHolderSpecial(this);
            }
            imgUrl.set(milktea.getImgUrl());
            name.set(milktea.getName());
            price.set(String.valueOf(milktea.getPrice()) + "\n  VND");

            specialLineitemBinding.addBtnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.change(true, milkteaList.get(position));
                }
            });
        }
    }
}

