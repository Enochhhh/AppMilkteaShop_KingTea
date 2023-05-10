package com.milkteashop.kingtea.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GenericGenerator(name = "user_id", strategy = "com.milkteashop.kingtea.identifygenerator.UserIdGenerator")
	@GeneratedValue(generator = "user_id")
	@Column(name = "id", length = 10)
	private String id;
	
	@Column(name = "user_name", unique = true, nullable = false, length = 50)
	private String userName;
	
	@Column(name = "password", nullable = false, length = 150)
	private String password;
	
	@Column(name = "role", nullable = false, length = 10)
	private String role;
	
	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "full_name", length = 50)
	private String fullName;
	
	@Column(name = "address", length = 50)
	private String address;
	
	@Column(name = "email", nullable = false, length = 50)
	private String email;
	
	@Column(name = "phone", length = 11)
	private String phone;
	
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "otp_code", length = 10)
	private String otpCode;
	
	@Column(name = "otp_requested_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date otpRequestedTime;
	
	@Column(name = "image_url", length = 300)
	private String imageUrl;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
}
