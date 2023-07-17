package com.glaze.flow.mappers;

import com.glaze.flow.dtos.request.SignUpRequest;
import com.glaze.flow.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "username", target = "displayUsername")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "profilePicture", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    User signUpRequestToUser(SignUpRequest request);

}
