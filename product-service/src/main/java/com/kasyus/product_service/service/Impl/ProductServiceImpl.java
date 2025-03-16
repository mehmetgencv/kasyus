package com.kasyus.product_service.service.Impl;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.exception.ProductNotFoundException;
import com.kasyus.product_service.mapper.ProductMapper;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.model.ProductImage;
import com.kasyus.product_service.repository.ProductRepository;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import com.kasyus.product_service.service.MinioService;
import com.kasyus.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MinioService minioService;
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, MinioService minioService) {

        this.productRepository = productRepository;
        this.minioService = minioService;
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest request) {
        Product product = ProductMapper.INSTANCE.toProduct(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toProductDto(savedProduct);
    }

    @Override
    public ProductDto uploadProductImages(Long productId, List<MultipartFile> images, int coverImageIndex) {
        Product product = findProductById(productId);

        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                if (!image.isEmpty()) {
                    try {
                        String imageUrl = minioService.uploadFile(image);
                        boolean isCover = (i == coverImageIndex);
                        ProductImage productImage = new ProductImage(imageUrl, isCover, product);
                        product.addImage(productImage);
                    } catch (Exception e) {
                        log.error("Error uploading image: {}", e.getMessage());
                        throw new RuntimeException("Image upload failed", e);
                    }
                }
            }
            productRepository.save(product);
        }

        return ProductMapper.INSTANCE.toProductDto(product);
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

    @Override
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        deleteImagesFromMinio(product.getImages());
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateProductImage(Long productId, Long imageId, MultipartFile newImage, Boolean isCoverImage) {
        Product product = findProductById(productId);
        ProductImage imageToUpdate = findImageById(product, imageId);

        if (newImage != null && !newImage.isEmpty()) {
            updateImageFile(imageToUpdate, newImage);
        }

        if (isCoverImage != null) {
            updateCoverImageStatus(product, imageToUpdate, isCoverImage);
        }

        productRepository.save(product);
        return ProductMapper.INSTANCE.toProductDto(product);
    }

    @Override
    public ProductDto deleteProductImage(Long productId, Long imageId) {
        Product product = findProductById(productId);
        ProductImage imageToDelete = findImageById(product, imageId);

        boolean wasCoverImage = imageToDelete.isCoverImage();
        deleteImageFromMinio(imageToDelete);
        product.getImages().remove(imageToDelete);

        if (wasCoverImage && !product.getImages().isEmpty()) {
            setFirstImageAsCover(product);
        }

        productRepository.save(product);
        return ProductMapper.INSTANCE.toProductDto(product);
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapper.INSTANCE.toProductDtoList(products);
    }


    @Override
    public ProductDto setCoverImage(Long productId, Long imageId) {
        Product product = findProductById(productId);
        ProductImage imageToSetAsCover = findImageById(product, imageId);

        updateCoverImageStatus(product, imageToSetAsCover, true);

        productRepository.save(product);
        return ProductMapper.INSTANCE.toProductDto(product);
    }


    // Private methods
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private ProductImage findImageById(Product product, Long imageId) {
        return product.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found for product"));
    }

    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }


    private void deleteImagesFromMinio(List<ProductImage> images) {
        for (ProductImage image : images) {
            deleteImageFromMinio(image);
        }
    }

    private void deleteImageFromMinio(ProductImage image) {
        try {
            String fileName = extractFileNameFromUrl(image.getImageUrl());
            minioService.deleteFile(fileName);
        } catch (Exception e) {
            log.error("Error deleting image from MinIO: {}", e.getMessage());
        }
    }

    private void updateImageFile(ProductImage image, MultipartFile newImage) {
        try {
            String oldFileName = extractFileNameFromUrl(image.getImageUrl());
            minioService.deleteFile(oldFileName);
            String newImageUrl = minioService.uploadFile(newImage);
            image.setImageUrl(newImageUrl);
        } catch (Exception e) {
            log.error("Error updating image in MinIO: {}", e.getMessage());
            throw new RuntimeException("Image update failed", e);
        }
    }

    private void updateCoverImageStatus(Product product, ProductImage targetImage, boolean isCoverImage) {
        if (isCoverImage) {
            product.getImages().forEach(img -> img.setCoverImage(false));
        }
        targetImage.setCoverImage(isCoverImage);
    }

    private void setFirstImageAsCover(Product product) {
        ProductImage newCoverImage = product.getImages().getFirst();
        newCoverImage.setCoverImage(true);
        log.info("New cover image set automatically: {}", newCoverImage.getImageUrl());
    }



} 