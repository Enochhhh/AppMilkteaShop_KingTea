package com.milkteashop.kingtea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.dto.CustomMilkteaDto;
import com.milkteashop.kingtea.model.CartLine;
import com.milkteashop.kingtea.model.CustomMilktea;
import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.model.OrderLine;
import com.milkteashop.kingtea.model.Topping;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.CartLineRepository;
import com.milkteashop.kingtea.repository.MilkTeaRepository;
import com.milkteashop.kingtea.repository.OrderLineRepository;
import com.milkteashop.kingtea.repository.OrderRepository;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
@Transactional
public class OrderService {
	@Autowired OrderRepository orderRepository;
	@Autowired UserRepository userRepository;
	@Autowired CartLineRepository cartLineRepository;
	@Autowired OrderLineRepository orderLineRepository;
	@Autowired MilkTeaRepository milkTeaRepository;
	@Autowired MailSenderService mailSenderService;
	@Autowired JwtUtils jwtUtils;
	
	public Order createOrder(Order order, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElse(null);
		if (user == null) {
			return null;
		}
		
		order.setUser(user);
		order.setEnabled(true);
		order.setDateCreated(new Date());
		if (order.getPaymentMethod().equals("COD")) {
			order.setState("Waiting Accept");
		} else {
			order.setState("Unpaid Order");
		}
		order = orderRepository.save(order);
		
		return addDetailInformation(order);
	}
	
	private Order addDetailInformation(Order order) {
		List<CartLine> cartLines = cartLineRepository.findByUser(order.getUser());
		if (cartLines == null) {
			return null;
		}
		
		int totalCost = 0;
		// List<Milktea> milkteas = new ArrayList<>();
		for(CartLine cartLine : cartLines) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrder(order);
			orderLine.setCustomMilktea(cartLine.getCustomMilktea());
			orderLine.setQuantity(cartLine.getQuantity());
			orderLine.setTotalCostOnLine(cartLine.getTotalCostOnLine());
			orderLine.setTotalPriceOnLine(cartLine.getTotalPriceOnLine());
			
			/*
			// Abstract quantity of Milktea is available in DB
			Milktea milktea = orderLine.getCustomMilktea().getMilkTea();
			if (milktea.getQuantity() < orderLine.getQuantity()) {
				cartLineRepository.deleteByUser(order.getUser()); 
				orderLineRepository.deleteByOrder(order);
				orderRepository.delete(order);
				for (Milktea mt : milkteas) {
					milkTeaRepository.save(mt);
				}
				return null;
			}
			// Add to 1 code check if milktea is existed in list, we don't add milktea to it
			***** This place will be added code ******

			milkteas.add(milktea); 
			milktea.setQuantity(milktea.getQuantity() - orderLine.getQuantity());
			if (milktea.getQuantity() == 0) {
				milktea.setEnabled(false);
			}
			milkTeaRepository.save(milktea);*/
			
			orderLineRepository.save(orderLine);
			
			// Calculate total cost
			totalCost += orderLine.getTotalCostOnLine();
			
		}
		cartLineRepository.deleteByUser(order.getUser()); 
		order.setTotalCost(totalCost);
		return orderRepository.save(order);
	}
	
	public Order getOrder(String id) {
		Order order = orderRepository.findById(id).orElse(null);
		
		return order;
	}
	
	public void sendEmailToCustomer(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElse(null);
		
		String subject = "Notice about your order at KingTea milk tea shop";
		String body = "<b>Thank you for coming to KingTea</b> <br>"			
				+ "<p>Hi " + user.getFullName() == null ? user.getUserName() : user.getFullName() 
						+ ", thank you for ordering at KingTeaShop. Your order will be delivered to you as soon as possible.</p><br>"
						+ "<p>We look forward to seeing you in the shop again. Have a good day.</p>";
		body.concat("<p>We look forward to seeing you in the shop again. Have a good day.</p>");
		mailSenderService.sendEmail(user.getEmail(), subject, body);
	}
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public List<Order> getOrdersByToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		User user = userRepository.findById(jwtUtils.extractUserId(token)).orElse(null);
		if (user == null) {
			return null;
		}
		
		return orderRepository.findByUserAndEnabledTrueOrderByDateCreatedDesc(user);
	}
	
	public String getImageOrder(String orderId) {
		Order order = getOrder(orderId);
		
		if (order == null) {
			return null;
		}
		
		List<OrderLine> orderLines = order.getListOrderLine();
		return orderLines.get(0).getCustomMilktea().getMilkTea().getImgUrl();
	}
	
	public Order cancelOrder(String orderId) {
		Order order = getOrder(orderId);
		
		if (order == null) {
			return null;
		}
		
		order.setState("Cancelled");
		return orderRepository.save(order);
	}
	
	public List<CustomMilkteaDto> getMilkteaInOrder(String orderId) {
		Order order = getOrder(orderId);
		
		if (order == null) {
			return null;
		}
		
		List<OrderLine> orderLines = order.getListOrderLine();
		
		List<CustomMilkteaDto> listMilktea = new ArrayList<>();
		for (OrderLine orderLine : orderLines) {
			CustomMilkteaDto customMilkteaDto = convertCustomMilkTeaEntityToDto(orderLine.getCustomMilktea());
			customMilkteaDto.setTotalPriceOfItem(orderLine.getTotalPriceOnLine() / orderLine.getQuantity());
			customMilkteaDto.setQuantity(orderLine.getQuantity());
			listMilktea.add(customMilkteaDto);
		}
		return listMilktea;
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
