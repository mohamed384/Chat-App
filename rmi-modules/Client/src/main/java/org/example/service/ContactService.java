package org.example.service;


import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.ChatRMI;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserContact;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class ContactService {
    UserContact remoteContact = null;
    CallBackServer callBackServer = null;

    ChatRMI chatRMI;

    public ContactService() {
        remoteContact = (UserContact) StubContext.getStub("UserContactStub");
        callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");
    }


    private UserContact UserContactRemote() {
        UserContact remoteObject = null;
        try {
            remoteObject =(UserContact)StubContext.getStub("UserContactStub");
           // remoteObject = (UserContact) Naming.lookup("rmi://localhost:1099/UserContactStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    public List<UserDTO> getAllContacts() {
        List<UserDTO> contacts = null;
        try {
            contacts = remoteContact.getAllContactsByUserPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber());
        } catch (Exception e) {
            System.out.println("Error while getting all contacts " + e.getMessage());
        }
        return contacts;
    }

    public UserDTO getUserByFriendID(String friendID) {
        UserDTO user = null;
        try {
            UserAuthentication remoteObject = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
           // UserAuthentication remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/UserAuthenticationStub");
            user = remoteObject.getUser(friendID);
        } catch (Exception e) {
            System.out.println("Error while getting user by friendID " + e.getMessage());
        }
        return user;
    }
    public boolean deleteContact(UserDTO userDTO) {
        chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
        try {
            boolean isDeleted = chatRMI.deleteChat(UserToken.getInstance().getUser().getPhoneNumber(), userDTO.getPhoneNumber());
            boolean deleted = remoteContact.deleteContact(UserToken.getInstance().getUser().getPhoneNumber(), userDTO.getPhoneNumber());
            callBackServer.updateContactList(UserToken.getInstance().getUser().getPhoneNumber());
            callBackServer.updateContactList(userDTO.getPhoneNumber());
            return deleted && isDeleted;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while deleting contact " + e.getMessage());
        }
        return false;

    }




}
