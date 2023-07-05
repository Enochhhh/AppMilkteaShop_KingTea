package com.milkteashop.kingtea.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Category;
import com.milkteashop.kingtea.model.Milktea;

@Repository
public interface MilkTeaRepository extends JpaRepository<Milktea, String> {
	List<Milktea> findByCategory(Category category);
	List<Milktea> findByEnabledTrueAndNameContaining(String keyword);
}
