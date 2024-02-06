package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.ChatRMI;
import org.example.Utils.BotClass;
import org.example.interfaces.UserAuthentication;

import javax.sound.midi.Receiver;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class Message22Controller implements Initializable {
    @FXML
    public Button bot;

    @FXML
    ImageView profileImage;


    @FXML
    private Label receiver;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxMessage;
    @FXML
    private Button editTxt;
    @FXML
    private Button sendEmoji;
    @FXML
    private TextField textField;

    CallBackServer callBackServer;
    String receiverPhoneNumber;
    ChatRMI chatRMI;

    URL url;

    int chatID;
    public Path uploadedFilePath;

    ProgressBar progressBar;

    List<String> receiverPhoneNumbers;

    public  void setDataSource(String receiverName, byte[] receiverImage,int ChatID){
        this.chatID = ChatID;
        Platform.runLater(() ->{
            receiver.setText(receiverName);
            profileImage.setImage(new Image(new ByteArrayInputStream(receiverImage)));
        });

        try {
            receiverPhoneNumbers = chatRMI.getChatParticipants(UserToken.getInstance().getUser().getPhoneNumber(),chatID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("this is message page in set data source" + this);
        System.out.println(receiverName + " message22" );
    }



    public Message22Controller(){

        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

    }
    public URL getFxmlUrl() {
        return url;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneLoaderFactory.getInstance().setMessage22Controller(this);
        this.url = url;

    }
    public void setCallBackServer(CallBackServer callBackServer) {
        this.callBackServer = callBackServer;
    }

    public void showProfile(MouseEvent mouseEvent) {
    }

    private boolean chatBotBtn = false;
    private static final String BOT_ACTIVE_COLOR = "#7b55a0";
    private static final String BOT_INACTIVE_COLOR = "#9b75d0";

    public void startBot(ActionEvent event) {
        chatBotBtn = !chatBotBtn;
        if (chatBotBtn) {
            updateBotState(BOT_ACTIVE_COLOR, "Bot is started");
        } else {
            updateBotState(BOT_INACTIVE_COLOR, "Bot is stopped");
        }
    }

    private void updateBotState(String color, String notificationMessage) {
        bot.setStyle("-fx-background-color: " + color + ";");
        showNotification("Bot", notificationMessage);
    }
    public void receiveAttachment(ActionEvent event) {
    }


    public void sendAttachment(ActionEvent event) {
        File file = selectFile();
        if (file != null) {
            FileController fileController = prepareFileController(file);
            progressBar = fileController.getProgressBar();
            fileController.setProgressBar(progressBar);
            Parent filePane = loadFilePane(fileController);
            bubbleMessageSender(filePane);
            Task<Void> task = createFileUploadTask(fileController, file);
            startFileUploadTask(task);
        } else {
            showNoFileSelectedAlert();
        }
    }

    private File selectFile() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(null);
    }

    private FileController prepareFileController(File file) {
        String fileNameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf('.'));
        double fileSizeInMB = file.length() / (1024.0 * 1024.0);

        return new FileController(fileNameWithoutExtension, String.format("%.2f", fileSizeInMB), getFileExtension(file));
    }

    private Parent loadFilePane(FileController fileController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/File.fxml"));
            loader.setController(fileController);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Task<Void> createFileUploadTask(FileController fileController, File file) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                long fileSize = file.length();
                long bytesRead = 0;

                Path destinationDirectory = Paths.get("destinationPath");
                if (!Files.exists(destinationDirectory)) {
                    try {
                        Files.createDirectories(destinationDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> {
                            showAlert(Alert.AlertType.ERROR, "Directory Creation Failed", "An error occurred while creating the destination directory.");
                        });
                        return null;
                    }
                }

                try (InputStream in = new FileInputStream(file);
                     OutputStream out = new FileOutputStream(destinationDirectory.resolve(file.getName()).toFile())) {

                    byte[] buffer = new byte[8192];
                    byte[] bufferall = new byte[(int)file.length()];
                    double progress = (double) bufferall.length/buffer.length;
                    int bytesReadThisTime;

                    while ((bytesReadThisTime = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesReadThisTime);
                        bytesRead += bytesReadThisTime;

                        //  double progress = (double) bytesRead / fileSize;
                        updateProgress(progress, progress);

                        // Update progress in the FileController
                        Platform.runLater(() -> fileController.setProgress(progress));

                        // Update color based on progress
//                        Platform.runLater(() -> {
//                            if (progress < 0.3) {
//                                progressBar.setStyle("-fx-bar-fill: red;");
//                            } else if (progress < 0.6) {
//                                progressBar.setStyle("-fx-bar-fill: orange;");
//                            } else {
//                                progressBar.setStyle("-fx-bar-fill: green;");
//                            }
//                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        showAlert(Alert.AlertType.ERROR, "File Upload Failed", "An error occurred during file upload.");
                    });
                }

                uploadedFilePath = destinationDirectory.resolve(file.getName());
                // After the file is uploaded, read the file into a byte array and send it
                try {
                    byte[] fileData = Files.readAllBytes(file.toPath());
                    System.out.println("this is file data" + fileData);
                    System.out.println("this is file data" + fileData.length);
//                    for (int i = 0; i < fileData.length; i++) {
//                        System.out.println(fileData[i]);
//                    }
                    //  callBackServer.sendFile(fileData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Throwable ex = newValue;
                // Handle exception
            }
        });

        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            // Download completed, show a notification
            showNotification("File Upload", "Upload completed successfully.");
        });

        task.setOnFailed(event -> {
            // Download failed, show an error notification
            showNotification("File Upload Error", "An error occurred during the Upload.");
        });

        return task;
    }

    private void showNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .owner(null) // You can set a specific owner if needed
                .hideAfter(Duration.seconds(5)) // Notification duration
                .position(Pos.TOP_RIGHT)
                .showInformation(); // Use showInformation() for an information notification
    }


    private void startFileUploadTask(Task<Void> task) {
        new Thread(task).start();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showNoFileSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No File Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a file to upload.");
        alert.showAndWait();
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }


    public void bubbleMessageSender(Node file) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        HBox hBox = new HBox(10); // Add spacing between the image and the text
        hBox.getChildren().addAll(imageView, file);

        VBox messageBubble = new VBox();
        messageBubble.setMaxHeight(Double.MAX_VALUE); // Set maximum height to a very large value
        messageBubble.getChildren().add(hBox);
        messageBubble.setStyle("-fx-background-color: rgba(0, 0, 0, 0); ; -fx-background-radius: 5px; -fx-padding: 10px;");


        Platform.runLater(() -> {
            vboxMessage.getChildren().add(messageBubble);
            scrollPane.setVvalue(1.0);
        });
    }

    public  Label labelText(String text){
        Label label = new Label();
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setText(text);
        label.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");
        return label;
    }

    public  void bubleReceiverMessage(Node text , String senderPhoneNumber){

        UserAuthentication userAuthenticationRMI = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
        UserDTO user = null;
        try {
            user = userAuthenticationRMI.getUser(senderPhoneNumber);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Image imageSender = new Image(new ByteArrayInputStream(user.getPicture()));
        ImageView imageView = new ImageView();
        imageView.setImage(imageSender);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        HBox hBox = new HBox(10);
//      //  hBox.setMinSize(text.getWidth() , text.getHeight());
        hBox.setPadding(new Insets(5));
        HBox.setMargin(imageView, new Insets(0, 5, 0, 0));
        hBox.getChildren().add(text);
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);


        VBox messageBubble = new VBox();
        messageBubble.getChildren().add(hBox);
        messageBubble.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 5px; -fx-padding: 10px;");
        System.out.println("this is vbox message in receive message Messsage page" + vboxMessage);

        Platform.runLater(() -> {
            vboxMessage.getChildren().add(messageBubble);
            scrollPane.setVvalue(1.0);
        });
    }
    public void sendMessage(ActionEvent event) {

        String message = textField.getText();
        textField.setText("");

        bubbleMessageSender(labelText(message));


        Platform.runLater(() -> {
            try {
                callBackServer = (CallBackServer)StubContext.getStub("CallBackServerStub");

                System.out.println("callBackServer from message page" + callBackServer);
                callBackServer.sendMsg(message, UserToken.getInstance().getUser().getPhoneNumber(), receiverPhoneNumbers, chatID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public  boolean receiveMessage(String Result , String senderPhoneNumber , int chatID){
        System.out.println("this is message page in receive message" + this);

        if (chatID != this.chatID){
            return false;
        }

        bubleReceiverMessage(labelText(Result) , senderPhoneNumber);

        if(chatBotBtn == true){
            new Thread(() -> {
                try {
                    callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");

                    System.out.println("callBackServer from message page" + callBackServer);

                    String botResult = BotClass.getBotResult(Result);

                    Platform.runLater(() ->
                            bubbleMessageSender(labelText(botResult)));

                    callBackServer.sendMsg(botResult, UserToken.getInstance().getUser().getPhoneNumber(), receiverPhoneNumbers, chatID);
                } catch (RemoteException | MalformedURLException | NotBoundException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        return true;
    }

}
