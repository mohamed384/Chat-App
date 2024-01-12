package org.example.controller.services;


import org.example.DTOs.UserDTO;
import org.example.controller.DAO.UserDAO;
import org.example.models.User;
import org.example.utils.PasswordHashing;

import java.sql.SQLOutput;

public class UserService {
    private UserDAO userDAO;

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
        return userDAO.create(userDto);
    }
    public User login (String phoneNumber,  String password) {

        User user = UserDAO.findByPhoneNumber(phoneNumber);
        String passwordHash = user.getPasswordHash();
        password = PasswordHashing.hashPassword(password);
        System.out.println(passwordHash);

        if (passwordHash.equals(password)) {
            System.out.println("Login successful");
            return user;
        }

        System.out.println("Login failed");
        return null;

    }



}