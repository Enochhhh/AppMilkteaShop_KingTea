package com.milkteashop.kingtea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkteashop.kingtea.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

}
