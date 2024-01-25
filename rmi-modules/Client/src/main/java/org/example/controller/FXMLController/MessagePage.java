package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HiddenSidesPane;

public class MessagePage {

    @FXML
    VBox sliderVBox;
    @FXML
    HiddenSidesPane hiddenSidesPane;

    public void showProfile(MouseEvent mouseEvent) {
        if (hiddenSidesPane.getPinnedSide() == null) {
            hiddenSidesPane.setPinnedSide(Side.RIGHT);

        } else {
            hiddenSidesPane.setPinnedSide(null);
        }
    }

    public void startBot(ActionEvent event) {
    }

    public void sendEmoji(ActionEvent event) {
    }

    public void sendAttachment(ActionEvent event) {
    }

    public void sendMessage(ActionEvent event) {
    }
}
