package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.ChatRMI;
import org.example.models.Enums.UserStatus;
import org.example.service.ContactService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContactMainController  implements Initializable {

    public BorderPane searchList;
    public Button deleteBtn;
    @FXML
    private ImageView contactImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bioLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;

    @FXML
    public GridPane UserProfile;

    @FXML
    public ImageView cypherImg;
    ChatRMI chatRMI;
    UserDTO selectedItem;
    MessagePage messagePage;


    public ContactMainController() {
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
    }

    @FXML
    public TreeView<UserDTO> contactsTreeView;

    private ContactService contactService ;

    private ObservableList<UserDTO> contacts;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserProfile.setVisible(false);
        contactService= new ContactService();
        contacts = FXCollections.observableArrayList();
        //System.out.println("annaaa contact main controller" + this);
        PaneLoaderFactory.getInstance().setContactMainController(this);
        updateContactList();
        handleSelection();


    }


    public void handleSelection(){
        contactsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() != null && !newValue.getValue().getDisplayName().equals("Contacts") && !newValue.getValue().getDisplayName().equals("Online") && !newValue.getValue().getDisplayName().equals("Offline")) {
                selectedItem = newValue.getValue();
                if (selectedItem != null) {
                    UserProfile.setVisible(true);
                    cypherImg.setVisible(false);
                    UserProfile.toFront();

                    contactImage.setImage(new Image(new ByteArrayInputStream(selectedItem.getPicture())));
                    nameLabel.setText(selectedItem.getDisplayName());
                    bioLabel.setText(selectedItem.getBio());
                    phoneLabel.setText(selectedItem.getPhoneNumber());
                    emailLabel.setText(selectedItem.getEmailAddress());
                }
            }
        });

    }


    public void updateContactList(){
        contacts.clear();
        getAllContacts();
        TreeItem<UserDTO> root = contactsTreeView.getRoot();
        TreeItem<UserDTO> onlineContactsTreeItem;
        TreeItem<UserDTO> offlineContactsTreeItem;
        if (root == null) {
            root = new TreeItem<>(new UserDTO("", "Contacts", "", "", "", "", "", null, "", null, null, null));
            contactsTreeView.setRoot(root);
            onlineContactsTreeItem = new TreeItem<>(new UserDTO("", "Online", "", "", "", "", "", null, "", null, null, null));
            offlineContactsTreeItem = new TreeItem<>(new UserDTO("", "Offline", "", "", "", "", "", null, "", null, null, null));
            contactsTreeView.getRoot().getChildren().addAll(onlineContactsTreeItem, offlineContactsTreeItem);
        }
        else{
            onlineContactsTreeItem  = root.getChildren().get(0);
            offlineContactsTreeItem = root.getChildren().get(1);
        }
        onlineContactsTreeItem.setExpanded(true);
        offlineContactsTreeItem.setExpanded(true);

        onlineContactsTreeItem.getChildren().clear();
        offlineContactsTreeItem.getChildren().clear();
        for (UserDTO userDTO : contacts) {
            if (userDTO.getUserStatus() == UserStatus.Online) {
                onlineContactsTreeItem.getChildren().add(new TreeItem<>(userDTO));
            } else {
                offlineContactsTreeItem.getChildren().add(new TreeItem<>(userDTO));
            }
        }
        updateTreeView();
    }
    public void updateTreeView(){
        contactsTreeView.setCellFactory(param -> new TreeCell<UserDTO>() {
            @Override
            protected void updateItem(UserDTO item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(null);
                    setText(null);
                    if (getTreeItem().getValue().getDisplayName().equals("Contacts") || getTreeItem().getValue().getDisplayName().equals("Online") || getTreeItem().getValue().getDisplayName().equals( "Offline") ){
                        setText(getTreeItem().getValue().getDisplayName());
                    }else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ContactNode.fxml"));
                            VBox contactCell = loader.load();

                            ContactController controller = loader.getController();
                            controller.setUserName(item.getDisplayName());
                            controller.setUserImg(item.getPicture());
                            controller.setUserNumber(item.getPhoneNumber());
                            controller.setStatus(item.getUserMode(), item.getUserStatus());
                            // Set the loaded HBox as the graphic
                            setGraphic(contactCell);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        contactsTreeView.refresh();

    }

    public void getAllContacts(){
        contacts.setAll(contactService.getAllContacts());
    }





    @FXML
    public void onAddContactClick(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddFriend.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // AddFriend controller = loader.getController();
        //controller.setCallBackServer(callBackServer);

        Scene secondScene = new Scene(root, 550, 300);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setTitle("Add Friend");
        newWindow.setScene(secondScene);
        newWindow.show();

    }


    public void createGroup(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GroupPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GroupController groupController= loader.getController();
        groupController.setContactList(contacts);
        Scene secondScene = new Scene(root, 388, 495);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setResizable(false);
        newWindow.setTitle("Create Group");
        newWindow.setScene(secondScene);
        newWindow.show();
    }




    public void messageBtn(MouseEvent event) {
        try {
            ChatDTO existChat = chatRMI.getPrivateChat(UserToken.getInstance().getUser().getPhoneNumber()
                    , phoneLabel.getText()) ;

            if(existChat == null){
                System.out.println("walahy mal2it chat");
                chatRMI.createChat(nameLabel.getText(),selectedItem.getPicture(),
                        UserToken.getInstance().getUser().getPhoneNumber(),phoneLabel.getText());
            }

            FXMLLoader loader ;
            BorderPane borderPane = null;



            borderPane = PaneLoaderFactory.messagePageLoader().getKey();

//            ChatListManager.getInstance().addContact(selectedItem);
            BorderPane mainBorder = (BorderPane)  StageUtils.getMainStage().getScene().getRoot();
            mainBorder.setCenter(borderPane);
            messagePage = PaneLoaderFactory.getInstance().getMessagePage();
            messagePage.setSelectedContact(selectedItem.getDisplayName());



        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


    public void onDeleteClick(MouseEvent mouseEvent) {
        if (showConfirmationDialog()) {
            deleteContact();
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Contact");
        alert.setContentText("Are you sure you want to delete this contact?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void deleteContact() {
        contactService.deleteContact(selectedItem);
        try {
            boolean isDeleted = chatRMI.deleteChat(UserToken.getInstance().getUser().getPhoneNumber(), selectedItem.getPhoneNumber());
            updateContactList();
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
        UserProfile.setVisible(false);
        cypherImg.setVisible(true);
    }

    private void handleRemoteException(RemoteException e) {
        System.err.println("Failed to delete chat: " + e.getMessage());
    }
}
