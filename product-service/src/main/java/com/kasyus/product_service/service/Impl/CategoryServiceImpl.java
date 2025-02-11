package com.kasyus.product_service.service.Impl;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.exception.CategoryNotFoundException;
import com.kasyus.product_service.mapper.CategoryMapper;
import com.kasyus.product_service.model.Category;
import com.kasyus.product_service.repository.CategoryRepository;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;
import com.kasyus.product_service.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return CategoryMapper.INSTANCE.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = findCategoryById(id);
        return CategoryMapper.INSTANCE.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryCreateRequest request) {
        Category category = CategoryMapper.INSTANCE.toCategory(request);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = findCategoryById(id);

        Category category1 = CategoryMapper.INSTANCE.updateCategoryFields(category, request);
        Category updatedCategory = categoryRepository.save(category1);
        return CategoryMapper.INSTANCE.toCategoryDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
