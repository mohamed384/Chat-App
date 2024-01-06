package org.example.controller.implementations;



import org.example.interfaces.UserAuthentication;
import org.example.model.entities.User;
import org.example.utils.DBConnection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class UserController extends UnicastRemoteObject implements UserAuthentication {

    public UserController ()  throws RemoteException{
        super();
    }

    public boolean signup() {
        User user;

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                {
                try  {
                    String query = "INSERT INTO users (email, password_hash, gender, country, bio, status, last_seen,phone_number,display_name,date_of_birth) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, user.getEmail());
                        preparedStatement.setString(2, user.getPasswordHash());
                        preparedStatement.setString(3, user.getGender());
                        preparedStatement.setString(4, user.getCountry());
                        preparedStatement.setString(5, user.getBio());
                        preparedStatement.setString(6, user.getStatus().name());
                        preparedStatement.setTimestamp(7, user.getLastSeen());
                        preparedStatement.setString(8, user.getPhoneNumber());
                        preparedStatement.setString(9, user.getDisplayName());
                        Date sqlDateOfBirth = new Date( user.getDateOfBirth().getTime());

                        preparedStatement.setDate(10,sqlDateOfBirth);

                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
}
