package com.kasyus.userservice.mapper;

import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.requests.PaymentMethodUpdateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;
import com.kasyus.userservice.model.PaymentMethod;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.YearMonth;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expiryDate", source = "expiryDate", qualifiedByName = "yearMonthToLocalDate")
    PaymentMethod toPaymentMethod(PaymentMethodCreateRequest request);

    @Mapping(target = "expiryDate", source = "expiryDate", qualifiedByName = "localDateToYearMonth")
    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "expiryDate", source = "expiryDate", qualifiedByName = "yearMonthToLocalDate")
    void updatePaymentMethod(@MappingTarget PaymentMethod paymentMethod, PaymentMethodUpdateRequest request);

    @Named("yearMonthToLocalDate")
    static LocalDate yearMonthToLocalDate(YearMonth yearMonth) {
        return yearMonth != null ? yearMonth.atDay(1) : null;
    }

    @Named("localDateToYearMonth")
    static YearMonth localDateToYearMonth(LocalDate localDate) {
        return localDate != null ? YearMonth.from(localDate) : null;
    }
}
