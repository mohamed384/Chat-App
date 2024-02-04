package org.example.controller.FXMLController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.service.ContactService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    public GroupController(){this.contactService = new ContactService();}

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

    }

    private void populateContactList() {
        List<UserDTO> userDTOList = contactService.getAllContacts();
        observableList= FXCollections.observableArrayList(userDTOList);
        contactList.setItems(observableList);
        System.out.println("contactList "+ contactList);
        System.out.println("observableList" + observableList);
    }
}
