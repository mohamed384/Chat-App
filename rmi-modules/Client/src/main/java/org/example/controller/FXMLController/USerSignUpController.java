package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.example.models.Enums.Gender;
import org.example.service.UserAuthService;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class USerSignUpController implements Initializable {

    @FXML
    private Label phoneValidLabel;
    @FXML
    private Label nameValidLabel;
    @FXML
    private Label emailValidLabel;
    @FXML
    private Label counrtyValidLabel;
    @FXML
    private Label passwordValidLabel;
    @FXML
    private Label confirmPassValidLabel;

    //SignUp fields
    @FXML
    TextField phoneSignUp;
    @FXML
    TextField NameSignUp;
    @FXML
    TextField EmailLogin;
    @FXML
    TextField CountrySignUp;
    @FXML
    DatePicker birthDateSignUp;
    @FXML
    PasswordField passwordSignUp;
    @FXML
    PasswordField passwordConfirmationSignUp;
    @FXML
    ImageView imageSignUp;
    @FXML
    RadioButton femaleRadio;
    @FXML
    RadioButton maleRadio;

    ToggleGroup genderGroup = new ToggleGroup();
    String selectedGender= Gender.male.name();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        birthDateSignUp.setValue(LocalDate.of(2010, 1, 1));
        birthDateSignUp.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.of(2010, 1, 1))) {
                    setDisable(true);
                }
            }
        });
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        maleRadio.setSelected(true);
        genderGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) newToggle;
                 selectedGender = selectedRadioButton.getText();

                // You can now save the selected gender wherever you need it
                System.out.println("Selected gender: " + selectedGender);
            }
        });


    }


    private final UserAuthService userAuthService;

   public USerSignUpController( ){
            this.userAuthService= new UserAuthService();
        }


    @FXML
    protected void signup(ActionEvent actionEvent) throws RemoteException {
        userAuthService.signup(actionEvent, phoneSignUp, NameSignUp, EmailLogin, CountrySignUp,
                birthDateSignUp, passwordSignUp,
                passwordConfirmationSignUp, imageSignUp,
                selectedGender,phoneValidLabel,nameValidLabel,emailValidLabel,passwordValidLabel,confirmPassValidLabel);

    }


    public void onBtnBackClicked(MouseEvent event) {
    }



}
