package com.milkteashop.kingtea.controller;


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

import com.milkteashop.kingtea.dto.OtpDto;
import com.milkteashop.kingtea.dto.ResponseStringDto;
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
			throw new NotFoundValueException("Couldn't find user with your request", pathApi + "/getbytoken");
		}
	
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/otp/createotp/{email}")
	public ResponseEntity<Object> createOtpAndCheckEmail(@PathVariable String email) {

		String otpCode = userService.createOtpAndCheckEmail(email);
		System.out.println("Here is OTP Code: " + otpCode);
		
		
		if (otpCode == null) {
			throw new NotFoundValueException("Email Not Exist", pathApi + "/otp/createotp");
		}
		ResponseStringDto response = new ResponseStringDto();
		response.setMessage(otpCode);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/otp/checkotp")
	public ResponseEntity<Object> checkOtpAndCreateNewPass(@RequestBody OtpDto otpRequest) {
		boolean isMath = userService.checkOtpAndCreateNewPass(otpRequest);
		
		if (!isMath) {
			throw new NotFoundValueException("OTP is not match", pathApi + "/otp/checkotp");
		}
		ResponseStringDto response = new ResponseStringDto();
		response.setMessage("Success");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/otp/sendotpmail")
	public ResponseEntity<Object> sendOtpCodeToCustomer(@RequestParam String email) {
		boolean isSent = userService.sendEmailOtpToCustomer(email);
		
		if (!isSent) {
			throw new NotFoundValueException("Couldn't find email to send OTP", pathApi + "/otp/sendotpmail");
		}
		ResponseStringDto responseStringDto = new ResponseStringDto();
		responseStringDto.setMessage("OTP is sent to email " + email);
		return ResponseEntity.ok(responseStringDto);
	}
	
	@PutMapping("/update/profile")
	public ResponseEntity<Object> updateProfile(@RequestBody User user, HttpServletRequest request) {
		user = userService.updateProfile(user, request);
		System.out.println("Success");
		
		if (user == null) {
			throw new NotFoundValueException("Couldn't update profile", pathApi + "/update/profile");
		}
		
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/update/image")
	public ResponseEntity<Object> updateImage(@RequestBody User user, HttpServletRequest request) {
		user = userService.updateImage(user, request);
		
		if (user == null) {
			throw new NotFoundValueException("Couldn't update profile", pathApi + "/update/profile");
		}
		
		return ResponseEntity.ok(user);
	}
}
