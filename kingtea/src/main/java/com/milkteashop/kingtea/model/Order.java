package com.milkteashop.kingtea.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "order_shop")
public class Order {
	@Id
	@GenericGenerator(name = "order_id", strategy = "com.milkteashop.kingtea.identifygenerator.OrderIdGenerator")
	@GeneratedValue(generator = "order_id")
	@Column(name = "order_id", length = 10)
	private String orderId;
	
	@Column(name = "receiver_name", length = 50, nullable = false)
	private String receiverName;
	
	@Column(name = "phone", length = 11, nullable = false)
	private String phone;
	
	@Column(name = "address", length = 50, nullable = false)
	private String address;
	
	@Column(name = "total_price")
	private int totalPrice;
	
	@Column(name = "total_cost")
	private int totalCost;
	
	@Column(name = "date_created", nullable = false)
	private Date dateCreated;
	
	@Column(name = "payment_method", length = 20)
	private String paymentMethod;
	
	@Column(name = "state", length = 20, nullable = false)
	private String state;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@Column(name = "note", length = 300)
	private String note;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "RelationshipUserAndOrder")
	private User user;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "RelationOrderAndOrderLine")
	private List<OrderLine> listOrderLine;
}
