package com.milkteashop.kingtea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUserByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		
		if (user == null) {
			//throw exception
		}
		return user;
	}
}
