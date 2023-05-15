package com.milkteashop.kingtea.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.dto.CustomMilkteaDto;
import com.milkteashop.kingtea.dto.ResponseStringDto;
import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.service.CartLineService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/cart")
public class CartLineController {
	
	private static final String pathApi = "/milkteashop/kingtea/cart";
	
	@Autowired CartLineService cartLineService;
	
	@GetMapping("/getmilktea")
	public ResponseEntity<Object> getMilkteaInCart(HttpServletRequest request) {
		List<CustomMilkteaDto> listMilkTeaInCart = cartLineService.getMilkteaInCart(request);
		
		if (listMilkTeaInCart == null) {
			throw new NotFoundValueException("Couldn't get cart", pathApi + "/getmilktea");
		}
		return ResponseEntity.ok(listMilkTeaInCart);
	}
	
	@PostMapping("/addtocart")
	public ResponseEntity<Object> addMilkteaToCart(@RequestBody CustomMilkteaDto milkteaRequest, 
			HttpServletRequest request) {
		cartLineService.addMilkteaToCart(milkteaRequest, request, milkteaRequest.getQuantity());
		
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Updated your cart");
		
		return ResponseEntity.ok(message);
	}
	
	@PostMapping("/increaseitemoncart")
	public ResponseEntity<Object> increaseMilktea(HttpServletRequest request, 
			@RequestBody CustomMilkteaDto milkteaRequest) {
		cartLineService.increaseMilktea(request, milkteaRequest);
		
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Updated your cart");
		return ResponseEntity.ok(message);
	}
	
	@PostMapping("/decreaseitemoncart")
	public ResponseEntity<Object> decreaseMilktea(HttpServletRequest request, 
			@RequestBody CustomMilkteaDto milkteaRequest) {
		cartLineService.decreaseMilktea(request, milkteaRequest);
		
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Updated your cart");
		return ResponseEntity.ok(message);
	}
	
	@DeleteMapping("/deleteitem") 
	public ResponseEntity<Object> deleteCartLine(HttpServletRequest request, 
			@RequestParam String customMilkteaDto) {
		cartLineService.deleteCartLine(request, customMilkteaDto);
		
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Updated your cart");
		return ResponseEntity.ok(message);
	}
}
