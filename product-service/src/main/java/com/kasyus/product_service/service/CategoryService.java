package com.kasyus.product_service.service;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto createCategory(CategoryCreateRequest category);
    CategoryDto updateCategory(Long id, CategoryUpdateRequest category);
    void deleteCategory(Long id);
}
