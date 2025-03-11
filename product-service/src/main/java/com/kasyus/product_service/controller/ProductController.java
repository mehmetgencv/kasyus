package com.kasyus.product_service.controller;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.general.RestResponse;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import com.kasyus.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public static final String CORRELATION_ID_KEY = "kasyus-correlation-id";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<RestResponse<ProductDto>> createProduct(@RequestBody ProductCreateRequest request) {
        logger.debug("createProduct method start");
        ProductDto createdProduct = productService.createProduct(request);
        logger.debug("createProduct method end");
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.of(createdProduct, "Product created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ProductDto>> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product retrieved successfully"));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<RestResponse<ProductDto>> getProductBySku(@PathVariable String sku) {
        ProductDto productDto = productService.getProductBySku(sku);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<ProductDto>>> getAllProducts(@RequestHeader(CORRELATION_ID_KEY) String correlationId) {
        logger.debug(CORRELATION_ID_KEY + " found: {}", correlationId);
        logger.debug("getAllProducts method start");
        List<ProductDto> productDtos = productService.getAllProducts();
        logger.debug("getAllProducts method end");
        return ResponseEntity.ok(RestResponse.of(productDtos, "Products retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        ProductDto productDto = productService.updateProduct(id, request);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(RestResponse.of(null, "Product deleted successfully"));
    }

    // Image Operations

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload images for a product", description = "Uploads one or more images for a specific product")
    public ResponseEntity<ProductDto> uploadProductImages(
            @PathVariable Long productId,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "coverImageIndex", defaultValue = "0") int coverImageIndex) {
        ProductDto productDto = productService.uploadProductImages(productId, images, coverImageIndex);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping(value = "/{productId}/images/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a product image", description = "Updates the image or its cover status")
    public ResponseEntity<ProductDto> updateProductImage(
            @PathVariable Long productId,
            @PathVariable Long imageId,
            @RequestPart(value = "image", required = false) MultipartFile newImage,
            @RequestParam(value = "isCoverImage", required = false) Boolean isCoverImage) {
        ProductDto productDto = productService.updateProductImage(productId, imageId, newImage, isCoverImage);
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/{productId}/images/{imageId}/cover")
    @Operation(summary = "Set an image as cover", description = "Sets a specific image as the cover image for a product")
    public ResponseEntity<ProductDto> setCoverImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {
        ProductDto productDto = productService.setCoverImage(productId, imageId);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    @Operation(summary = "Delete a product image", description = "Deletes a specific image from a product")
    public ResponseEntity<ProductDto> deleteProductImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {
        ProductDto productDto = productService.deleteProductImage(productId, imageId);
        return ResponseEntity.ok(productDto);
    }

} 