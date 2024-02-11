package org.example.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Utils.Enum.ValidationTypes;

public class CheckFiledValidation {
    public static boolean checkFiledValidation(String text, Label TextLabel, TextField textfield, ValidationTypes validationType) {
        boolean isValid = false;
        switch (validationType) {
            case phone -> {
                isValid = UserDataValidator.isValidPhoneNumber(text);
            }
            case name -> {
                isValid = UserDataValidator.isValidName(text);
            }
            case email -> {
                isValid = UserDataValidator.isValidEmail(text);
            }
            case password -> {
                isValid = UserDataValidator.isValidPassword(text);
            }
            case bio -> {
                isValid = UserDataValidator.isValidBio(text);
            }
            default -> {
            }
        }


        if (!isValid) {
            TextLabel.getStyleClass().add("not-valid-label");
            TextLabel.getStyleClass().remove("valid-label");
            textfield.getStyleClass().remove("custom-text-field");
            textfield.getStyleClass().add("not-valid-text-field");
            return false;
        } else {
            TextLabel.getStyleClass().add("valid-label");
            TextLabel.getStyleClass().remove("not-valid-label");
            textfield.getStyleClass().remove("not-valid-text-field");
            textfield.getStyleClass().add("custom-text-field");
            return true;

        }
    }
}
