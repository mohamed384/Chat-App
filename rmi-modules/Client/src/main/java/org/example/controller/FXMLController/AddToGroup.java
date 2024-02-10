package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
import org.example.interfaces.*;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddToGroup implements Initializable {

    @FXML
    private Button addToGroupBtn;

    @FXML
    private CheckListView<UserDTO> contactList;

    @FXML
    private ImageView groupImage;

    @FXML
    private Label groupName;

    @FXML
    private Circle imageProfileClip;
    int groupId;

    UserContact userContact;


    ChatRMI chatRMIController;
    private GroupSliderHiden groupSliderHiden;


    List<UserDTO> users;
    ObservableList<UserDTO> usersObservableLis;

    String name;

    Image groupPhoto;



    BorderPane borderPane;
    CallBackClientImp callBackClient;



    public AddToGroup() {

        this.chatRMIController = (ChatRMI) StubContext.getStub("ChatControllerStub");
        this.userContact = (UserContact) StubContext.getStub("UserContactStub");
    }


    public List<UserDTO> getContactInGroup(List<String> list) {

        UserAuthentication userAuthentication = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
        List<UserDTO> userContactList ;
        try {
            userContactList = userContact.getAllContactsByUserPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

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
        System.out.println("Users are"+users);

        List<String> phoneNumbersList1 = users.stream()
                .map(UserDTO::getPhoneNumber)
                .collect(Collectors.toList());

        List<UserDTO> filteredContacts = userContactList.stream()
                .filter(user -> !phoneNumbersList1.contains(user.getPhoneNumber()))
                .collect(Collectors.toList());
        System.out.println("User Contacts are "+userContactList);
        System.out.println("filteredContacts are "+filteredContacts);

        return filteredContacts;
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
        this.groupSliderHiden = groupSliderHiden;
        updateUI();

    }

    private void updateUI() {
        groupName.setText(name);
        groupImage.setImage(groupPhoto);
    }




    public void Add(ActionEvent event) {

        List<UserDTO> selected = contactList.getCheckModel().getCheckedItems();
        try {
            if(selected.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("choose a member to add");
                alert.setTitle("No contacts selected");
                alert.showAndWait();
                return;
            }

                for (UserDTO userSelected : selected) {
                    chatRMIController.addNewUserToGroup(groupId, userSelected.getPhoneNumber());
                    usersObservableLis.add(userSelected);

                }

                List<String> phoneNumbers = usersObservableLis.stream()
                        .map(UserDTO::getPhoneNumber)
                        .collect(Collectors.toList());
                phoneNumbers.add(UserToken.getInstance().getUser().getPhoneNumber());
                groupSliderHiden.UpList(phoneNumbers);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Added");
                alert.setTitle("Added Successfully");
                alert.showAndWait();
                Stage stage = (Stage) addToGroupBtn.getScene().getWindow();
                stage.close();
                notifySound();
                refresh();


        } catch (RemoteException e) {
            e.printStackTrace();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupName.setText(name);
        groupImage.setImage(groupPhoto);


    }
}
