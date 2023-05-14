package com.milkteashop.kingtea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.model.Category;
import com.milkteashop.kingtea.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory() {
		return categoryRepository.findByEnabledTrue();
	}
}
