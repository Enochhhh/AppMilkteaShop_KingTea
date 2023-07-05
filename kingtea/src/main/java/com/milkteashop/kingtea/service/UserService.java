package com.milkteashop.kingtea.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.milkteashop.kingtea.config.JwtUtils;
import com.milkteashop.kingtea.dto.OtpDto;
import com.milkteashop.kingtea.model.User;
import com.milkteashop.kingtea.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private JwtUtils jwtUtils;
	
	@Autowired private MailSenderService mailSenderService;
	
	
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
	
	public String createOtpAndCheckEmail(String email) {
		User user = userRepository.findByEmailAndEnabledTrue(email);
		
		if (user == null) {
			return null;
		}
		
		String otp = new DecimalFormat("0000").format(new Random().nextInt(9999));
		Date dateExpired = new Date(new Date().getTime() + 300*1000);
		user.setOtpCode(otp);
		user.setOtpRequestedTime(dateExpired);
		//sendEmailOtpToCustomer(otp, email);
		userRepository.save(user);
		
		return otp;
	}
	
	public boolean sendEmailOtpToCustomer(String email) {
		User user = userRepository.findByEmailAndEnabledTrue(email);
		if (user == null) {
			return false;
		}
		
		String subject = "Kingtea milk tea Shop send OTP Code";
		String body = "<b>OTP Code is expired after 5 minutes</b> <br>"			
				+ "<p>" + user.getOtpCode() + "</p><br>";
		mailSenderService.sendEmail(email, subject, body);
		return true;
	}
	
	public boolean checkOtpAndCreateNewPass(OtpDto otpDto) {
		User user = userRepository.findByEmailAndEnabledTrue(otpDto.getEmail());
		
		if (user == null) {
			return false;
		}
		
		if (otpDto.getOtpCode().equals(user.getOtpCode()) && otpDto.getDateNowMilisecond() <= user.getOtpRequestedTime().getTime()) {
			user.setPassword(passwordEncoder.encode(otpDto.getNewPass()));
			user.setOtpCode(null);
			user.setOtpRequestedTime(null);
			userRepository.save(user);
			return true;
		}
		return false;
	}
}
