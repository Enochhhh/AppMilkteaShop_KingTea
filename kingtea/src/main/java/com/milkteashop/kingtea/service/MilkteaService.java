package com.milkteashop.kingtea.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Category;
import com.milkteashop.kingtea.model.Milktea;
import com.milkteashop.kingtea.repository.CategoryRepository;
import com.milkteashop.kingtea.repository.MilkTeaRepository;

@Service
public class MilkteaService {
	@Autowired MilkTeaRepository milkTeaRepository;
	@Autowired CategoryRepository categoryRepository;
	
	public Milktea getMilkteaById(String id) {
		return milkTeaRepository.findById(id).orElseThrow(
				() -> new NotFoundValueException("Couldn't find milktea by id", "milkteashop/kingtea/milktea"));
	}
	
	public List<Milktea> getMilkteaByCategoryName(String name) {
		Category category = categoryRepository.findByName(name);
		
		if (category == null) {
			return null;
		}
			
		List<Milktea> temp = category.getListMilktea(); 
		List<Milktea> milkteas = new ArrayList<>();
		for(Milktea milktea : temp) {
			if (milktea.isEnabled()) {
				milkteas.add(milktea);
			}
		}
		
		return milkteas;
	}
	
	public Milktea saveMilktea(Milktea milktea) {
		return milkTeaRepository.save(milktea);
	}
	
	public List<Milktea> getAllMilktea() {
		return milkTeaRepository.findAll();
	}
	
	public List<Milktea> getByCategory(String name) {
		Category category = categoryRepository.findByName(name);
		return milkTeaRepository.findByCategory(category);
	}
	
	public Category getCategory(String milkteaId) {
		Milktea milktea = getMilkteaById(milkteaId);
		return milktea.getCategory();
	}
	
	public List<Milktea> getByKeyword(String keyWord) {
		List<Milktea> milkteas = milkTeaRepository.findByEnabledTrueAndNameContaining(keyWord);
		return milkteas;
	}
}
