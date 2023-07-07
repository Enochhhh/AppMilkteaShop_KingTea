package com.milkteashop.kingtea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Order;
import com.milkteashop.kingtea.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findByStateAndEnabledTrue(String state);
	List<Order> findByUserAndEnabledTrueOrderByDateCreatedDesc(User user);
	List<Order> findByStateAndEnabledTrueOrderByDateCreated(String state);
}
