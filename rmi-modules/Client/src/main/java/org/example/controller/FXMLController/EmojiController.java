package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class EmojiController {


    @FXML
    private TextFlow emojiList;


    private MessageChatController messageChatController;

    private TextField textFieldController;

    public void initialize() {
        emojiList.getChildren().forEach(child -> {
            if (child instanceof Text) {
                // Add an event handler to each Text node representing an emoji
                ((Text) child).setOnMouseClicked(event -> {
                    // Retrieve the clicked emoji
                    String emoji = ((Text) child).getText();
                    // Append the emoji to the TextField in the other controller
                    if (textFieldController != null) {
                        textFieldController.appendText(emoji);
                    }
                });
            }
        });
    }

    public void setTextFieldInOtherController(TextField textField) {
        this.textFieldController = textField;
    }



}
