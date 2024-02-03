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
import javafx.scene.layout.VBox;
import org.controlsfx.control.HiddenSidesPane;
import org.example.DTOs.ChatDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.ChatRMI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class MessagePage implements Initializable {
    @FXML
    private BorderPane contactListview;

    @FXML
    private HiddenSidesPane hiddenSidesPane;

    @FXML
    private ListView<ChatDTO> listView;

    @FXML
    private ImageView profileImage;

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

    URL location;
    ChatRMI chatRMI;
    public MessagePage(){
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

    }
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
    public URL getFxmlUrl() {
        return location;
    }



    public void startBot(ActionEvent event) {
        botBoolean = true;
    }

    public void sendEmoji(ActionEvent event) {
    }

    public void sendAttachment(ActionEvent event) {
    }
//    UserDTO selectedUser ;

    public void sendMessage(ActionEvent event) {

        Label text = new Label();
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMaxHeight(Double.MAX_VALUE);
        text.setText(textArea.getText());
        String message = textArea.getText();
        text.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");
        textArea.setText("");


        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        HBox hBox = new HBox(10); // Add spacing between the image and the text
        hBox.getChildren().addAll(imageView, text);

        //VBox messageBubble = new VBox();
      //  messageBubble.setMaxHeight(Double.MAX_VALUE); // Set maximum height to a very large value
       // messageBubble.getChildren().add(hBox);
        vboxMessage.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5px; -fx-padding: 10px;");

        vboxMessage.getChildren().add(hBox);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));

        Platform.runLater(() -> {
            try {
                callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");

//                System.out.println("callBackServer from message page" + callBackServer);
//                callBackServer.sendMsg(message, UserToken.getInstance().getUser().getPhoneNumber(), selectedUser.getPhoneNumber());
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public  void receiveMessage(String Result){
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


       // VBox messageBubble = new VBox();
      //  vboxMessage.getChildren().add(hBox);
        vboxMessage.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5px; -fx-padding: 10px;");

        vboxMessage.getChildren().add(hBox);

        Platform.runLater(() -> {
            scrollPane.setVvalue(1.0);
        });

//        if(botBoolean){
//            try {
//               callBackServer.chatBot( Result, "01005036123" ,"01095192555");
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//        }

        vboxMessage.getChildren().add(hBox);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneLoaderFactory.getInstance().setMessagePage(this);
        location = url;

        try {
            listView.refresh();
            List<ChatDTO>  chatDTOList = chatRMI.getAllChatsForUser(UserToken.getInstance().getUser().getPhoneNumber());
            ObservableList<ChatDTO> chats = FXCollections.observableArrayList(chatDTOList);
            System.out.println("chats size" + chats.size());
            listView.setItems(chats);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            profileImage.setImage(new Image(new ByteArrayInputStream(newValue.getReceiverImage())));
            reciver.setText(newValue.getReceiverName());
            // Add your action here
        });
//        if(!ChatListManager.getInstance().getContacts().isEmpty())
//             selectedUser = ChatListManager.getInstance().getContacts().get(0);
//        if(listView.getSelectionModel().getSelectedItem()!=null){
//            selectedUser = listView.getSelectionModel().getSelectedItem();
//        }


//        if(selectedUser !=null){
//            System.out.println("iam the selected user" + selectedUser.getDisplayName());
//            profileImagw.setImage(new Image(new ByteArrayInputStream(selectedUser.getPicture())));
//            reciver.setText(selectedUser.getDisplayName());
//        }

        listView.setCellFactory(param -> new ListCell<ChatDTO>() {
            @Override
            protected void updateItem(ChatDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MessageNode.fxml"));
                        HBox notificationItem = loader.load();
                        MessageNode controller = loader.getController();
                        controller.setUserName(item.getReceiverName());
                        Image image = new Image(new ByteArrayInputStream(item.getReceiverImage()));
                        controller.setUserImg(image);

//                        System.out.println("name from cell" + selectedUser.getDisplayName());
                        controller.setMessageController(MessagePage.this); // Pass the reference
//                     //   Image image = new Image("/images/profile.jpg");
//                        Image image = new Image(new ByteArrayInputStream(selectedUser.getPicture()));
                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
