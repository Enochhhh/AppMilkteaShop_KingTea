package com.example.appmilkteashop.listener;

import com.example.appmilkteashop.dto.CustomMilkteaDto;
import com.example.appmilkteashop.model.Milktea;

public interface ChangeNumberItemListener {
    void change(boolean isPlus, Milktea milktea);

    void change(boolean isPlus, CustomMilkteaDto milktea);
    void delete(CustomMilkteaDto customMilkteaDto);
}
