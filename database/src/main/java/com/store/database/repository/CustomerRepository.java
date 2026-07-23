package com.store.database.repository;

import com.store.database.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Customer findByEmail(String email);

    List<Customer> findByName(String name);
}