package org.example.controller.DAO;

import org.example.DTOs.UserDTO;
import org.example.models.User;
import org.example.models.UserStatus;
import org.example.utils.DBConnection;
import org.example.utils.PasswordHashing;
import org.example.utils.PictureConverter;

import java.sql.*;

public class UserDAO implements DAO<UserDTO>{

    @Override
    public boolean create(UserDTO userDto) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String checkQuery = "SELECT * FROM users WHERE phone_number = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                    checkStatement.setString(1, userDto.getPhoneNumber());
                    ResultSet resultSet = checkStatement.executeQuery();
                    if (resultSet.next()) {
                        System.out.println("Phone number already exists");
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String query = "INSERT INTO users (phone_number, display_name, email, password_hash, gender, country, date_of_birth, bio, status ,last_seen,picture) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, userDto.getPhoneNumber());
                    preparedStatement.setString(2, userDto.getDisplayName());

                    preparedStatement.setString(3, userDto.getEmail());
                    preparedStatement.setString(4, PasswordHashing.hashPassword(userDto.getPasswordHash()));
                    preparedStatement.setString(5, userDto.getGender());
                    preparedStatement.setString(6, userDto.getCountry());
                    Date sqlDateOfBirth = new Date(userDto.getDateOfBirth().getTime());
                    preparedStatement.setDate(7, sqlDateOfBirth);
                    if(userDto.getBio().equals("")) {
                        preparedStatement.setString(8, "Hi there! I'm using CypherChat");
                    }
                    preparedStatement.setString(9, userDto.getStatus().name());
                    preparedStatement.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

                    byte[] picture;
                    System.out.println(userDto.getPicture());
                    if (userDto.getPicture().equals("") || userDto.getPicture().indexOf("javafx") != -1) {
                        picture = new PictureConverter().getDefaultPictureData();
                        System.out.println("Default picture");
                    }
                    else {
                        // Load picture data from a file
                        System.out.println("User picture");
                        picture = new PictureConverter().getPictureData(userDto.getPicture());
                    }
                    preparedStatement.setBytes(11, picture);


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
                String query = "SELECT * FROM users WHERE phone_number = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, phoneNumber);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            UserStatus status;
                            /*
                            try {
                                status = ;
                            } catch (IllegalArgumentException e) {
                                status = UserStatus.AVAILABLE; // Replace DEFAULT with your default UserStatus
                            }*/
                            // Assuming you have a User constructor that takes all fields as parameters
                            user = new User(
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getDate(7),
                                    resultSet.getString(8),
                                    UserStatus.valueOf(resultSet.getString(9).toUpperCase()),
                                    resultSet.getString(10)
                            );

                        } else {
                            System.out.println("notfound user");
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
