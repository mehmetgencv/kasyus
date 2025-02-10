package com.kasyus.product_service.mapper;

import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductDto productDto);

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductCreateRequest productCreateRequest);

    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductUpdateRequest productUpdateRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    Product updateProductFields(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);
}
