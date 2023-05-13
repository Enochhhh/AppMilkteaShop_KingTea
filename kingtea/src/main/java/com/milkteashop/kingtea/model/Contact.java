package com.milkteashop.kingtea.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "contact")
public class Contact {
	@Id
	@GenericGenerator(name = "contact_id", strategy = "com.milkteashop.kingtea.identifygenerator.ContactIdGenerator")
	@GeneratedValue(generator = "contact_id")
	@Column(name = "contact_id", length = 10)
	private String contactId;
	
	@Column(name = "title", length = 50)
	private String title;
	
	@Column(name = "message", length = 300)
	private String message;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "phone", length = 11)
	private String phone;
	
	@Column(name = "sender_name", length = 50)
	private String senderName;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "RelationUserAndContact")
	private User user;
}
