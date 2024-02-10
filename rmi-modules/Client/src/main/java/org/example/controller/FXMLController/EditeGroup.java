package org.example.controller.FXMLController;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.CallBackImp.CallBackClientImp;
import org.example.Utils.LoadImage;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.ChatRMI;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class EditeGroup implements Initializable {



    @FXML
    private Button editBtn;

    @FXML
    private TextField editeName;

    @FXML
    private ImageView groupImage;

    private String name;

    private Image image;

    int chatId;

    GroupSliderHiden groupSliderHiden;
    ChatRMI chatRmi;

//    BorderPane borderPane;
//    CallBackClientImp callBackClient;
    public EditeGroup(){
//        borderPane = PaneLoaderFactory.getMainBorderPane();
//        callBackClient = PaneLoaderFactory.getInstance().getCallBackClient();

        chatRmi = (ChatRMI) StubContext.getStub("ChatControllerStub");
    }

    public void setData(int chatId , String name ,Image image , GroupSliderHiden groupSliderHiden){
        this.chatId = chatId;
        editeName.setText(name);
        groupImage.setImage(image);
        this.name = name;
        this.image = image;
        this.groupSliderHiden = groupSliderHiden;
        editBtn.setDisable(true);

        update();
    }

    void update() {
        editeName.setText(name);
        groupImage.setImage(image);
    }


    @FXML
    void UpdateGroup(ActionEvent event) {


        try {
            chatRmi.updateChatGroup(chatId,LoadImage.convertImageToByteArray(image),editeName.getText());
            groupSliderHiden.updateImageAndName(image,editeName.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Added");
            alert.setTitle("Added Successfully");
            alert.showAndWait();
            Stage stage = (Stage) editBtn.getScene().getWindow();
            stage.close();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        refresh();
        notifySound();

    }
    public void imgClicked() {
        Image imageLoad = LoadImage.loadImage();
        if (imageLoad != null) {
            Platform.runLater(() -> {
                groupImage.setImage(imageLoad);
                editBtn.setDisable(false);
            });

        }else {
            groupImage.setImage(image);
        }


    }


    private void refresh(){

        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BorderPane borderPane = PaneLoaderFactory.messagePageLoader().getKey();
                MessagePage messagePage = PaneLoaderFactory.getInstance().getMessagePage();
                messagePage.setSelectedContact(editeName.getText());
                messagePage.updateList();

                Platform.runLater(() -> {
                    BorderPane mainBorder = (BorderPane) StageUtils.getMainStage().getScene().getRoot();
                    mainBorder.setCenter(borderPane);
                });

                return null;
            }
        };

        new Thread(loadTask).start();

    }

    public void notifySound(){
        String mediaUrl = getClass().getResource("/media/mouse_click.mp3").toExternalForm();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editeName.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue "+oldValue);
            System.out.println("newValue "+newValue);
                editBtn.setDisable(oldValue.equals(newValue));
        });
    }
}
