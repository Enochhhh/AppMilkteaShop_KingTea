package com.milkteashop.kingtea.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.milkteashop.kingtea.model.Topping;

public interface ToppingRepository extends JpaRepository<Topping, String> {
	Topping findByName(String name);
}
