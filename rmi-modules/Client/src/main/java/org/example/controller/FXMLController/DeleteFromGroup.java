package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.ChatRMI;
import org.example.interfaces.UserAuthentication;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DeleteFromGroup implements Initializable {

    @FXML
    private Button DeleteFromGroup;

    @FXML
    private CheckListView<UserDTO> contactList;

    @FXML
    private ImageView groupImage;

    @FXML
    private Circle imageProfileClip;

    @FXML
    private Label groupName;

    List<UserDTO> users;
    ObservableList<UserDTO> usersObservableLis;

    String name;

    Image groupPhoto;
    int groupId;
    ChatRMI chatRMI;
    GroupSliderHiden    groupSliderHiden;

    BorderPane  borderPane;

    CallBackClientImp callBackClient;


    public DeleteFromGroup(){

        borderPane = PaneLoaderFactory.getMainBorderPane();
        callBackClient = PaneLoaderFactory.getCallBackClient();

        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
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



    public void setData(int groupId, String groupName, Image groupPhoto, List<String> users , GroupSliderHiden groupSliderHiden) {


        this.groupId = groupId;
        this.name = groupName;
        this.groupPhoto = groupPhoto;
        users.remove(UserToken.getInstance().getUser().getPhoneNumber());
        this.users = getContactInGroup(users);
        usersObservableLis= FXCollections.observableArrayList(this.users);
        if(usersObservableLis !=null)
            contactList.setItems(usersObservableLis);
        System.out.println(this.users);

        this.groupSliderHiden = groupSliderHiden;


        updateUI();



    }


    private void updateUI() {
        groupName.setText(name);
        groupImage.setImage(groupPhoto);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        contactList.getCheckModel().getCheckedItems().addListener((ListChangeListener<UserDTO>) c -> {
            if (contactList.getCheckModel().getCheckedItems().isEmpty()) {
                DeleteFromGroup.setDisable(true);
            } else {
                DeleteFromGroup.setDisable(false);
            }
        });

        if(usersObservableLis !=null)
            contactList.setItems(usersObservableLis);

        groupName.setText(name);
        groupImage.setImage(groupPhoto);
        DeleteFromGroup.setDisable(true);


    }

    public void delete(ActionEvent event) {
        Platform.runLater(()-> DeleteFromGroup.setDisable(true));
    List<UserDTO> selected = contactList.getCheckModel().getCheckedItems();
        try {
            for(UserDTO userSelected :selected){
                chatRMI.deleteGroupParticipant(groupId , userSelected.getPhoneNumber());
                usersObservableLis.remove(userSelected);

            }

            List<String> phoneNumbers = usersObservableLis.stream()
                    .map(UserDTO::getPhoneNumber)
                    .collect(Collectors.toList());
            phoneNumbers.add(UserToken.getInstance().getUser().getPhoneNumber());
            groupSliderHiden.UpList(phoneNumbers);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Delete");
            alert.setTitle("Deleted Successfully");
            alert.showAndWait();
            Stage stage = (Stage) DeleteFromGroup.getScene().getWindow();
            stage.close();
            notifySound();
            refresh();



        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

//    private void refresh(){
//
//        BorderPane borderPane = null;
//
//        borderPane = PaneLoaderFactory.messagePageLoader().getKey();
//
////            ChatListManager.getInstance().addContact(selectedItem);
//        BorderPane mainBorder = (BorderPane)  StageUtils.getMainStage().getScene().getRoot();
//        mainBorder.setCenter(borderPane);
//        MessagePage   messagePage = PaneLoaderFactory.getInstance().getMessagePage();
//        messagePage.setSelectedContact(groupName.getText());
//        messagePage.updateList();
//
//    }


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
