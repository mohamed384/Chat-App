package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class USerController {

    @FXML
    private ImageView btnBack;
    @FXML
   private Pane pnSignIn;
   private Pane pnSignUp;
   @FXML
    Button btnDontHaveAccount;
    @FXML
    protected void onBtnBackClicked() {
        pnSignIn.toFront();

    }
    @FXML
    protected void onBtnDontHaveAccountClicked() {
        pnSignIn.toBack();

    }
}
