package org.example.models.Mapper;

import org.example.DTOs.UserDTO;
import org.example.models.User;
import org.example.models.UserStatus;

import java.util.Date;

public class UserMapper {

    public UserMapper() {
    }

    /* ====================== USER CREATION MAPPER ==================== */
    public UserDTO toDTO(User user) {
        String name = user.getDisplayName();
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String gender = user.getGender();
        String country = user.getCountry();
        Date dateOfBirth = user.getDateOfBirth();
        String bio = user.getBio();
        UserStatus status = user.getStatus();
        String picture = user.getPicture();

        return new UserDTO(phoneNumber, name, email, gender, country,
                dateOfBirth, bio, status, picture);
    }

    public User toUser(UserDTO userDTO) {
        User user = new User(userDTO.getPhoneNumber(), userDTO.getDisplayName(), userDTO.getEmail(),
                userDTO.getPasswordHash(), userDTO.getGender(), userDTO.getCountry(),
                userDTO.getDateOfBirth(), userDTO.getBio(), userDTO.getStatus(), userDTO.getPicture());
        user.toString();
        return user;
    }
    /* ================================================================ */
}
