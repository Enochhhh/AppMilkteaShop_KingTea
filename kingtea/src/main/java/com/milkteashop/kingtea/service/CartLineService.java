package com.milkteashop.kingtea.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.dto.CustomMilkteaDto;
import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.CartLine;
import com.milkteashop.kingtea.model.CustomMilktea;
import com.milkteashop.kingtea.model.Topping;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.CartLineRepository;
import com.milkteashop.kingtea.repository.ToppingRepository;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
public class CartLineService {
	
	@Autowired CartLineRepository cartLineRepository; 
	@Autowired UserRepository userRepository;
	@Autowired CustomMilkteaService customMilkteaService;
	@Autowired MilkteaService milkteaService;
	@Autowired JwtUtils jwtUtils;
	@Autowired ToppingRepository toppingRepository;
	
	public List<CustomMilkteaDto> getMilkteaInCart(HttpServletRequest request) {
	
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElseThrow(
				() -> new NotFoundValueException("Couldn't find User", "/milkteashop/kingtea/cart/getmilktea"));
		
		List<CartLine> cart = user.getListCartLine();
		List<CustomMilkteaDto> customMilkteaDtos = new ArrayList<>();
		
		for (CartLine line : cart) {
			CustomMilktea customMilkTea = line.getCustomMilktea();
			CustomMilkteaDto dto = convertCustomMilkTeaEntityToDto(customMilkTea);
			dto.setQuantity(line.getQuantity());
			dto.setTotalPriceOfItem(line.getTotalPriceOnLine() / line.getQuantity());
			customMilkteaDtos.add(dto);
		}
		
		return customMilkteaDtos;
	}
	
	public CartLine addMilkteaToCart(CustomMilkteaDto milkteaRequestDto, HttpServletRequest request, int quantity) {
		
		CustomMilktea milkteaRequest = convertCustomMilkTeaDtoToEntity(milkteaRequestDto);
		
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElseThrow(
					() -> new NotFoundValueException("Couldn't find User", "/milkteashop/kingtea/cart/getmilktea"));
		
		
		milkteaRequest = customMilkteaService.addCustomMilktea(milkteaRequest); 
			
		CartLine cartLine = cartLineRepository.findByUserAndCustomMilktea(user, milkteaRequest);
		
		if (cartLine == null) {
			cartLine = new CartLine();
			cartLine.setCustomMilktea(milkteaRequest);
			cartLine.setUser(user);
			cartLine.setQuantity(quantity);
			cartLine.setTotalCostOnLine(calculateTotalCost(milkteaRequest, quantity));
			cartLine.setTotalPriceOnLine(calculateTotalPrice(milkteaRequest, quantity));
		} else {
			quantity = cartLine.getQuantity() + quantity;
			
			cartLine.setQuantity(quantity);
			cartLine.setTotalCostOnLine(calculateTotalCost(milkteaRequest, quantity));
			cartLine.setTotalPriceOnLine(calculateTotalPrice(milkteaRequest, quantity));
		}
		
		if(cartLine.getQuantity() > milkteaRequest.getMilkTea().getQuantity()) {
			return null;
		}
			
		return cartLineRepository.save(cartLine);
	}
	
	public boolean increaseMilktea(HttpServletRequest request, CustomMilkteaDto customMilkteaDto) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElseThrow(
					() -> new NotFoundValueException("Couldn't find User", "/milkteashop/kingtea/cart/increasemilktea"));
		
		CustomMilktea customMilktea = customMilkteaService.getById(customMilkteaDto.getCustomMilkteaId());
		CartLine cartLine = cartLineRepository.findByUserAndCustomMilktea(user, customMilktea);
		if (cartLine != null) {
			cartLine.setQuantity(cartLine.getQuantity() + customMilkteaDto.getQuantity());
			cartLine.setTotalCostOnLine(calculateTotalCost(customMilktea, cartLine.getQuantity()));
			cartLine.setTotalPriceOnLine(calculateTotalPrice(customMilktea, cartLine.getQuantity()));
			if(cartLine.getQuantity() > customMilktea.getMilkTea().getQuantity()) {
				return false;
			}
			cartLineRepository.save(cartLine);
		}
		return true;
	}
	
	public void decreaseMilktea(HttpServletRequest request, CustomMilkteaDto customMilkteaDto) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElseThrow(
					() -> new NotFoundValueException("Couldn't find User", "/milkteashop/kingtea/cart/increasemilktea"));
		
		CustomMilktea customMilktea = customMilkteaService.getById(customMilkteaDto.getCustomMilkteaId());
		CartLine cartLine = cartLineRepository.findByUserAndCustomMilktea(user, customMilktea);
		if (cartLine != null) {
			if (cartLine.getQuantity() == 1) {
				cartLineRepository.delete(cartLine);
				return;
			}
			cartLine.setQuantity(cartLine.getQuantity() - customMilkteaDto.getQuantity());
			cartLine.setTotalCostOnLine(calculateTotalCost(customMilktea, cartLine.getQuantity()));
			cartLine.setTotalPriceOnLine(calculateTotalPrice(customMilktea, cartLine.getQuantity()));
			cartLineRepository.save(cartLine);
		}
	}
	
	public void deleteCartLine(HttpServletRequest request, String customMilkteaId) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElseThrow(
					() -> new NotFoundValueException("Couldn't find User", "/milkteashop/kingtea/cart/increasemilktea"));
		
		CustomMilktea customMilktea = customMilkteaService.getById(customMilkteaId);
		CartLine cartLine = cartLineRepository.findByUserAndCustomMilktea(user, customMilktea);
		cartLineRepository.delete(cartLine);
	}
	
	private int calculateTotalPrice(CustomMilktea customMilktea, int quantity) {
		int price = customMilktea.getPrice();
		/* Comment this code because price of toppings were added to price of custommilktea
		List<Topping> toppings = customMilktea.getListTopping();
		if (toppings == null) {
			return price;
		}
		for (Topping topping : toppings) {
			price += topping.getPrice();
		} */
		
		return price * quantity;
	}
	
	private int calculateTotalCost(CustomMilktea customMilktea, int quantity) {
		int cost = customMilktea.getCost();
		List<Topping> toppings = customMilktea.getListTopping();
		
		if (toppings == null) {
			return cost;
		}
		for (Topping topping : toppings) {
			cost += topping.getCost();
		}
		
		return cost * quantity;
	}
	
	private CustomMilktea convertCustomMilkTeaDtoToEntity(CustomMilkteaDto dto) {
		CustomMilktea entity = new CustomMilktea();
		entity.setSize(dto.getSize());
		entity.setSugarAmount(dto.getSugarAmount());
		entity.setIceAmount(dto.getIceAmount());
		entity.setMilkTea(milkteaService.getMilkteaById(dto.getMilkTeaId()));
		entity.setEnabled(dto.isEnabled());
		
		List<String> nameTopping = dto.getListTopping();
		List<Topping> toppings = new ArrayList<>();
		for (String nameTp : nameTopping) {
			toppings.add(toppingRepository.findByName(nameTp));
		}
		entity.setListTopping(toppings);
		
		return entity;
	}
	
	private CustomMilkteaDto convertCustomMilkTeaEntityToDto(CustomMilktea entity) {
		CustomMilkteaDto dto = new CustomMilkteaDto();
		dto.setCustomMilkteaId(entity.getCustomMilkteaId());
		dto.setSize(entity.getSize());
		dto.setSugarAmount(entity.getSugarAmount());
		dto.setIceAmount(entity.getIceAmount());
		dto.setMilkTeaId(entity.getMilkTea().getMilkTeaId());
		dto.setEnabled(true);
		dto.setNameMilktea(entity.getMilkTea().getName());
		dto.setImgUrl(entity.getMilkTea().getImgUrl());		
		List<String> toppingName = new ArrayList<>();
		List<Topping> toppings = entity.getListTopping();
		for (Topping topping : toppings) {
			toppingName.add(topping.getName());
		}
		
		dto.setListTopping(toppingName);
		return dto;
	}
}
