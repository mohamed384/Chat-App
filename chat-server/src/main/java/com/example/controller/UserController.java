package com.example.controller;

import com.example.model.User;
import com.example.view.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {
    private final User user;
    private UserView view;
    public UserController(User user) {
        this.user = user;
       // this.view = view;
    }
    public void addUser(Connection connection) {
        try  {
            String query = "INSERT INTO user (email, password_hash, gender, country, bio, status, last_seen) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPasswordHash());
                preparedStatement.setString(3, user.getGender());
                preparedStatement.setString(4, user.getCountry());
                preparedStatement.setString(5, user.getBio());
                preparedStatement.setString(6, user.getStatus());
                preparedStatement.setTimestamp(7, user.getLastSeen());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
