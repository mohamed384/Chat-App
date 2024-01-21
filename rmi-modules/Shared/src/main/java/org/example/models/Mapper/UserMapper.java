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

    /* ====================== USER CREATION MAPPER ==================== */
//    public UserDTO toDTO(User user) {
//        String name = user.getDisplayName();
//        String phoneNumber = user.getPhoneNumber();
//        String email = user.getEmailAddress();
//        String gender = user.getGender();
//        String country = user.getCountry();
//        Date dateOfBirth = user.getDateOfBirth();
//        String bio = user.getBio();
//        UserStatus status = user.getUserStatus();
//        String picture = user.getPicture();
//
//        return new UserDTO(phoneNumber, name, email, gender, country,
//                dateOfBirth, bio, status, picture);
//    }
//
//    public User toUser(UserDTO userDTO) {
//        User user = new User(userDTO.getPhoneNumber(), userDTO.getDisplayName(), userDTO.getEmail(), userDTO.getPicture(),
//                userDTO.getPasswordHash(), userDTO.getGender(), userDTO.getCountry(),
//                userDTO.getDateOfBirth(), userDTO.getBio(), userDTO.getStatus(), UserMode.Busy.name(), new Timestamp(System.currentTimeMillis()));
//        user.toString();
//        return user;
//    }
    /* ================================================================ */
}
