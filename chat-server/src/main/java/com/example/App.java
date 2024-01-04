package com.example;

import com.example.DataAccessLayer.DbConnection;
import com.example.controller.UserController;
import com.example.model.User;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Connection connection = DbConnection.getMyConnection();
//        Timestamp lastSeenTime = Timestamp.valueOf("2022-01-01 12:00:00");
//
//        Date dateOfBirth = new Date();
//        Timestamp lastSeen = Timestamp.valueOf("2022-01-01 12:00:00");

//        // Create a User object using the constructor
//        User user = new User("123456789", "John Doe", "john@example.com",
//                "hashedPassword", "Male", "Country", dateOfBirth,
//                "This is my bio", User.UserStatus.ONLINE, lastSeen);
//        UserController userController = new UserController(user);

    }
}
