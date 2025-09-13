package com.example.demo.repository;

import com.example.demo.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales,String> {
}
