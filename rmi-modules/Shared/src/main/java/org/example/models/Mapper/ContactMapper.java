package org.example.models.Mapper;

import org.example.DTOs.ContactDTO;
import org.example.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {
    org.example.models.Mapper.ContactMapper INSTANCE = Mappers.getMapper( org.example.models.Mapper.ContactMapper.class );

    ContactDTO toDTO(Contact contact);

    Contact toContact(ContactDTO contactDTO);
}