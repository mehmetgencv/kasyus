package com.kasyus.product_service.controller;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.general.RestResponse;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;
import com.kasyus.product_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all product categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @GetMapping
    public ResponseEntity<RestResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(RestResponse.of(categories, "Categories retrieved successfully"));
    }

    @Operation(summary = "Get a category by ID", description = "Retrieves a single category by its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<CategoryDto>> getCategoryById(
            @Parameter(description = "Category ID", example = "1") @PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(RestResponse.of(category, "Category retrieved successfully"));
    }

    @Operation(summary = "Create a new category", description = "Creates a new product category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category data")
    })
    @PostMapping
    public ResponseEntity<RestResponse<CategoryDto>> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {
        CategoryDto createdCategory = categoryService.createCategory(request);
        return ResponseEntity.status(201).body(RestResponse.of(createdCategory, "Category created successfully"));
    }

    @Operation(summary = "Update an existing category", description = "Updates the details of an existing category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<CategoryDto>> updateCategory(
            @Parameter(description = "Category ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest category) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(RestResponse.of(updatedCategory, "Category updated successfully"));
    }

    @Operation(summary = "Delete a category", description = "Deletes a category by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteCategory(
            @Parameter(description = "Category ID", example = "1") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(RestResponse.of(null, "Category deleted successfully"));
    }
}
