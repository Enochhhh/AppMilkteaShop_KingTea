package com.example.appmilkteashop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appmilkteashop.databinding.CategoryLineitemBinding;
import com.example.appmilkteashop.listener.ChangeActivityListener;
import com.example.appmilkteashop.model.Category;
import com.example.appmilkteashop.model.User;

import java.util.List;
import com.example.appmilkteashop.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private ChangeActivityListener changeActivityListener;

    public CategoryAdapter(List<Category> categoryList, ChangeActivityListener changeActivityListener) {

        this.categoryList = categoryList;
        this.changeActivityListener = changeActivityListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        CategoryLineitemBinding categoryLineitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_lineitem, parent, false);
        return new CategoryViewHolder(categoryLineitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.setBinding(categoryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> imgUrl = new ObservableField<>();
        public ObservableField<String> nameCategory = new ObservableField<>();
        private CategoryLineitemBinding categoryLineitemBinding;

        public CategoryViewHolder(CategoryLineitemBinding itemView) {
            super(itemView.getRoot());
            this.categoryLineitemBinding = itemView;
        }

        public void setBinding(Category category, int position) {
            if (categoryLineitemBinding.getCategory() == null) {
                categoryLineitemBinding.setCategory(this);
            }
            imgUrl.set(category.getImgUrl());
            nameCategory.set(category.getName());

            categoryLineitemBinding.imvLineCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeActivityListener.changeActivity(categoryList.get(position).getCategoryId());
                }
            });

            categoryLineitemBinding.ctLineCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeActivityListener.changeActivity(categoryList.get(position).getCategoryId());
                }
            });
        }

    }
}
