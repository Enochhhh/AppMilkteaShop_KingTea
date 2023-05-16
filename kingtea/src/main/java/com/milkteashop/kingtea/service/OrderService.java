package com.milkteashop.kingtea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.model.CartLine;
import com.milkteashop.kingtea.model.Milktea;
import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.model.OrderLine;
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
		
		if(order.getReceiverName().equals("")) {
			order.setReceiverName(user.getFullName());
		}
		if(order.getPhone().equals("")) {
			order.setPhone(user.getPhone());
		}
		if(order.getAddress().equals("")) {
			order.setAddress(user.getAddress());
		}
		
		order.setUser(user);
		order.setEnabled(true);
		order.setDateCreated(new Date());
		order.setState("Chờ xác nhận");
		order = orderRepository.save(order);
		
		return addDetailInformation(order);
	}
	
	private Order addDetailInformation(Order order) {
		List<CartLine> cartLines = cartLineRepository.findByUser(order.getUser());
		if (cartLines == null) {
			return null;
		}
		
		int totalCost = 0;
		List<Milktea> milkteas = new ArrayList<>();
		for(CartLine cartLine : cartLines) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrder(order);
			orderLine.setCustomMilktea(cartLine.getCustomMilktea());
			orderLine.setQuantity(cartLine.getQuantity());
			orderLine.setTotalCostOnLine(cartLine.getTotalCostOnLine());
			orderLine.setTotalPriceOnLine(cartLine.getTotalPriceOnLine());
			
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
			milkteas.add(milktea);
			milktea.setQuantity(milktea.getQuantity() - orderLine.getQuantity());
			if (milktea.getQuantity() == 0) {
				milktea.setEnabled(false);
			}
			milkTeaRepository.save(milktea);
			
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
}
