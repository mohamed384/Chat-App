package org.example;
import org.example.controller.implementations.AuthenticationImpl;
import org.example.interfaces.UserAuthentication;
import org.example.utils.DBConnection;

import java.sql.Connection;


public class Server {

    public static void main(String[] args) {
        try {
            UserAuthentication stub = new AuthenticationImpl();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmiObject", stub);
            Connection connection = DBConnection.getConnection();
            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
