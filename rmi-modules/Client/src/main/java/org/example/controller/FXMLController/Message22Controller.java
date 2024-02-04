package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.ChatRMI;

import javax.sound.midi.Receiver;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class Message22Controller implements Initializable {
    @FXML
    private ImageView profileImage;
    @FXML
    private Label receiver;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxMessage;
    @FXML
    private Button editTxt;
    @FXML
    private Button sendEmoji;
    @FXML
    private TextArea textArea;

    CallBackServer callBackServer;
    String receiverPhoneNumber;
    ChatRMI chatRMI;

    URL url;

    int chatID;

    public  void setDataSource(String receiverName, byte[] receiverImage,int ChatID){
        this.chatID = ChatID;
     Platform.runLater(() ->{

        receiver.setText(receiverName);
        profileImage.setImage(new Image(new ByteArrayInputStream(receiverImage)));
        });

        try {
            receiverPhoneNumber  = chatRMI.getReceiverPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber(),chatID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("this is message page in set data source" + this);
        System.out.println(receiverName + " message22" );
    }



    public Message22Controller(){

        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

    }
    public URL getFxmlUrl() {
        return url;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneLoaderFactory.getInstance().setMessage22Controller(this);
        this.url = url;

    }
    public void setCallBackServer(CallBackServer callBackServer) {
        this.callBackServer = callBackServer;
    }

    public void showProfile(MouseEvent mouseEvent) {
    }

    public void startBot(ActionEvent event) {
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

                    System.out.println("callBackServer from message page" + callBackServer);
                    try {
                        receiverPhoneNumber  = chatRMI.getReceiverPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber(),chatID);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    callBackServer.sendMsg(message, UserToken.getInstance().getUser().getPhoneNumber(), receiverPhoneNumber);
                } catch (RemoteException | MalformedURLException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    public  boolean receiveMessage(String Result , int chatID){
        System.out.println("this is message page in receive message" + this);

        if (chatID != this.chatID){
            return false;
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
        System.out.println("this is vbox message in receive message Messsage page" + vboxMessage);
        Platform.runLater(() -> {
            vboxMessage.getChildren().add(hBox);
            scrollPane.setVvalue(1.0);
        });

        return true;
    }

}
