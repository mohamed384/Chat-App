package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.example.models.Enums.Gender;
import org.example.service.UserAuthService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
    String selectedCountry;

    ToggleGroup genderGroup = new ToggleGroup();
    String selectedGender = Gender.male.name();
    @FXML
    ComboBox<String> countryComboBox;
    private final List<String> countriesList = Arrays.asList(
            "Egypt", "South Africa", "Arab Saudi", "Algeria", "Morocco"
    );

    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            imageSignUp.setImage(image);

        }
    }

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
            }

        });
        ObservableList<String> countriesObservableList = FXCollections.observableArrayList(countriesList);
        countryComboBox.setItems(countriesObservableList);
        countryComboBox.setOnAction(event -> {
            selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();

        });

    }


    private final UserAuthService userAuthService;

    public USerSignUpController() {
        this.userAuthService = new UserAuthService();
    }


    @FXML
    protected void signup(ActionEvent actionEvent) throws RemoteException {
        userAuthService.signup(actionEvent, phoneSignUp, NameSignUp, EmailLogin, selectedCountry,
                birthDateSignUp, passwordSignUp,
                passwordConfirmationSignUp, imageSignUp,
                selectedGender, phoneValidLabel, nameValidLabel,
                emailValidLabel, passwordValidLabel, confirmPassValidLabel, counrtyValidLabel);

    }


    public void onBtnAlreadyRegisterClicked(MouseEvent event) throws IOException {
        AuthContainerController authContainerController = AuthContainerController.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Pane signupPane = loader.load();
        authContainerController.switchToPane(signupPane);
    }


}
