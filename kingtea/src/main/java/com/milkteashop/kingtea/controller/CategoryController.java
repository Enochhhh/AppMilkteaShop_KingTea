package com.milkteashop.kingtea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Category;
import com.milkteashop.kingtea.service.CategoryService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/category")
public class CategoryController {
	private static final String pathApi = "/milkteashop/kingtea/category";
	
	@Autowired private CategoryService categoryService;
	
	@GetMapping("/getall")
	public ResponseEntity<Object> getAllCategory() {
		List<Category> listCategory = categoryService.getAllCategory();
		if (listCategory.isEmpty()) {
			throw new NotFoundValueException("Couldn't find all Category", pathApi + "/getall");
		}
		return ResponseEntity.ok(listCategory);
	}
}
