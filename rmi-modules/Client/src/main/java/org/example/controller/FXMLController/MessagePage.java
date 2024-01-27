package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HiddenSidesPane;
import org.example.Utils.UserToken;

import java.io.ByteArrayInputStream;

public class MessagePage {
    @FXML
    private BorderPane contactListview;

    @FXML
    private HiddenSidesPane hiddenSidesPane;

    @FXML
    private ListView<?> listView;

    @FXML
    private ImageView profileImagw;

    @FXML
    private Label reciver;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox sliderVBox;

    @FXML
    private TextArea textArea;

    @FXML
    private VBox vboxMessage;
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

        Label text = new Label();
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMaxHeight(Double.MAX_VALUE);
        text.setText(textArea.getText());
        text.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");
        textArea.setText("");

         /*
        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.setMaxHeight(Double.MAX_VALUE);
        text.setText(textArea.getText());
        text.setEditable(false); // Make the TextArea non-editable
        text.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: black;"); // Change text color to black
        textArea.setText("");
        */

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        HBox hBox = new HBox(10); // Add spacing between the image and the text
        hBox.getChildren().addAll(imageView, text);

        VBox messageBubble = new VBox();
        messageBubble.setMaxHeight(Double.MAX_VALUE); // Set maximum height to a very large value
        messageBubble.getChildren().add(hBox);
        messageBubble.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5px; -fx-padding: 10px;");

        vboxMessage.getChildren().add(messageBubble);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));

    }


}
