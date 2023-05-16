package com.milkteashop.kingtea.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.CartLine;
import com.milkteashop.kingtea.model.CustomMilktea;
import com.milkteashop.kingtea.model.User;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine, Integer> {
	CartLine findByUserAndCustomMilktea(User user, CustomMilktea milkteaRequest);
	List<CartLine> findByUser(User user);
	int deleteByUser(User user);
}
