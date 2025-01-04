package com.kasyus.product_service.service;

import com.kasyus.product_service.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Optional<Product> getProductById(Long id);
    Optional<Product> getProductBySku(String sku);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> getLowStockProducts(Integer threshold);
    List<Product> searchProductsByName(String name);
    List<Product> getAllProducts();
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Product updateStock(Long id, Integer quantity);
} 