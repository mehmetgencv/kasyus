package com.kasyus.product_service.service;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto createProduct(ProductCreateRequest request);
    ProductDto uploadProductImages(Long productId, List<MultipartFile> images, int coverImageIndex);
    ProductDto setCoverImage(Long productId, Long imageId);
    ProductDto getProductById(Long id);
    ProductDto getProductBySku(String sku);
    List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<ProductDto> searchProductsByName(String name);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductUpdateRequest request);
    void deleteProduct(Long id);

    ProductDto updateProductImage(Long productId, Long imageId, MultipartFile newImage, Boolean isCoverImage);

    ProductDto deleteProductImage(Long productId, Long imageId);
}