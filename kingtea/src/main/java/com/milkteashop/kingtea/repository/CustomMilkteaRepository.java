package com.milkteashop.kingtea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.CustomMilktea;
import com.milkteashop.kingtea.model.Milktea;

@Repository
public interface CustomMilkteaRepository extends JpaRepository<CustomMilktea, String> {
	List<CustomMilktea> findByEnabledTrue();
	List<CustomMilktea> findByMilkTeaAndEnabledTrue(Milktea milktea);
}
