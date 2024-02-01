package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HiddenSidesPane;
import org.example.DTOs.NotificationDto;
import org.example.DTOs.UserDTO;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserSendNotification;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MessagePage implements Initializable {
    @FXML
    private BorderPane contactListview;

    @FXML
    private HiddenSidesPane hiddenSidesPane;

    @FXML
    private ListView<UserDTO> listView;

    @FXML
    private ImageView profileImagw;

    @FXML
    private Label reciver;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox sliderVBox;

    @FXML
    private TextArea textArea;

    @FXML
    private VBox vboxMessage;

    Boolean botBoolean = false;
    CallBackClient callBackClient;

    CallBackServer callBackServer;

    ObservableList<UserDTO> observableList;

    public void setCallBackClient(CallBackClient callBackClient) {
        this.callBackClient = callBackClient;
    }

    public void setCallBackServer(CallBackServer callBackServer) {
        this.callBackServer = callBackServer;
    }

    public void showProfile(MouseEvent mouseEvent) {
        if (hiddenSidesPane.getPinnedSide() == null) {
            hiddenSidesPane.setPinnedSide(Side.RIGHT);

        } else {
            hiddenSidesPane.setPinnedSide(null);
        }
    }



    public void startBot(ActionEvent event) {
        botBoolean = true;
    }

    public void sendEmoji(ActionEvent event) {
    }

    public void sendAttachment(ActionEvent event) {
    }

    public void sendMessage(ActionEvent event) {

        Label text = new Label();
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMaxHeight(Double.MAX_VALUE);
        text.setText(textArea.getText());
        String message = textArea.getText();
        text.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");
        textArea.setText("");

         /*
        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMaxHeight(Double.MAX_VALUE);
        text.setText(textArea.getText());
        text.setEditable(false); // Make the TextArea non-editable
        text.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: black;"); // Change text color to black
        textArea.setText("");
        */

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        HBox hBox = new HBox(10); // Add spacing between the image and the text
        hBox.getChildren().addAll(imageView, text);

        VBox messageBubble = new VBox();
        messageBubble.setMaxHeight(Double.MAX_VALUE); // Set maximum height to a very large value
        messageBubble.getChildren().add(hBox);
        messageBubble.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5px; -fx-padding: 10px;");

        vboxMessage.getChildren().add(messageBubble);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));

        try {
            callBackServer.sendMsg(message, "01095192555", "01005036123");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public  void receiveMessage(String Result){
        if(Result.equals("")){
            return;
        }
        Label text = new Label();
        text.setWrapText(true); // Enable text wrapping
        text.setMaxWidth(1000);
        text.setText(Result);
        text.setStyle("-fx-background-color: #b79bc2; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");


        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        HBox hBox = new HBox(10);

        hBox.setMinSize(text.getWidth() , text.getHeight());
        hBox.setPadding(new Insets(5));
        HBox.setMargin(imageView, new Insets(0, 5, 0, 0));
        hBox.getChildren().add( text);
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);


        VBox messageBubble = new VBox();
        messageBubble.getChildren().add(hBox);
        messageBubble.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5px; -fx-padding: 10px;");


        Platform.runLater(() -> {
            vboxMessage.getChildren().add(hBox);
            scrollPane.setVvalue(1.0);
        });

//        if(botBoolean){
//            try {
//               callBackServer.chatBot( Result, "01005036123" ,"01095192555");
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//        }
        /*
        vboxMessage.getChildren().add(hBox);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
        */
    }

//    private UserSendNotification UserNotificationController() {
//        UserSendNotification remoteObject = null;
//        try {
//            remoteObject = (UserSendNotification) Naming.lookup("rmi://localhost:1099/UserSendNotificationStub");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return remoteObject;
//    }
    String phoneNumber = "1234567890";
    String displayName = "Test User";
    String email = "testuser@example.com";
    String passwordHash = "hashedpassword";
    String confirmPassword = "hashedpassword";
    String gender = "Male";
    String country = "Test Country";
    Date dateOfBirth = new Date();
    String bio = "This is a test user.";
    UserStatus status = UserStatus.Online;
    UserMode userMode = UserMode.Available;
    byte[] picture = new byte[0]; // Empty picture for the test

    UserDTO user = new UserDTO(phoneNumber, displayName, email, passwordHash, confirmPassword, gender, country, dateOfBirth, bio, status, userMode, picture);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        UserSendNotification userSendNotification = UserNotificationController();
//
//        List<NotificationDto> notificationList =  userSendNotification.receiveNotification(UserToken.getInstance().getUser().getPhoneNumber());
        List<UserDTO> userDTOList= new ArrayList<>();
        userDTOList.add(user);
        userDTOList.add(user);
        observableList = FXCollections.observableArrayList(userDTOList);
        listView.setItems(observableList);
        listView.setCellFactory(param -> new ListCell<UserDTO>() {
            @Override
            protected void updateItem(UserDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MessageNode.fxml"));
                        HBox notificationItem = loader.load();
                        MessageNode controller = loader.getController();
                        controller.setUserName(item.getDisplayName());
                        controller.setMessageController(MessagePage.this); // Pass the reference
                        Image image = new Image("/images/profile.jpg");
                        controller.setUserImg(image);
                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
