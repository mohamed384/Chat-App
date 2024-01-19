package org.example.controller.services;


import org.example.DTOs.UserDTO;
import org.example.controller.DAO.UserDAOImpl;
import org.example.models.Enums.UserStatus;
import org.example.models.Mapper.UserMapper;
import org.example.models.User;
import org.example.utils.PasswordHashing;

import java.sql.Timestamp;

public class UserService {
    private UserDAOImpl userDAO;
    private UserMapper userMapper = UserMapper.INSTANCE;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public Boolean signup(UserDTO userDto) {
        if (userDto ==null) {
            System.out.println("UserDTO is null");
            return false;
        } else if (userDto.getConfirmPassword() == null) {
            System.out.println("Confirm password is null");
            return false;
        } else if (userDto.getPasswordHash() == null) {
            System.out.println(" password is null");
            return false;

        }

        if (!userDto.getPasswordHash().equals(userDto.getConfirmPassword())) {
            System.out.println("Passwords don't match");
            return false;
        }
        //User user = userMapper.toUser(userDto);

        User user = UserMapper.INSTANCE.toUser(userDto);
        return userDAO.create(user);
    }
    public UserDTO login (String phoneNumber,  String password) {

        User user = UserDAOImpl.findByPhoneNumber(phoneNumber);
        String passwordHash = user.getPasswordHash();
        password = PasswordHashing.hashPassword(password);
        System.out.println(passwordHash);

        if (passwordHash.equals(password)) {
            System.out.println("Login successful");
            //UserDTO userDto = userMapper.toDTO(user);
            UserDTO userDto = UserMapper.INSTANCE.toDTO(user);
            return userDto;
        }

        System.out.println("Login failed");
        return null;

    }

    public boolean logout(UserDTO userDto) {
        if (userDto == null) {
            System.out.println("From logout: UserDTO is null");
            return false;
        }

        User user = UserMapper.INSTANCE.toUser(userDto);

        user.setUserStatus(UserStatus.Offline);

        user.setLastLogin(new Timestamp(System.currentTimeMillis()));

        //User user = userMapper.toUser(userDto);
        return userDAO.update(user);
    }



}