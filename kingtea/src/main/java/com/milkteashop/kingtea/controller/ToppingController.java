package com.milkteashop.kingtea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Topping;
import com.milkteashop.kingtea.service.ToppingService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/topping")
public class ToppingController {
	private static final String pathApi = "/milkteashop/kingtea/topping";
	
	@Autowired
	private ToppingService toppingService;
	
	@GetMapping("/getall")
	public ResponseEntity<Object> getAllTopping() {
		List<Topping> toppings = toppingService.getAll();
		
		if (toppings.isEmpty()) {
			throw new NotFoundValueException("Not found any topping value", pathApi);
		}
		return ResponseEntity.ok(toppings);
	}
}
