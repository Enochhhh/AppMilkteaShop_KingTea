package com.milkteashop.kingtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
