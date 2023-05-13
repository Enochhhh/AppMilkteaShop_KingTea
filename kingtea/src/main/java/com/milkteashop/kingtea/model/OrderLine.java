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
@Table(name = "order_line")
public class OrderLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderline_id")
	private int orderLineId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	@JsonBackReference("RelationOrderAndOrderLine")
	private Order order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "custom_milktea_id")
	@JsonBackReference(value = "RelationCustomMilkteaAndOrderLine")
	private CustomMilktea customMilktea;
	
	@Column(name = "total_price_on_line")
	private int totalPriceOnLine;
	
	@Column(name = "total_cost_on_line")
	private int totalCostOnLine;
	
	@Column(name = "quantity")
	private int quantity;
	
}
