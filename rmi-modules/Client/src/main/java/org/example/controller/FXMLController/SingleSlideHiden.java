package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.interfaces.UserAuthentication;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class SingleSlideHiden  implements Initializable {

    @FXML
    private Label PhoneNumber;

    @FXML
    private Label ProfileBio;

    @FXML
    private Label ProfileName;

    @FXML
    private Circle ProfilePhoto;

    @FXML
    private Label profileCountry;

    @FXML
    private Label profileEmail;

    @FXML
    private Label profileGender;


    private  String name ;

    private  String Number;

    private Image image;

    private String bio;

    private String country;
    private String email;
    private String gender;


    private UserDTO getUser(String receiver) {
        UserAuthentication userAuthentication = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
        UserDTO user = null;
        try {
            System.out.println("SingleSlideHiden: getUser");
            user = userAuthentication.getUser(receiver);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return user;
    }




    public void setData(String receiver, byte[] image, List<String> list) {
        name = receiver;
        Number = list.get(0);
        this.image = new Image(new ByteArrayInputStream(image));
        System.out.println("SingleSlideHiden: setData");
        UserDTO user = getUser(list.get(0));
        System.out.println(user);
        bio = user.getBio();
        country = user.getCountry();
        email = user.getEmailAddress();
        gender = user.getGender();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SingleSlideHiden: initialize");
        ProfileName.setText(name);
        Circle clip = new Circle(45, 45, 50);
        ProfilePhoto.setClip(clip);
        ProfilePhoto.setFill(new ImagePattern(image));
        PhoneNumber.setText(Number);
        System.out.println("SingleSlideHiden: initialize");
        ProfileBio.setText(bio);
        profileCountry.setText(country);
        profileEmail.setText(email);
        profileGender.setText(gender);


    }
}
