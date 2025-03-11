package com.kasyus.product_service.dto;


import java.util.List;

public record CategoryDto(
        Long id,
        String name,
        List<ProductDto> products
) {
}
