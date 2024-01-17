package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.DTOs.UserDTO;
import org.example.models.User;
import org.example.interfaces.UserAuthentication;
import org.example.models.UserStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class USerController {

    @FXML
    private ImageView btnBack;
    @FXML
   private Pane pnSignIn;
   @FXML
   private Pane pnSignUp;
   @FXML
   private  Label btnDontHaveAccount;
    @FXML
    private ToggleGroup toggleGroup;
    //Login fields
   @FXML
   PasswordField PasswordLog;
   @FXML
   TextField PhoneLog;

   //SignUp fields
    @FXML
    TextField phoneSignUp;
    @FXML
    TextField NameSignUp;
    @FXML
    TextField EmailignUp;
    @FXML
    TextField CountrySignUp;
    @FXML
    DatePicker birthDateSignUp;
    @FXML
    PasswordField passwordSignUp;
    @FXML
    PasswordField passwordconSignUp;
    @FXML
    SplitMenuButton genderSingnUp;
    @FXML
    ImageView imageSignUp;



    private UserAuthentication UserContror() {
        UserAuthentication remoteObject = null;
        try {
            remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/rmiObject");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    @FXML
    protected void onBtnBackClicked() {
        pnSignIn.toFront();

    }

    @FXML
    protected void onBtnDontHaveAccountClicked() {
        pnSignIn.toBack();

    }
    @FXML
    protected void  handleGenderSelected(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        String selectedGender = selectedItem.getText();
        genderSingnUp.setText(selectedGender);

    }

    @FXML
    public void initialize() {

        LocalDate maxDate = LocalDate.of(2015, 12, 31); // Replace with your max date
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };

        birthDateSignUp.setDayCellFactory(dayCellFactory);

        // when select gender

        birthDateSignUp.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(LocalDate.now())) {
                    // Disable future dates
                    setDisable(true);
                }
            }
        });


    }



    @FXML
    protected UserDTO login(ActionEvent actionEvent) throws IOException {
        String password = PasswordLog.getText();
        String phone = PhoneLog.getText();


        UserAuthentication remoteObject = UserContror();
        UserDTO user = null;
        if (remoteObject != null) {

            user = remoteObject.login(phone, password);
            if(user != null){
                switchToMessagePage(actionEvent);
            }
        } else {
            System.out.println("Cant connect to server");
        }

        PasswordLog.setText("");
        PhoneLog.setText("");
        return user;
    }

    @FXML
    protected void signup(ActionEvent actionEvent )throws RemoteException{

        String phone =  phoneSignUp.getText().toString();

        String name =  NameSignUp.getText().toString();

        String email=  EmailignUp.getText().toString();

        String country =  CountrySignUp.getText().toString();


        // Convert LocalDate to Date
        LocalDate selectedLocalDate = birthDateSignUp.getValue();

        Date birthDate = Date.from(selectedLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String birthDateString = dateFormat.format(birthDate);

        String password =  passwordSignUp.getText().toString();

        String passwordcon =  passwordconSignUp.getText().toString();

        String gender =  genderSingnUp.getText().toString();

        String image =  imageSignUp.getImage().toString();


        UserAuthentication remoteObject = UserContror();
        User user = null;
        if (remoteObject != null) {

            if (!password.equals(passwordcon)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Password not match");
                alert.setContentText("Please enter the same password");
                alert.showAndWait();
                //return;
            }


            UserDTO user1 = new UserDTO(phone, name, email, password, passwordcon, gender, country,
                    birthDate,  "",UserStatus.AVAILABLE,image);


           user1.toString();

            System.out.println(user1.toString());
            System.out.println(user1 instanceof UserDTO);


            remoteObject.signup(user1);




        } else {
            System.out.println("Cant connect to server");
        }


         phoneSignUp.setText("");
         NameSignUp.setText("");
         EmailignUp.setText("");
        CountrySignUp.setText("");
        birthDateSignUp.setUserData(new Date());
        passwordSignUp.setText("");
        passwordconSignUp.setText("");
         genderSingnUp.setText("");
        imageSignUp.setAccessibleText("");
       // return user;
    }


    protected  void switchToMessagePage(ActionEvent actionEvent) {

        Parent newScreenParent = null;
        try {
            newScreenParent = FXMLLoader.load(getClass().getResource("/views/MessagePage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene newScreenScene = new Scene(newScreenParent);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        currentStage.setScene(newScreenScene);

        currentStage.show();
    }

}
