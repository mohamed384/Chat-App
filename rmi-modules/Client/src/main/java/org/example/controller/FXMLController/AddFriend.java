package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.DTOs.UserDTO;
import org.example.Utils.SessionManager;
import org.example.interfaces.UserAuthentication;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class AddFriend {


    @FXML
    private TextField friendNumberTxt;

    @FXML
    private ListView<String> listview;
    private SessionManager sessionManager;

    private UserAuthentication UserAuthRemoteObject() {
        UserAuthentication remoteObject = null;
        try {
            remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/rmiObject");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    @FXML
    void searchOnUser(ActionEvent event)  {

        sessionManager = SessionManager.getInstance();
        UserDTO userDTO = null;
        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            String phoneNumber = friendNumberTxt.getText();
            System.out.println("Phone Number: " + phoneNumber);
            try {
                userDTO = remoteObject.getUser(phoneNumber);
                System.out.println("UserDTO: " + userDTO);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (userDTO != null) {
            String displayName = userDTO.getDisplayName();
            System.out.println("Display Name: " + displayName);
            listview.getItems().add(displayName);
        } else {
            System.out.println("No user found with the provided phone number.");
        }

    }

    @FXML
    void sendAdd(ActionEvent event) {


    }
}
