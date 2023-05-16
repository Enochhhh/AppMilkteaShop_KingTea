package com.milkteashop.kingtea.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail,
			String subject,
			String body) {
		String from = "phanhongson234@gmail.com";
		String to = toEmail;
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			boolean html = true;
			helper.setText(body, html);
		} catch(Exception e) {
			e.printStackTrace();
		}	
		mailSender.send(message);
	}
}
