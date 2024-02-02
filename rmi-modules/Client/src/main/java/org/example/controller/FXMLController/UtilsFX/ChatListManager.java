package org.example.controller.FXMLController.UtilsFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.DTOs.UserDTO;

public class ChatListManager {
    private static ChatListManager instance;
    private ObservableList<UserDTO> contacts;

    private ChatListManager() {
        contacts = FXCollections.observableArrayList();
    }

    public static ChatListManager getInstance() {
        if (instance == null) {
            instance = new ChatListManager();
        }
        return instance;
    }

    public void addContact(UserDTO contact) {
        for(UserDTO user : contacts){
            if( user.getPhoneNumber().equals(contact.getPhoneNumber())){
                return;
            }
        }
        contacts.add(contact);
    }
    public ObservableList<UserDTO> getContacts() {
        return contacts;
    }

}