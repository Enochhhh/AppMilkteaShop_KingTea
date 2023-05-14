package com.milkteashop.kingtea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	Category findByName(String name);
	List<Category> findByEnabledTrue();
}
