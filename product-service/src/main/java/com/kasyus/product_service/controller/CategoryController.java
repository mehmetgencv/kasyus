package com.kasyus.product_service.controller;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.general.RestResponse;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;
import com.kasyus.product_service.service.CategoryService;
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

    @GetMapping
    public ResponseEntity<RestResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(RestResponse.of(categories, "Categories retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(RestResponse.of(category, "Category retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<RestResponse<CategoryDto>> createCategory(@RequestBody CategoryCreateRequest request) {
        CategoryDto createdCategory = categoryService.createCategory(request);
        return ResponseEntity.status(201).body(RestResponse.of(createdCategory, "Category created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<CategoryDto>> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequest category) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(RestResponse.of(updatedCategory, "Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(RestResponse.of(null, "Category deleted successfully"));
    }
}
