package com.kasyus.userservice.mapper;

import com.kasyus.userservice.dto.responses.UserProfileResponse;
import com.kasyus.userservice.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.id", target = "id")
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}