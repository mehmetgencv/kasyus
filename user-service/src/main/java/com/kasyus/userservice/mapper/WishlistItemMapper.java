package com.kasyus.userservice.mapper;

import com.kasyus.userservice.dto.requests.WishlistItemCreateRequest;
import com.kasyus.userservice.dto.responses.WishlistItemResponse;
import com.kasyus.userservice.model.WishlistItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistItemMapper {

    WishlistItemMapper INSTANCE = Mappers.getMapper(WishlistItemMapper.class);

    @Mapping(target = "user", ignore = true)
    WishlistItem toWishlistItem(WishlistItemCreateRequest request);
    WishlistItemResponse toWishlistItemResponse(WishlistItem wishlistItem);

}
