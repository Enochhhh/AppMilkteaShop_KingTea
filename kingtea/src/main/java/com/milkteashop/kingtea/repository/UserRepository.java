package com.milkteashop.kingtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUserName(String userName);
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
}
