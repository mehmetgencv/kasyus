package com.kasyus.userservice.mapper;


import com.kasyus.userservice.dto.requests.AddressCreateRequest;
import com.kasyus.userservice.dto.responses.AddressResponse;
import com.kasyus.userservice.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toAddress(AddressCreateRequest request);
    AddressResponse toAddressResponse(Address address);
}

