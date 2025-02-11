package com.kasyus.product_service.service;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto createProduct(ProductCreateRequest request);
    ProductDto getProductById(Long id);
    ProductDto getProductBySku(String sku);
    List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<ProductDto> searchProductsByName(String name);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductUpdateRequest request);
    void deleteProduct(Long id);
}