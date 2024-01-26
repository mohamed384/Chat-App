package org.example.service;


//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.DTOs.UserDTO;
import org.example.Utils.SessionManager;
import org.example.Utils.UserDataValidator;
import org.example.interfaces.UserAuthentication;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class UserAuthService {


    private byte[] convertImageToByteArray(Image image)  {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] imageBytes = new byte[width * height * 4]; // Assuming 4 bytes per pixel (RGBA)

        PixelReader pixelReader = image.getPixelReader();
        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                imageBytes[pixelIndex++] = (byte) (color.getRed() * 255);     // Red
                imageBytes[pixelIndex++] = (byte) (color.getGreen() * 255);   // Green
                imageBytes[pixelIndex++] = (byte) (color.getBlue() * 255);    // Blue
                imageBytes[pixelIndex++] = (byte) (color.getOpacity() * 255); // Alpha
            }
        }

        return imageBytes;
    }



    protected void switchToMessagePage(ActionEvent actionEvent) {

        Parent newScreenParent;
        try {
            newScreenParent = FXMLLoader.load(getClass().getResource("/views/MainPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene newScreenScene = new Scene(newScreenParent);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        currentStage.setScene(newScreenScene);

        currentStage.show();
    }

    private enum ValidationType {
        phone,
        name,
        email,
        password

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

    public UserDTO login(ActionEvent actionEvent, PasswordField PasswordLog, TextField PhoneLog) throws IOException {
        String password = PasswordLog.getText();
        String phone = PhoneLog.getText();
        UserAuthentication remoteObject = UserAuthRemoteObject();


        UserDTO user;
        if (remoteObject != null) {
            user = remoteObject.login(phone, password);
            if (user != null) {
                switchToMessagePage(actionEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid phone number or password");
                alert.showAndWait();
                PhoneLog.getStyleClass().remove("custom-text-field");
                PhoneLog.getStyleClass().add("not-valid-text-field");
                PasswordLog.getStyleClass().remove("custom-text-field");
                PasswordLog.getStyleClass().add("not-valid-text-field");
            }
        } else {
            return null;
        }

        PasswordLog.setText("");
        PhoneLog.setText("");
        return user;
    }

    public boolean checkFiledValidation(String text, Label TextLabel, TextField textfield, ValidationType validationType) {
        boolean isValid = false;
        switch (validationType) {
            case phone:
                isValid = UserDataValidator.isValidPhoneNumber(text);
                System.out.println("Phone validation: " + isValid);

                break;
            case name:
                isValid = UserDataValidator.isValidName(text);
                System.out.println("Name validation: " + isValid);

                break;
            case email:
                isValid = UserDataValidator.isValidEmail(text);
                System.out.println("email validation: " + isValid);

                break;
            case password:
                isValid = UserDataValidator.isValidPassword(text);
                System.out.println("password validation: " + text);
                break;
            default:
                break;
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

    public Boolean signup(ActionEvent actionEvent, TextField phoneSignUp, TextField nameSignUp,
                       TextField emailSignUp, String selectedCountry, DatePicker birthDateSignUp
            , PasswordField passwordSignUp, PasswordField passwordConfirmationSignUp, ImageView imageSignUp,
                       String selectedGender, Label phoneValidLabel, Label nameValidLabel,
                       Label emailValidLabel, Label passwordValidLabel, Label confirmPassValidLabel, Label counrtyValidLabel) throws IOException {

        String phone = phoneSignUp.getText();

        String name = nameSignUp.getText();

        String email = emailSignUp.getText();

        LocalDate selectedLocalDate = birthDateSignUp.getValue();
        Date birthDate = Date.from(selectedLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        String password = passwordSignUp.getText();

        String passwordConfirm = passwordConfirmationSignUp.getText();

        byte[] image = convertImageToByteArray(imageSignUp.getImage());


        boolean isValidPhone = checkFiledValidation(phone, phoneValidLabel, phoneSignUp, ValidationType.phone);
        boolean isValidName = checkFiledValidation(name, nameValidLabel, nameSignUp, ValidationType.name);
        boolean isValidEmail = checkFiledValidation(email, emailValidLabel, emailSignUp, ValidationType.email);
        boolean isValidPassword = checkFiledValidation(password, passwordValidLabel, passwordSignUp, ValidationType.password);
        if (selectedCountry == null) {
            counrtyValidLabel.getStyleClass().remove("valid-label");
            counrtyValidLabel.getStyleClass().add("not-valid-label");
            return false;
        } else {
            counrtyValidLabel.getStyleClass().add("valid-label");
            counrtyValidLabel.getStyleClass().remove("not-valid-label");
        }
        if (isValidPassword) {
            if (!passwordConfirm.equals(password)) {
                confirmPassValidLabel.getStyleClass().remove("valid-label");
                confirmPassValidLabel.getStyleClass().add("not-valid-label");
                passwordConfirmationSignUp.getStyleClass().remove("custom-text-field");
                passwordConfirmationSignUp.getStyleClass().add("not-valid-text-field");
                return false;


            } else {
                confirmPassValidLabel.getStyleClass().add("valid-label");
                confirmPassValidLabel.getStyleClass().remove("not-valid-label");
                passwordConfirmationSignUp.getStyleClass().add("custom-text-field");
                passwordConfirmationSignUp.getStyleClass().remove("not-valid-text-field");
            }
        }
        if (!(isValidPhone && isValidName && isValidEmail && isValidPassword)) {
            return false;
        }


        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            UserDTO user1 = new UserDTO(phone, name, email, password, passwordConfirm, selectedGender, selectedCountry,
                    birthDate, "", UserStatus.Online, UserMode.Available, image);
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.startSession(user1);
            remoteObject.signup(user1);
            System.out.println("signup from client auth service is successfully done");

            switchToMessagePage(actionEvent);
            return true;

        } else {
            System.out.println("Cant connect to server");
            return false;
        }


    }
}
