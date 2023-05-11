package com.milkteashop.kingtea.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User findUserByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		System.out.print(passwordEncoder.encode("AdminKingtea"));
		
		if (user == null) {
			//throw exception
		}
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
}
