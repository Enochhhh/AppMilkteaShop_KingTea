package com.milkteashop.kingtea.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Milktea;

@Repository
public interface MilkTeaRepository extends JpaRepository<Milktea, String> {

}
