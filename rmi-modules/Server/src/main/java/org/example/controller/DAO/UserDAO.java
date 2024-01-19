package org.example.controller.DAO;


import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;
import org.example.models.User;
import org.example.utils.DBConnection;
import org.example.utils.PasswordHashing;
import org.example.utils.PictureConverter;

import java.sql.*;

public class UserDAO implements DAO<User>{

    @Override
    public boolean create(User user) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String checkQuery = "SELECT * FROM users WHERE phonenumber = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, user.getPhoneNumber());
                    ResultSet resultSet = checkStatement.executeQuery();
                    if (resultSet.next()) {
                        System.out.println("Phone number already exists");
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (user == null) {
                    System.out.println("User is null");
                } else if (user.getUserStatus() == null) {
                    System.out.println("User status is null");
                } else {
                    System.out.println(user.getUserStatus().name());
                }

                String sql = "INSERT INTO Users (PhoneNumber, DisplayName, EmailAddress, " +
                        "ProfilePicture, PasswordHash, Gender, Country, DateOfBirth, Bio, " +
                        "UserMode, UserStatus, LastLogin) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, user.getPhoneNumber());
                    preparedStatement.setString(2, user.getDisplayName());

                    preparedStatement.setString(3, user.getEmailAddress());
                    byte[] picture;
                    System.out.println(user.getPicture());
                    if (user.getPicture().isEmpty() || user.getPicture().contains("javafx")) {
                        picture = new PictureConverter().getDefaultPictureData();
                        System.out.println("Default picture");
                    }
                    else {
                        // Load picture data from a file
                        System.out.println("User picture");
                        picture = new PictureConverter().getPictureData(user.getPicture());
                    }
                    preparedStatement.setBytes(4, picture);
                    preparedStatement.setString(5, PasswordHashing.hashPassword(user.getPasswordHash()));

                    preparedStatement.setString(6, user.getGender());
                    preparedStatement.setString(7, user.getCountry());
                    Date sqlDateOfBirth = new Date(user.getDateOfBirth().getTime());
                    preparedStatement.setDate(8, sqlDateOfBirth);
                    if(user.getBio().isEmpty()) {
                        preparedStatement.setString(9, "Hi there! I'm using CypherChat");
                    }
                    System.out.println(user.getEmailAddress());
                    preparedStatement.setString(10, user.getUserMode());
                    System.out.println(user.getUserStatus().name());
                    preparedStatement.setString(11, user.getUserStatus().name());
                    preparedStatement.setTimestamp(12, new Timestamp(System.currentTimeMillis()));

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect to the database.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;


    }

    public static User findByPhoneNumber(String phoneNumber) {
        User user = null;

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM users WHERE phonenumber = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, phoneNumber);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {

                            user = new User(
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getString(7),
                                    resultSet.getString(8),
                                    resultSet.getDate(9),
                                    resultSet.getString(10),
                                    UserStatus.valueOf(resultSet.getString(12)),
                                   resultSet.getString(11),
                                    resultSet.getTimestamp(13)

                            );

                        } else {
                            System.out.println("not found user");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to establish a database connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }



}
