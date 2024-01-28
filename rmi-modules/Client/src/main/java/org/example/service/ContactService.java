package org.example.service;


import org.example.DTOs.UserDTO;
import org.example.Utils.UserToken;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserContact;

import java.rmi.Naming;
import java.util.List;

public class ContactService {

    private UserContact UserContactRemote() {
        UserContact remoteObject = null;
        try {
            remoteObject = (UserContact) Naming.lookup("rmi://localhost:1099/UserContactStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    public List<UserDTO> getAllContacts() {
        UserContact remoteObject = UserContactRemote();
        List<UserDTO> contacts = null;
        try {
            contacts = remoteObject.getAllContactsByUserPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber());
        } catch (Exception e) {
            System.out.println("Error while getting all contacts " + e.getMessage());
        }
        return contacts;
    }

    public UserDTO getUserByFriendID(String friendID) {
        UserDTO user = null;
        try {
            UserAuthentication remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/UserAuthenticationStub");
            user = remoteObject.getUser(friendID);
        } catch (Exception e) {
            System.out.println("Error while getting user by friendID " + e.getMessage());
        }
        return user;
    }


}
