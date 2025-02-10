package com.kasyus.product_service.mapper;

import com.kasyus.product_service.dto.CategoryDto;
import com.kasyus.product_service.model.Category;
import com.kasyus.product_service.requests.CategoryCreateRequest;
import com.kasyus.product_service.requests.CategoryUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CategoryDto toCategoryDto(Category category);


    Category toCategory(CategoryDto categoryDto);


    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    Category toCategory(CategoryUpdateRequest categoryUpdateRequest);
}
