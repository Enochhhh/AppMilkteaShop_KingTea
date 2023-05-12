package com.milkteashop.kingtea.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.config.JwtUserPrincipal;
import com.milkteashop.kingtea.dto.AuthenticationRequestDto;
import com.milkteashop.kingtea.dto.ResponseTokenDto;
import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.exception.UnauthorizedException;
import com.milkteashop.kingtea.exception.ValueExistedException;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private static final String pathApi = "/milkteashop/kingtea/authentication";
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final com.milkteashop.kingtea.config.JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<Object> authenticate(
			@RequestBody AuthenticationRequestDto request) {
		
		// Check Username exists or not? Because Spring Security only check request from Client with User is existed
		User user = userService.findUserByUserName(request.getUsername());
		if (user == null) {
			throw new NotFoundValueException("Couldn't find user with request name", pathApi + "/login");
		}
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch(BadCredentialsException e) {
			throw new UnauthorizedException("Unable to authenticate user information", pathApi + "/login");
		}
		
		JwtUserPrincipal jwtUserPrincipal = new JwtUserPrincipal(user);
			
		ResponseTokenDto responseTokenDto = new ResponseTokenDto();
		responseTokenDto.setToken(jwtUtils.generateToken(jwtUserPrincipal));
			
		return ResponseEntity.ok(responseTokenDto);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(
			@RequestBody User request) {
		String username = request.getUserName();
		String email = request.getEmail();
		if (userService.isExistedByUserName(username) || userService.isExistedByEmail(email)) {
			throw new ValueExistedException("Username/Email already existed", pathApi + "/register");
		}
		
		User user = userService.createUser(request);
		return ResponseEntity.ok(user);
	}
}
