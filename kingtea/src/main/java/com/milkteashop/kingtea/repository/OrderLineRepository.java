package com.milkteashop.kingtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.model.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
	public void deleteByOrder(Order order);
}
