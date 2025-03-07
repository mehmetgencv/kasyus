package com.kasyus.userservice.mapper;

import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;
import com.kasyus.userservice.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    @Mapping(target = "user", ignore = true)
    PaymentMethod toPaymentMethod(PaymentMethodCreateRequest request);

    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
}