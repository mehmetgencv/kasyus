package com.kasyus.product_service.service.Impl;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.exception.ProductNotFoundException;
import com.kasyus.product_service.mapper.ProductMapper;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.repository.ProductRepository;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import com.kasyus.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest request) {
        Product product = ProductMapper.INSTANCE.toProduct(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toProductDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = findProductById(id);

        return ProductMapper.INSTANCE.toProductDto(product);

    }

    @Override
    public ProductDto getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return ProductMapper.INSTANCE.toProductDto(product);
    }


    @Override
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return ProductMapper.INSTANCE.toProductDtoList(products);
    }


    @Override
    public List<ProductDto> searchProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return ProductMapper.INSTANCE.toProductDtoList(products);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.toProductDtoList(products);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductUpdateRequest request) {
        Product product = findProductById(id);

        Product updateProduct = ProductMapper.INSTANCE.updateProductFields(product, request);
        Product savedProduct = productRepository.save(updateProduct);
        return ProductMapper.INSTANCE.toProductDto(savedProduct);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

} 