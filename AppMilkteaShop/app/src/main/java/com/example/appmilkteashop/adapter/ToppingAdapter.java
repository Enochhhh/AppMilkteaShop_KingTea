package com.example.appmilkteashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.appmilkteashop.R;

import androidx.core.view.ViewGroupKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmilkteashop.model.Topping;
import com.google.android.material.transition.Hold;

import java.util.List;

public class ToppingAdapter extends ArrayAdapter<Topping> {
    private Context mContext;
    private List<Topping> listTopping;
    private ToppingAdapter toppingAdapter;
    private boolean isFromView = false;

    public ToppingAdapter(Context context, int resource, List<Topping> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listTopping = objects;

        Topping topping = new Topping();
        topping.setName("Select toppings");
        this.listTopping.add(0, topping);
        this.toppingAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.spinner_topping_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.txtSpinItemTopping);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cbSpinItemTopping);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listTopping.get(position).getName());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listTopping.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int position = (Integer) compoundButton.getTag();

                if (!isFromView) {
                    listTopping.get(position).setSelected(isChecked);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
