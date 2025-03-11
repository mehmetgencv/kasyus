package com.kasyus.product_service.mapper;

import com.kasyus.product_service.dto.ImageDto;
import com.kasyus.product_service.dto.ProductDto;
import com.kasyus.product_service.model.Product;
import com.kasyus.product_service.model.ProductImage;
import com.kasyus.product_service.requests.ProductCreateRequest;
import com.kasyus.product_service.requests.ProductUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductCreateRequest productCreateRequest);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    Product updateProductFields(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(target = "coverImageUrl", source = "images", qualifiedByName = "mapCoverImage")
    @Mapping(target = "imageUrls", source = "images", qualifiedByName = "mapImages")
    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

    @Named("mapImages")
    default List<ImageDto> mapImages(List<ProductImage> images) {
        return images.stream()
                .sorted(Comparator.comparing(ProductImage::isCoverImage).reversed())
                .map(image -> new ImageDto(image.getId(), image.getImageUrl()))
                .collect(Collectors.toList());
    }

    @Named("mapCoverImage")
    default String mapCoverImage(List<ProductImage> images) {
        return images.stream()
                .filter(ProductImage::isCoverImage)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }
}
