package com.klu.cachecart.repository;

import com.klu.cachecart.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}