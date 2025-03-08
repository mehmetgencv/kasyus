package com.kasyus.userservice.repository;

import com.kasyus.userservice.model.Address;
import com.kasyus.userservice.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentMethod, UUID> {
} 