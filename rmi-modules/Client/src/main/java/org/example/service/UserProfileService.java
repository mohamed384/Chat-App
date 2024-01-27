package org.example.service;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.DTOs.UserDTO;
import org.example.Utils.CheckFiledValidation;
import org.example.Utils.Enum.ValidationTypes;
import org.example.interfaces.UserAuthentication;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class UserProfileService {

    public boolean updateUserDetails(UserDTO userDTO, TextField nameTextField, Label nameValidLabel,
                                     Label emailValidLabel,TextField emailTextField, Label bioValidLabel,TextField bioTextField) {

        boolean isValidName = CheckFiledValidation.checkFiledValidation(userDTO.getDisplayName(), nameValidLabel,
                nameTextField, ValidationTypes.name);
        boolean isValidEmail = CheckFiledValidation.checkFiledValidation(userDTO.getEmailAddress(), emailValidLabel,
                emailTextField, ValidationTypes.email);
        boolean isValidBio = CheckFiledValidation.checkFiledValidation(userDTO.getBio(), bioValidLabel,
                bioTextField, ValidationTypes.bio);
        if(!(isValidEmail && isValidName&& isValidBio)){
            return false;
        }



        UserAuthentication remoteObject = UserAuthRemoteObject();
        boolean isUpdate = false;
        try {
            isUpdate = remoteObject.updateUser(userDTO);
        } catch (RemoteException e) {
            System.out.println("Error while Updateing User Profile " + e.getMessage());
        }
        return isUpdate;
    }
    private UserAuthentication UserAuthRemoteObject() {
        UserAuthentication remoteObject = null;
        try {
            remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/UserAuthenticationStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }
}
