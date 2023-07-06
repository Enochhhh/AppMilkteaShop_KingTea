package com.milkteashop.kingtea.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.model.Contact;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired ContactRepository contactRepository;
	@Autowired JwtUtils jwtUtils;
	@Autowired UserService userService;
	
	public Contact createContact(Contact contact, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		String userName = jwtUtils.extractUserName(token);
		
		User user = userService.findUserByUserName(userName);
		contact.setUser(user);
		
		return contactRepository.save(contact);
	}
}
