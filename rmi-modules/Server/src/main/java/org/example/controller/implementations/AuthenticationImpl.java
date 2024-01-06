package org.example.controller.implementations;



import org.example.interfaces.UserAuthentication;
import org.example.utils.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationImpl extends UnicastRemoteObject implements UserAuthentication {


    public AuthenticationImpl() throws RemoteException {
        super();
    }

    public boolean signup(String number, String password) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                // Example select query
                String selectQuery = "SELECT ssn, fname FROM employee";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Process the result set
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ssn");
                        String name = resultSet.getString("fname");
                        System.out.println("ID: " + id + ", Name: " + name);
                    }

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
}
