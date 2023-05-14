package com.milkteashop.kingtea.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private JwtUtils jwtUtils;
	
	
	public User findUserByUserName(String userName) {
		User user = userRepository.findByUserNameAndEnabledTrue(userName);
		return user;
	}
	
	public boolean isExistedByUserName(String userName) {
		return userRepository.existsByUserName(userName);
	}
	
	public boolean isExistedByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	
	public User createUser(User user) {
		user.setRole("CUSTOMER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(new Date());
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	public User getUserByToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer ")) {
			return null;
		}
		
		String userName = jwtUtils.extractUserName(header.substring(7));
		return findUserByUserName(userName);
	}
}
