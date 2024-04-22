package com.pccwglobal.user.mapper;

import com.pccwglobal.user.entity.User;
import com.pccwglobal.user.entity.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "active", source = "user.active")
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true) // Ignore the id field when mapping
    User toEntity(UserDTO userDTO);

}

