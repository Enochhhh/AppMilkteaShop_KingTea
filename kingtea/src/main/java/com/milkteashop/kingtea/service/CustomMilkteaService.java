package com.milkteashop.kingtea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.CustomMilktea;
import com.milkteashop.kingtea.model.Milktea;
import com.milkteashop.kingtea.model.Topping;
import com.milkteashop.kingtea.repository.CustomMilkteaRepository;

@Service
public class CustomMilkteaService {
	@Autowired CustomMilkteaRepository customMilkteaRepository;
	
	/* If CustomMilktea already existed in database, this method will return this CustomMilktea, 
	opposite it will return null */
	public CustomMilktea addCustomMilktea(CustomMilktea customMilktea) {
		CustomMilktea responseCustom = checkCustomMilkteaIsExisted(customMilktea);
		if (responseCustom == null) {
			setPriceAndCost(customMilktea);
			return customMilkteaRepository.save(customMilktea);
		}
		return responseCustom;
	}
	
	private void setPriceAndCost(CustomMilktea customMilktea) {
		int price = customMilktea.getMilkTea().getPrice();
		int cost = customMilktea.getMilkTea().getCost();
		
		switch(customMilktea.getSize()) {
			case "L": 
				price += 10000;
				cost += 1000;
				break;
			case "XL":
				price += 20000;
				cost += 2000;
				break;
			default:
				break;
		}
		
		List<Topping> toppings = customMilktea.getListTopping();
		for (Topping topping : toppings) {
			price += topping.getPrice();
			cost += topping.getCost();
		}
		
		customMilktea.setPrice(price);
		customMilktea.setCost(cost);
	}
	
	public CustomMilktea checkCustomMilkteaIsExisted(CustomMilktea customCheck) {
		List<CustomMilktea> customMilkteas = getCustomMilkteaByMilktea(customCheck.getMilkTea());
		
		if (customMilkteas == null) {
			return null;
		}
		
		for (CustomMilktea custom : customMilkteas) {
			
			if (customCheck.getSize().equals(custom.getSize()) 
					&& customCheck.getSugarAmount().equals(custom.getSugarAmount())
					&& customCheck.getIceAmount().equals(custom.getIceAmount())) {
				List<Topping> toppingCustomCheck = customCheck.getListTopping();
				List<Topping> toppingCustom = custom.getListTopping();
				
				if (toppingCustomCheck.size() == toppingCustom.size()) {
					Boolean check = true;
					for (Topping toppingCheck : toppingCustomCheck) {
						if (!toppingCustom.contains(toppingCheck)) {
							check = false;
							break;
						}
					}
					if (check) {
						return custom;
					}
				} 	
			} 
		}
		return null;
	}
	
	public List<CustomMilktea> getCustomMilkteaByMilktea(Milktea milktea) {
		return customMilkteaRepository.findByMilkTeaAndEnabledTrue(milktea);
	}
	
	public CustomMilktea getById(String id) {
		return customMilkteaRepository.findById(id).orElseThrow(
				() -> new NotFoundValueException("Couldn't find CustomMilktea by Id", "/milkteashop/kingtea/custommilktea"));
	}
}
