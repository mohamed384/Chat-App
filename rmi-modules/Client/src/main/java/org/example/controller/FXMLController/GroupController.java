package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.controlsfx.control.CheckListView;
import org.example.Utils.LoadImage;

public class GroupController {
    @FXML
    private CheckListView<?> contactList;

   @FXML
   ImageView groupImage;

   @FXML
    Circle imageProfileClip;

    @FXML
    void createGroup(ActionEvent event) {



    }

   @FXML
    void upLoadImage(MouseEvent event) {

     groupImage.setImage(LoadImage.loadImage());

    }


}
