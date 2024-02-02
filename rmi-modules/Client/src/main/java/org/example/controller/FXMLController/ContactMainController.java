package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.HiddenSidesPane;
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
import java.util.ResourceBundle;

public class ContactMainController  implements Initializable {

    public BorderPane searchList;
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
    ChatRMI chatRMI;
    UserDTO selectedItem;

    URL location;

    public ContactMainController() {
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

       // System.out.println(chatRMI);
    }

    @FXML
    public TreeView<UserDTO> contactsTreeView;

    private

    ContactService contactService ;

    private ObservableList<UserDTO> contacts;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactService= new ContactService();
        contacts = FXCollections.observableArrayList();
        //System.out.println("annaaa contact main controller" + this);
        PaneLoaderFactory.getInstance().setContactMainController(this);
        populateContactsTree();
        handleSelection();

    }


    public void handleSelection(){
        contactsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() != null && !newValue.getValue().getDisplayName().equals("Contacts") && !newValue.getValue().getDisplayName().equals("Online") && !newValue.getValue().getDisplayName().equals("Offline")) {
                selectedItem = newValue.getValue();
                if (selectedItem != null) {
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
        //System.out.println("Updating contact list in contact main controller");
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
        }
        else{
            onlineContactsTreeItem  = root.getChildren().get(0);
            offlineContactsTreeItem = root.getChildren().get(1);
        }

        onlineContactsTreeItem.getChildren().clear();
        offlineContactsTreeItem.getChildren().clear();
        for (UserDTO userDTO : contacts) {
            if (userDTO.getUserStatus() == UserStatus.Online) {
                onlineContactsTreeItem.getChildren().add(new TreeItem<>(userDTO));
            } else {
                offlineContactsTreeItem.getChildren().add(new TreeItem<>(userDTO));
            }
        }

       // contacts.add(new UserDTO("", "Contacts", "", "", "", "", "", null, "", UserStatus.Offline, null, null));

        contactsTreeView.refresh();


    }
    private void populateContactsTree() {
        contacts.clear();
        getAllContacts();
        TreeItem<UserDTO> root = new TreeItem<>(new UserDTO("", "Contacts", "", "", "", "", "", null, "", null, null, null));
        TreeItem<UserDTO> onlineContactsTreeItem = new TreeItem<>(new UserDTO("", "Online", "", "", "", "", "", null, "", null, null, null));
        TreeItem<UserDTO> offlineContactsTreeItem = new TreeItem<>(new UserDTO("", "Offline", "", "", "", "", "", null, "", null, null, null));
        contactsTreeView.setRoot(root);
        contactsTreeView.getRoot().getChildren().addAll(onlineContactsTreeItem, offlineContactsTreeItem);

        populateContacts(onlineContactsTreeItem,getOnlineContacts());
        populateContacts(offlineContactsTreeItem, getOfflineContacts());

        onlineContactsTreeItem.setExpanded(true);
        offlineContactsTreeItem.setExpanded(true);

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

                            ContactAddFriendController controller = loader.getController();
                            controller.setUserName(item.getDisplayName());
                            controller.setUserImg(item.getPicture());
                            controller.setUserNumber(item.getPhoneNumber());

                            // Set the loaded HBox as the graphic
                            setGraphic(contactCell);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }



    private void populateContacts(TreeItem<UserDTO> treeItem, ObservableList<UserDTO> contacts) {
        for (UserDTO contact : contacts) {
            treeItem.getChildren().add(new TreeItem<>(contact));
        }
    }

    public void getAllContacts(){
        contacts.setAll(contactService.getAllContacts());
    }

    private ObservableList<UserDTO> getOfflineContacts() {
        ObservableList<UserDTO> offlineContacts = FXCollections.observableArrayList();
        for (UserDTO contact : contacts) {
            if (contact.getUserStatus() == UserStatus.Offline) {
                offlineContacts.add(contact);
            }
        }
        return offlineContacts;
    }

    private ObservableList<UserDTO> getOnlineContacts() {
        ObservableList<UserDTO> onlineContacts = FXCollections.observableArrayList();
        for (UserDTO contact : contacts) {
            if (contact.getUserStatus() == UserStatus.Online) {
                onlineContacts.add(contact);
            }
        }
        return onlineContacts;
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

        Scene secondScene = new Scene(root, 550, 300);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setTitle("Add Friend");
        newWindow.setScene(secondScene);
        newWindow.show();



    }



    public void creatGroup(ActionEvent actionEvent) {
    }




    public void messageBtn(MouseEvent event) {
        try {
            ChatDTO existChat = chatRMI.getPrivateChat(UserToken.getInstance().getUser().getPhoneNumber()
                    , phoneLabel.getText()) ;
            if(existChat == null){
                chatRMI.createChat(nameLabel.getText(),selectedItem.getPicture(),0,
                        UserToken.getInstance().getUser().getPhoneNumber(),phoneLabel.getText());
            }

            FXMLLoader loader = new FXMLLoader();
            BorderPane borderPane = null;
            try {
                loader.setLocation(PaneLoaderFactory.getInstance().getMessagePage().getFxmlUrl());
                borderPane = loader.load();
                // Now you can use 'root' as the root of your scene
            } catch (IOException e) {
                e.printStackTrace();
            }
            PaneLoaderFactory.getInstance().getMessagePage().setData(selectedItem);
            BorderPane mainBorder = (BorderPane)  StageUtils.getMainStage().getScene().getRoot();
            mainBorder.setCenter(borderPane);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

}
