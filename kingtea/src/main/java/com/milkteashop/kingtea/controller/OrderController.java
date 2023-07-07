package com.milkteashop.kingtea.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.dto.CustomMilkteaDto;
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
		orderService.sendEmailToCustomer(request);
		ResponseStringDto message = new ResponseStringDto();
		message.setMessage("Send email successfully");
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/getbytoken")
	ResponseEntity<Object> getOrderByToken(HttpServletRequest request) {
		List<Order> orders = orderService.getOrdersByToken(request);
		
		if (orders == null || orders.isEmpty()) {
			throw new NotFoundValueException("Couldn't find any order of Customer", "/milkteashop/kingtea/order");
		}
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/getbystate")
	ResponseEntity<Object> getOrderByState(@RequestParam String state) {
		List<Order> orders = orderService.getOrderByState(state);
		
		if (orders == null || orders.isEmpty()) {
			throw new NotFoundValueException("Couldn't find any order with state \"" + state + "\"", "/milkteashop/kingtea/order/getbystate");
		}
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/getimageorder")
	ResponseEntity<Object> getImageOrder(@RequestParam String orderId) {
		String url = orderService.getImageOrder(orderId);
		
		if (url == null) {
			throw new NotFoundValueException("Couldn't find image of order", "/milkteashop/kingtea/order");
		}
		ResponseStringDto res = new ResponseStringDto();
		res.setMessage(url);
				
		return ResponseEntity.ok(res);
	}
	
	@PutMapping("/cancel/{orderId}")
	ResponseEntity<Object> cancelOrder(@PathVariable String orderId) {
		Order order = orderService.cancelOrder(orderId);
		
		if (order == null) {
			throw new NotFoundValueException("Couldn't find Order to cancel", "/milkteashop/kingtea/order");
		}
		ResponseStringDto res = new ResponseStringDto();
		res.setMessage("Cancel Order Successfully");
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/getmilktea")
	ResponseEntity<Object> getMilkteaInOrder(@RequestParam String orderId) {
		List<CustomMilkteaDto> listMilktea = orderService.getMilkteaInOrder(orderId);
		
		if (listMilktea == null || listMilktea.isEmpty()) {
			throw new NotFoundValueException("Get Order detail unsuccessfully", "/milkteashop/kingtea/order");
		}
		return ResponseEntity.ok(listMilktea);
	}
	
	@PutMapping("/accept/{orderId}")
	ResponseEntity<Object> acceptOrder(@PathVariable String orderId) {
		Order order = orderService.acceptOrder(orderId);
		
		if (order == null) {
			throw new NotFoundValueException("Couldn't find Order to accept", "/milkteashop/kingtea/order/accept");
		}
		ResponseStringDto res = new ResponseStringDto();
		res.setMessage("Accept Order Successfully");
		return ResponseEntity.ok(res);
	}
}
