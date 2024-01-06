package org.example;

import org.example.interfaces.UserAuthentication;

import java.rmi.Naming;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        try {
            UserAuthentication remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/rmiObject");
            boolean x=false;
           // System.out.println( x = remoteObject.signup());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
