package com.milkteashop.kingtea.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkteashop.kingtea.exception.NotFoundValueException;
import com.milkteashop.kingtea.model.Contact;
import com.milkteashop.kingtea.service.ContactService;

@RestController
@RequestMapping(path = "/milkteashop/kingtea/contact")
public class ContactController {
	private static final String pathApi = "/milkteashop/kingtea/contact";
	
	@Autowired ContactService contactService;
	
	@PostMapping("/create") 
	ResponseEntity<Contact> createContact(@RequestBody Contact contact, HttpServletRequest request) {
		contact = contactService.createContact(contact, request);
		
		if (contact == null) {
			throw new NotFoundValueException("Couldn't create contact", pathApi + "/create");
		}
		return ResponseEntity.ok(contact);
	}
}
