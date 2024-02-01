package org.example.models.Mapper;

import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ChatMapper {

    org.example.models.Mapper.ChatMapper INSTANCE = Mappers.getMapper( org.example.models.Mapper.ChatMapper.class );


    ChatDTO toDTO(Chat chat);

    Chat toUser(ChatDTO chatDTO);

}
