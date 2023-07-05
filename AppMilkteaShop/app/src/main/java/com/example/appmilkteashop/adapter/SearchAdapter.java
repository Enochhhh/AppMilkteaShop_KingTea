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
import com.example.appmilkteashop.databinding.SearchLineItemBinding;
import com.example.appmilkteashop.listener.ChangeNumberItemListener;
import com.example.appmilkteashop.listener.ChangeToDetailActivityListener;
import com.example.appmilkteashop.model.Milktea;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MilkteaViewHolder> {
    private List<Milktea> milkteaList;
    private ChangeNumberItemListener changeNumberItemListener;
    private ChangeToDetailActivityListener changeToDetailActivityListener;

    public SearchAdapter(List<Milktea> milktea, ChangeNumberItemListener changeNumberItemListener,
                                    ChangeToDetailActivityListener changeToDetailActivityListener) {
        this.milkteaList = milktea;
        this.changeNumberItemListener = changeNumberItemListener;
        this.changeToDetailActivityListener = changeToDetailActivityListener;
    }

    @NonNull
    @Override
    public SearchAdapter.MilkteaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        SearchLineItemBinding searchLineItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_line_item, parent, false);
        return new SearchAdapter.MilkteaViewHolder(searchLineItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MilkteaViewHolder holder, int position) {
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
        private SearchLineItemBinding searchLineItemBinding;

        public MilkteaViewHolder(SearchLineItemBinding itemView) {
            super(itemView.getRoot());
            this.searchLineItemBinding = itemView;
        }

        public void setBinding(Milktea milktea, int position) {
            if (searchLineItemBinding.getMilkteaViewHolder() == null) {
                searchLineItemBinding.setMilkteaViewHolder(this);
            }
            imgUrl.set(milktea.getImgUrl());
            title.set(milktea.getName());
            quantity.set(String.valueOf(milktea.getQuantity()));
            price.set(String.valueOf(milktea.getPrice()) + "₫");

            searchLineItemBinding.btnAddMilkteaByCate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNumberItemListener.change(true, milkteaList.get(position));
                }
            });

            searchLineItemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeToDetailActivityListener.changeActivity(milkteaList.get(position));
                }
            });
        }
    }
}

