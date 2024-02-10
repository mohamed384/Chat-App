package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class EmojiController {


    @FXML
    private TextFlow emojiList;




    private TextField textFieldController;

    public void initialize() {
        emojiList.getChildren().forEach(child -> {
            if (child instanceof Text) {

                ((Text) child).setOnMouseClicked(event -> {
                    // Retrieve the clicked emoji
                    String emoji = ((Text) child).getText();
                    if (textFieldController != null) {
                        textFieldController.appendText(emoji);
                    }
                    child.setStyle("-fx-background-color: #6f8187;");
                });
            }
        });
    }

    public void setTextFieldInOtherController(TextField textField) {
        this.textFieldController = textField;
    }



}
