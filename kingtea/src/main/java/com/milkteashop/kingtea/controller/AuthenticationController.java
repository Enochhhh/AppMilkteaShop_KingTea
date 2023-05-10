package com.milkteashop.kingtea.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.config.JwtUserPrincipal;
import com.milkteashop.kingtea.dto.AuthenticationRequestDto;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final com.milkteashop.kingtea.config.JwtUtils jwtUtils;
	private final PasswordEncoder passw;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(
			@RequestBody AuthenticationRequestDto request) {
		// Xử exception BadCredential
		System.out.println(passw.encode("AdminKingTea"));
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		final User user = userService.findUserByUserName(request.getUsername());
		if (user != null) {
			JwtUserPrincipal jwtUserPrincipal = new JwtUserPrincipal(user);
			return ResponseEntity.ok(jwtUtils.generateToken(jwtUserPrincipal));
		}
		return ResponseEntity.status(400).body("Some errors occured");
	}
}
