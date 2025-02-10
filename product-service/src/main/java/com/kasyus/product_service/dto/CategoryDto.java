package com.kasyus.product_service.dto;

import com.kasyus.product_service.model.Product;

import java.util.List;

public record CategoryDto(
        Long id,
        String name,
        List<Product> products
) {
}
