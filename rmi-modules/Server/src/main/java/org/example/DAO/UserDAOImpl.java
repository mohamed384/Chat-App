package org.example.DAO;


import org.example.models.Enums.UserStatus;
import org.example.models.User;
import org.example.utils.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.Base64;

public class UserDAOImpl implements DAO<User> {

    @Override
    public boolean create(User user) {
        if (!validData(user)) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {

                User existingUser = findByPhoneNumber(user.getPhoneNumber());
                if (existingUser != null) {
                    System.out.println("Phone number already exists");
                    return false;
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
                   // Blob picture = ImageConvertor.bytesToBlob(user.getPicture());

                    preparedStatement.setBytes(4, user.getPicture());
                    preparedStatement.setString(5, PasswordHashing.hashPassword(user.getPasswordHash()));

                    preparedStatement.setString(6, user.getGender());
                    preparedStatement.setString(7, user.getCountry());
                    Date sqlDateOfBirth = new Date(user.getDateOfBirth().getTime());
                    preparedStatement.setDate(8, sqlDateOfBirth);
                    if (user.getBio().isEmpty()) {
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

    @Override
    public boolean update(User user) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {

                String sql = "UPDATE Users " +
                        "SET DisplayName = ?, " +
                        "    EmailAddress = ?, " +
                        "    ProfilePicture = ?, " +
                        "    Bio = ? " +
                        "WHERE PhoneNumber = ?";

                //ImageConvertor.bytesToBlob(user.getPicture())
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, user.getDisplayName());
                    preparedStatement.setString(2, user.getEmailAddress());
                    preparedStatement.setBytes(3,user.getPicture());
                    preparedStatement.setString(4, user.getBio());
                    preparedStatement.setString(5, user.getPhoneNumber());
                    preparedStatement.executeUpdate();
                    System.out.println("Updated Successfully");
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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


                         //   Blob picture = resultSet.getBlob(5);
                          //  byte[] pictureBytes = ImageConvertor.BlobToBytes(picture);

                            user = new User(
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getBytes(5),
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

    public boolean validData(User user) {
        if (user == null) {
            System.out.println("User is null");
            return false;
        }

        if (!UserDataValidator.isValidPhoneNumber(user.getPhoneNumber())) {
            System.out.println("Invalid phone number");
            return false;
        }
        if (!UserDataValidator.isValidName(user.getDisplayName())) {
            System.out.println("Invalid name");
            return false;
        }
        if (!UserDataValidator.isValidEmail(user.getEmailAddress())) {
            System.out.println("Invalid email");
            return false;
        }
        if (!UserDataValidator.isValidPassword(user.getPasswordHash())) {
            System.out.println("Invalid password");
            return false;
        }
        return true;
    }

}
