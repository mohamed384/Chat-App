package org.example.controller.services;


import org.example.DTOs.UserDTO;
import org.example.controller.DAO.UserDAO;
import org.example.models.Mapper.UserMapper;
import org.example.models.User;
import org.example.utils.PasswordHashing;

import java.sql.SQLOutput;

public class UserService {
    private UserDAO userDAO;
    private UserMapper userMapper = new UserMapper();

    public UserService() {
        this.userDAO = new UserDAO();
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
        User user = userMapper.toUser(userDto);
        return userDAO.create(user);
    }
    public UserDTO login (String phoneNumber,String password) {

        User user = UserDAO.findByPhoneNumber(phoneNumber);
        if(user !=null){
            String passwordHash = user.getPasswordHash();
            password = PasswordHashing.hashPassword(password);
            if (passwordHash.equals(password)) {
                System.out.println("Login successful");
                return userMapper.toDTO(user);
            }
        }


        System.out.println("Login failed");
        return null;

    }



}