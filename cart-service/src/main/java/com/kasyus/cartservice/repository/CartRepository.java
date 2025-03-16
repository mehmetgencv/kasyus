package com.kasyus.cartservice.repository;

import com.kasyus.product_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Category, Long> {
}