package com.milkteashop.kingtea.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "cart_line")
public class CartLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartline_id")
	private int cartLineId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "RelationUserAndCartLine")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "custom_milktea_id")
	@JsonBackReference(value = "RelationCustomMilkteaAndCartLine")
	private CustomMilktea customMilktea;
	
	@Column(name = "total_price_on_line")
	private int totalPriceOnLine;
	
	@Column(name = "total_cost_on_line")
	private int totalCostOnLine;
	
	@Column(name = "quantity")
	private int quantity;
}
