package com.kasyus.product_service.controller;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.general.RestResponse;
import com.kasyus.product_service.requests.PriceUpdateRequest;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import com.kasyus.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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


    @Operation(
            summary = "Create a new product",
            description = "Creates a new product and returns the created product details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid product data")
            }
    )
    @PostMapping
    public ResponseEntity<RestResponse<ProductDto>> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {
        logger.debug("createProduct method start");
        ProductDto createdProduct = productService.createProduct(request);
        logger.debug("createProduct method end");
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.of(createdProduct, "Product created successfully"));
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Retrieves a product by its unique identifier")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<ProductDto>> getProductById(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product retrieved successfully"));
    }

    @Operation(
            summary = "Get products by category ID",
            description = "Retrieves all products that belong to a specific category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<RestResponse<List<ProductDto>>> getProductsByCategoryId(
            @Parameter(description = "Category ID", example = "2") @PathVariable Long categoryId) {
        List<ProductDto> productDtos = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(RestResponse.of(productDtos, "Products retrieved successfully"));
    }

    @Operation(
            summary = "Get products by price range",
            description = "Retrieves all products that fall within a specific price range")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid price range")
            }
    )
    @GetMapping("/sku/{sku}")
    public ResponseEntity<RestResponse<ProductDto>> getProductBySku(
            @Parameter(description = "SKU", example = "111") @PathVariable String sku) {
        ProductDto productDto = productService.getProductBySku(sku);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product retrieved successfully"));
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves all products in the system")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<RestResponse<List<ProductDto>>> getAllProducts(

            @Parameter(description = "Correlation ID", example = "abc-123-def")
            @RequestHeader(CORRELATION_ID_KEY) String correlationId) {
        logger.debug(CORRELATION_ID_KEY + " found: {}", correlationId);
        logger.debug("getAllProducts method start");
        List<ProductDto> productDtos = productService.getAllProducts();
        logger.debug("getAllProducts method end");
        return ResponseEntity.ok(RestResponse.of(productDtos, "Products retrieved successfully"));
    }

    @Operation(
            summary = "Update an existing product",
            description = "Updates product information such as name, description, price, etc."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid price range")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<ProductDto>> updateProduct(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        ProductDto productDto = productService.updateProduct(id, request);
        return ResponseEntity.ok(RestResponse.of(productDto, "Product updated successfully"));
    }

    @Operation(
            summary = "Update product price",
            description = "Updates the price of a specific product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product price updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid price data")
            }
    )
    @PatchMapping("/{id}/price")
    public ResponseEntity<RestResponse<Void>> updateProductPrice(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody PriceUpdateRequest request) {
        productService.updateProductPrice(id, request);
        return ResponseEntity.ok(RestResponse.of(null, "Product price updated successfully"));
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes a specific product from the system")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteProduct(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(RestResponse.of(null, "Product deleted successfully"));
    }

    // Image Operations

    @Operation(summary = "Upload images for a product",
            description = "Uploads one or more images for a specific product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Images uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid image data")
            }
    )
    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> uploadProductImages(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long productId,
            @Parameter(description = "Image files")
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Parameter(description = "Index of the cover image", example = "0")
            @RequestParam(value = "coverImageIndex", defaultValue = "0") int coverImageIndex) {
        ProductDto productDto = productService.uploadProductImages(productId, images, coverImageIndex);
        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Update a product image",
            description = "Updates the image or its cover status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Image updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid image data")
            }
    )
    @PutMapping(value = "/{productId}/images/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> updateProductImage(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "Image ID", example = "10") @PathVariable Long imageId,
            @RequestPart(value = "image", required = false) MultipartFile newImage,
            @RequestParam(value = "isCoverImage", required = false) Boolean isCoverImage) {
        ProductDto productDto = productService.updateProductImage(productId, imageId, newImage, isCoverImage);
        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Set an image as cover",
            description = "Sets a specific image as the cover image for a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cover image set successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid image data")
            }
    )
    @PatchMapping("/{productId}/images/{imageId}/cover")
    public ResponseEntity<ProductDto> setCoverImage(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "Image ID", example = "10") @PathVariable Long imageId) {
        ProductDto productDto = productService.setCoverImage(productId, imageId);
        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Delete a product image",
            description = "Deletes a specific image from a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Image deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Image not found")
            }
    )
    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ProductDto> deleteProductImage(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "Image ID", example = "10") @PathVariable Long imageId) {
        ProductDto productDto = productService.deleteProductImage(productId, imageId);
        return ResponseEntity.ok(productDto);
    }

} 