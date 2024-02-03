package org.example.models.Mapper;

import org.example.DTOs.ChatDTO;
import org.example.DTOs.MessageDTO;
import org.example.models.Chat;
import org.example.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    org.example.models.Mapper.MessageMapper INSTANCE = Mappers.getMapper( org.example.models.Mapper.MessageMapper.class );

    MessageDTO toDTO(Message Message);

    Message toMessage(ChatDTO MessageDTO);
}
