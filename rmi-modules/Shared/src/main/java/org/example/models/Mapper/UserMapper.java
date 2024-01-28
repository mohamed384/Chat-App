package org.example.models.Mapper;

import org.example.DTOs.UserDTO;
import org.example.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
    public interface UserMapper {

    org.example.models.Mapper.UserMapper INSTANCE = Mappers.getMapper( org.example.models.Mapper.UserMapper.class );


    UserDTO toDTO(User user);

    //@Mapping(target = "userStatus", source = "userStatus")
    User toUser(UserDTO userDTO);

}
