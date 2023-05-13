package com.milkteashop.kingtea.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GenericGenerator(name = "category_id", strategy = "com.milkteashop.kingtea.identifygenerator.CategoryIdGenerator")
	@GeneratedValue(generator = "category_id")
	@Column(name = "category_id", length = 10)
	private String categoryId;
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "description", length = 1000)
	private String description;
	
	@Column(name = "img_url", length = 255)
	private String imgUrl;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "RelationMilkteaAndCategory")
	private List<Milktea> listMilktea;
	
}
