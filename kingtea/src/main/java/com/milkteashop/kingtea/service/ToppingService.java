package com.milkteashop.kingtea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.model.Topping;
import com.milkteashop.kingtea.repository.ToppingRepository;

@Service
public class ToppingService {
	@Autowired ToppingRepository toppingRepository;
	
	public List<Topping> getAll() {
		return toppingRepository.findAll();
	}
}
