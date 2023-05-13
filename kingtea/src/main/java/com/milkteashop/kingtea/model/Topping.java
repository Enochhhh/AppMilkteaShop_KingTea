package com.milkteashop.kingtea.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "topping")
public class Topping {
	@Id
	@GenericGenerator(name = "topping_id", strategy = "com.milkteashop.kingtea.identifygenerator.ToppingIdGenerator")
	@GeneratedValue(generator = "topping_id")
	@Column(name = "topping_id", length = 10)
	private String toppingId;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "cost")
	private int cost;
	
	@ManyToMany(mappedBy = "listTopping", fetch = FetchType.LAZY)
	List<CustomMilktea> listCustomMilktea;
}
