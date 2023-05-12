package com.milkteashop.kingtea.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.service.UserService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/user")
public class UserController {
	private static final String pathApi = "/milkteashop/kingtea/user";
	
	@Autowired private UserService userService;
	
	@GetMapping("/getbytoken")
	public ResponseEntity<User> getUserByToken(HttpServletRequest request) {
		User user = userService.getUserByToken(request);
		
		if (user == null) {
			throw new NotFoundValueException("Couldn't find user with your request", pathApi + "getbytoken");
		}
	
		return ResponseEntity.ok(user);
	}
}
