package com.milkteashop.kingtea.controller;


import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.dto.ResponseStringDto;
import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.service.OrderService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/order")
public class OrderController {
	@Autowired OrderService orderService;
	
	@PostMapping("/create")
	ResponseEntity<Object> createOrder(@RequestBody Order order, HttpServletRequest request) {
		Order orderResponse = orderService.createOrder(order, request);
		
		if (orderResponse == null) {
			throw new NotFoundValueException("Couldn't create order because quantity is not enough", "milktkeashop/kingtea/order/create");
		}
		return ResponseEntity.ok(orderResponse);
	}
	
	@GetMapping("/get")
	ResponseEntity<Order> getOrder(@RequestParam String id) {
		Order order = orderService.getOrder(id);
		
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/sendemail")
	ResponseEntity<ResponseStringDto> sendEmailToCustomer(HttpServletRequest request) {
		System.out.println("Here");
		orderService.sendEmailToCustomer(request);
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Send email successfully");
		return ResponseEntity.ok(message);
	}
}
