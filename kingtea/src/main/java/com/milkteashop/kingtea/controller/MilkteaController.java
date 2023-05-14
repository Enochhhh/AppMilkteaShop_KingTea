package com.milkteashop.kingtea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
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
}