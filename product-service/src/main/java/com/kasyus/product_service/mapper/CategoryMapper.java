package com.kasyus.product_service.mapper;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.model.Category;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CategoryDto toCategoryDto(Category category);


    Category toCategory(CategoryDto categoryDto);


    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    @Mapping(target = "id", ignore = true)
    Category updateCategoryFields(@MappingTarget Category category,CategoryUpdateRequest categoryUpdateRequest);

    List<CategoryDto> toCategoryDtoList(List<Category> categories);

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto productToProductDto(Product product);
}

