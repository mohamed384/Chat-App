package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.ChatRMI;
import org.example.interfaces.UserAuthentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GroupSliderHiden implements Initializable {

    @FXML
    private Button logOutBtu;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editeBtu;


    @FXML
    private ListView<UserDTO> contactInGroup;


    @FXML
    private Label groupName;

    @FXML
    private Circle groupPhoto;


    BorderPane mainBorderPane;
    CallBackClientImp callBackClient;
    String receiver;

    Image profileImage;

    List<String> list;
    int chatId;

    ChatRMI chatRMI;

    public GroupSliderHiden(){
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

        this.mainBorderPane = PaneLoaderFactory.getInstance().getMainBorderPane();
        this.callBackClient = PaneLoaderFactory.getInstance().getCallBackClient();
    }




    public List<UserDTO> getContactInGroup(List<String> list) {
        UserAuthentication userAuthentication = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
        List<UserDTO> users = new ArrayList<>();
        for (String id : list) {
            UserDTO userDTO = null;
            try {
                userDTO = userAuthentication.getUser(id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            users.add(userDTO);
        }
        return users;
    }

    public void updateImageAndName( Image image , String name){
        this.profileImage = image;
        this.receiver = name;
        groupName.setText(receiver);
        groupPhoto.setFill(new ImagePattern(profileImage));

    }

    public void UpList(List<String> list) {
        this.list = list;
        Task<List<UserDTO>> task = new Task<>() {
            @Override
            protected List<UserDTO> call() throws Exception {
                return getContactInGroup(list);
            }
        };

        task.setOnSucceeded(e -> {
            List<UserDTO> users = task.getValue();
            ObservableList<UserDTO> items = FXCollections.observableArrayList(users);
            contactInGroup.setItems(items);
            contactInGroup.setCellFactory(this::createCell);
        });

        new Thread(task).start();
    }


    public void setDataGroup(String receiver, byte[] profileImage, List<String> list , int chatId) {
         this.receiver = receiver;
         this.profileImage = new Image(new ByteArrayInputStream(profileImage));
         list.add(UserToken.getInstance().getUser().getPhoneNumber());
         this.list = list;
         this.chatId = chatId;

    }

    private ListCell<UserDTO> createCell(ListView<UserDTO> param) {
        return new ListCell<>() {
            @Override
            protected void updateItem(UserDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ContactNode.fxml"));
                        VBox notificationItem = loader.load();
                        ContactController controller = loader.getController();

                        if (controller == null) {
                            System.out.println("Controller is null");
                            return;
                        }

                        Platform.runLater(() -> {

                            try {
                                if(item.getPhoneNumber().equals(chatRMI.getGroupAdminID(chatId))) {
                                    controller.setUserName(item.getDisplayName() +  " 👑 'Admin'");
                                }else{
                                    controller.setUserName(item.getDisplayName());
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            controller.setUserNumber(item.getPhoneNumber());
                            controller.setUserImg(item.getPicture());
                            controller.setStatus(item.getUserMode(), item.getUserStatus());
                        });

                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        System.out.println("Failed to load FXML file: " + e.getMessage());
                    }
                }
            }
        };
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        groupName.setText(receiver);
        Circle clip = new Circle(45, 45, 50);
        groupPhoto.setClip(clip);
        groupPhoto.setFill(new ImagePattern(profileImage));

        Task<List<UserDTO>> task = new Task<>() {
            @Override
            protected List<UserDTO> call() throws Exception {
                return getContactInGroup(list);
            }
        };

        task.setOnSucceeded(e -> {
            List<UserDTO> users = task.getValue();
            ObservableList<UserDTO> items = FXCollections.observableArrayList(users);
            contactInGroup.setItems(items);
            contactInGroup.setCellFactory(this::createCell);
        });

        new Thread(task).start();

        addBtn.setOnAction(e -> {loadAddToGroup();});

        editeBtu.setOnAction(e -> {loadEditGroup();});

        deleteBtn.setOnAction(e -> {deleteGroup();});

        logOutBtu.setOnAction(e -> { logout();});



    }


    private void logout(){
        try {
            chatRMI.deleteGroupParticipant(chatId,UserToken.getInstance().getUser().getPhoneNumber());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have left the group successfully");
            alert.showAndWait();
            notifySound();
            refresh();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadAddToGroup() {
        String adminId;
        try {
            adminId = chatRMI.getGroupAdminID(chatId);
            System.out.println("Admin ID from loadAddToGroup is " + adminId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (UserToken.getInstance().getUser().getPhoneNumber().equals(adminId)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddToGroup.fxml"));
            VBox root = null;
            try {
                root = (VBox) loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            AddToGroup controller = loader.getController();
            controller.setData(chatId , receiver , profileImage , list , this );


            Scene secondScene = new Scene(root, 380, 472);
            notifySound();
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.initStyle(StageStyle.UTILITY);
            newWindow.setTitle("Add To Group");
            newWindow.setScene(secondScene);
            newWindow.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You are not the admin of this group");
            alert.showAndWait();

        }

    }

    private  void loadEditGroup() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditGroup.fxml"));
        VBox root = null;
        try {
            root = (VBox) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditeGroup controller = loader.getController();
        controller.setData(chatId ,receiver , profileImage ,this );


        Scene secondScene = new Scene(root, 460, 413);
        notifySound();
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setTitle("Edit Group");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    private void deleteGroup() {


        String adminId;
            try {
            adminId = chatRMI.getGroupAdminID(chatId);
            System.out.println("Admin ID from deleteGroup is " + adminId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
            if (UserToken.getInstance().getUser().getPhoneNumber().equals(adminId)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DeleteFromGroup.fxml"));
            VBox root = null;
            try {
                root = (VBox) loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            DeleteFromGroup controller = loader.getController();
                System.out.println("group of users " + list);
            controller.setData(chatId , receiver , profileImage , list , this );

                notifySound();
            Scene secondScene = new Scene(root, 380, 472);

            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.initStyle(StageStyle.UTILITY);
            newWindow.setTitle("Delete From Group");
            newWindow.setScene(secondScene);
            newWindow.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You are not the admin of this group");
            alert.showAndWait();

    }


    }


    private void refresh(){

        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BorderPane borderPane = PaneLoaderFactory.messagePageLoader().getKey();
                MessagePage messagePage = PaneLoaderFactory.getInstance().getMessagePage();
                messagePage.setSelectedContact(groupName.getText());
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

}

