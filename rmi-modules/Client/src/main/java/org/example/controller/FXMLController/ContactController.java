package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.DTOs.UserDTO;
import org.example.interfaces.CallBackServer;
import org.example.models.Enums.UserStatus;
import org.example.service.ContactService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContactController implements Initializable {



    @FXML
    public TreeView<UserDTO> contactsTreeView;

    private final ContactService contactService;
    private ObservableList<UserDTO> contacts = FXCollections.observableArrayList();


    public ContactController() {
        this.contactService = new ContactService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateContactsTree();
    }

    private void populateContactsTree() {
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

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
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
        contacts =  FXCollections.observableArrayList(contactService.getAllContacts());
    }

    private ObservableList<UserDTO> getOfflineContacts() {
        contacts.removeIf(contact -> contact.getUserStatus() != UserStatus.Offline);
        return contacts;
    }
    private ObservableList<UserDTO> getOnlineContacts() {
        contacts.removeIf(contact -> contact.getUserStatus() != UserStatus.Online);
        return contacts;
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
}