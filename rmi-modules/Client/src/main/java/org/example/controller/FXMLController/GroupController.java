package org.example.controller.FXMLController;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.CheckListView;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.ChatRMI;
import org.example.interfaces.GroupChatRMI;
import org.example.interfaces.UserAuthentication;
import org.example.service.ContactService;

import java.io.IOException;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GroupController implements Initializable {
    public TextField groupName;
    @FXML
    private Button createGroupBtn;
    @FXML
    private CheckListView<UserDTO> contactList;
    ObservableList<UserDTO> observableList;

   @FXML
   ImageView groupImage;

   @FXML
    Circle imageProfileClip;
    String groupNameString;
    private final ContactService contactService;
    GroupChatRMI groupChatRMIController;

    public GroupController(){
        this.contactService = new ContactService();
        groupChatRMIController = (GroupChatRMI)StubContext.getStub("GroupChatControllerStub");
    }

    @FXML
    public void createGroup() {
        ObservableList<Integer> checkedIndices = contactList.getCheckModel().getCheckedIndices();

        UserDTO checkedUser = null;
        for (Integer index : checkedIndices) {
            checkedUser  = observableList.get(index);
        }
        if(groupName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("choose group name");
            alert.setTitle("enter Group Name");
            alert.showAndWait();
        }else if(checkedUser == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("choose group members");
            alert.setTitle("No contacts added to group");
            alert.showAndWait();
        }
        else{
            groupNameString= groupName.getText();
            System.out.println(groupName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(groupNameString +" group created");
            alert.setTitle("Created Successfully");
            alert.showAndWait();
            Stage stage = (Stage) createGroupBtn.getScene().getWindow();
            stage.close();
            //backend logic here
            List<UserDTO> dtos = observableList;
            List<String> phoneList = new ArrayList<>(dtos.stream()
                    .map(UserDTO::getPhoneNumber)
                    .toList());
            phoneList.add(UserToken.getInstance().getUser().getPhoneNumber());
            try {
                groupChatRMIController.createGroupChat(groupNameString,LoadImage.convertImageToByteArray(groupImage.getImage()),
                        UserToken.getInstance().getUser().getPhoneNumber(),phoneList);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
       
        
    }

   @FXML
    void upLoadImage(MouseEvent event) {

     groupImage.setImage(LoadImage.loadImage());

    }


    public void setContactList(ObservableList<UserDTO> contacts) {
        observableList = contacts;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateContactList();
      //  cellFactory();

    }
//    public void cellFactory(){
//        contactList.setCellFactory(new Callback<ListView<UserDTO>, ListCell<UserDTO>>() {
//            @Override
//            public ListCell<UserDTO> call(ListView<UserDTO> param) {
//                return new CustomCheckListCell();
//            }
//        });
//
//    }
//    private static class CustomCheckListCell extends ListCell<UserDTO> {
//        @Override
//        protected void updateItem(UserDTO item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty || item == null) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ContactNode.fxml"));
//                    VBox node = loader.load();
//                    ContactController controller = loader.getController();
//                    new Thread(() -> {
//                        try {
//
//                            javafx.application.Platform.runLater(() -> {
//                                controller.setUserName(item.getDisplayName());
//                                controller.setUserNumber(item.getPhoneNumber());
//                                controller.setUserImg(item.getPicture());
//                                controller.setStatus(item.getUserMode(), item.getUserStatus());
//                            });
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }).start();
//                    HBox hBox = new HBox();
//                    hBox.getChildren().add(new CheckBox());
//                    hBox.getChildren().add(node);
//                    setGraphic(hBox);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void populateContactList() {
        List<UserDTO> userDTOList = contactService.getAllContacts();
        observableList= FXCollections.observableArrayList(userDTOList);
        contactList.setItems(observableList);
        System.out.println("contactList "+ contactList);
        System.out.println("observableList" + observableList);
    }
}
