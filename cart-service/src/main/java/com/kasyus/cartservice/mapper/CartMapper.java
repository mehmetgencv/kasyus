package com.kasyus.cartservice.mapper;

import com.kasyus.cartservice.dto.requests.CartCreateRequest;
import com.kasyus.cartservice.dto.responses.CartResponse;
import com.kasyus.cartservice.model.Cart;
import com.kasyus.cartservice.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "itemCount", expression = "java(cart.getItems().size())")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartResponse toCartResponse(Cart cart);

    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "id", ignore = true)
    CartItem toCartItem(CartCreateRequest request, Cart cart);

}

