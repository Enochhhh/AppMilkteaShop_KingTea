package com.milkteashop.kingtea.model;

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
@Table(name = "milk_tea")
public class Milktea {
	@Id
	@GenericGenerator(name = "milktea_id", strategy = "com.milkteashop.kingtea.identifygenerator.MilkteaIdGenerator")
	@GeneratedValue(generator = "milktea_id")
	@Column(name = "milktea_id", length = 10)
	private String milkTeaId;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "img_url", length = 255)
	private String imgUrl;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@JsonBackReference(value = "RelationMilkteaAndCategory")
	private Category category;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@OneToMany(mappedBy = "milkTea")
	@JsonManagedReference(value = "RelationshipMilkteaAndCustomMilktea")
	private List<CustomMilktea> listCustomMilktea;
}
