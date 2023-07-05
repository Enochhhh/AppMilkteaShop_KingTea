package com.milkteashop.kingtea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Category;
import com.milkteashop.kingtea.model.Milktea;
import com.milkteashop.kingtea.service.MilkteaService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/milktea")
public class MilkteaController {
	private static final String pathApi = "/milkteashop/kingtea/milktea";
	
	@Autowired MilkteaService milkteaService;
	
	@GetMapping("/getbycategoryname")
	public ResponseEntity<Object> getMilkteaByCategoryName(@RequestParam String name) {
		List<Milktea> listMilkTea = milkteaService.getMilkteaByCategoryName(name);
		
		if (listMilkTea == null) {
			throw new NotFoundValueException("Couldn't find any milktea", pathApi + "/getbycategoryname");
		}
		return ResponseEntity.ok(listMilkTea);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<Object> getAllMilktea() {
		List<Milktea> milkteas = milkteaService.getAllMilktea();
		
		if (milkteas == null) {
			throw new NotFoundValueException("Couldn't find any milktea", pathApi + "/getall");
		}
		return ResponseEntity.ok(milkteas);
	}
	
	@GetMapping("/getbycategory")
	public ResponseEntity<Object> getByCategory(@RequestParam String categoryName) {
		List<Milktea> milkteas = milkteaService.getByCategory(categoryName);
		
		if (milkteas == null) {
			throw new NotFoundValueException("Couldn't find any milktea", pathApi + "/getall");
		}
		return ResponseEntity.ok(milkteas);
	}
	
	@GetMapping("/getcategory")
	public ResponseEntity<Object> getCategory(@RequestParam String milkteaId) {
		Category category = milkteaService.getCategory(milkteaId);
		
		if (category == null) {
			throw new NotFoundValueException("Couldn't find category of specified milktea", pathApi + "/getcategory");
		}
		return ResponseEntity.ok(category);
	}
	
	@GetMapping("/getkeyword")
	public ResponseEntity<Object> getKeyword(@RequestParam String keyword) {
		List<Milktea> milkteas = milkteaService.getByKeyword(keyword);
		
		if (milkteas == null || milkteas.isEmpty()) {
			throw new NotFoundValueException("Couldn't find any milktea with key word \"" + keyword + "\"", pathApi + "/getkeyword");
		}
		return ResponseEntity.ok(milkteas);
	}
}
