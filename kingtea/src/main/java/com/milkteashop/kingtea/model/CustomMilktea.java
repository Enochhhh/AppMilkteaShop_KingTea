package com.milkteashop.kingtea.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "custom_milktea")
public class CustomMilktea {
	@Id
	@GenericGenerator(name = "custom_milktea_id", strategy = "com.milkteashop.kingtea.identifygenerator.CustomMilkteaIdGenerator")
	@GeneratedValue(generator = "custom_milktea_id")
	@Column(name = "custom_milktea_id", length = 10)
	private String customMilkteaId;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "cost")
	private int cost;
	
	@Column(name = "size", length = 2)
	private String size;
	
	@Column(name = "sugar_amount", length = 5)
	private String sugarAmount;
	
	@Column(name = "ice_amount", length = 5)
	private String iceAmount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "milktea_id")
	@JsonBackReference(value = "RelationshipMilkteaAndCustomMilktea")
	private Milktea milkTea;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "add_on_milktea",
			joinColumns = @JoinColumn(name = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_topping"))
	List<Topping> listTopping;
	
	@OneToMany(mappedBy = "customMilktea", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "RelationCustomMilkteaAndOrderLine")
	List<OrderLine> listOrderLine;
	
	@OneToMany(mappedBy = "customMilktea", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "RelationCustomMilkteaAndCartLine")
	List<CartLine> listCartLine;
}
