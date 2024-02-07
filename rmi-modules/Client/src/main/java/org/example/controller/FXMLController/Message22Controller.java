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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.DTOs.MessageDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.ChatRMI;
import org.example.Utils.BotClass;
import org.example.interfaces.MessageRMI;
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
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    MessageDTO messageDTO;

    public  void setDataSource(String receiverName, byte[] receiverImage,int ChatID){
        this.chatID = ChatID;
        System.out.println("chat data source" + ChatID);
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
         messageDTO = new MessageDTO();

        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
    }

    public void retriveMessages(MessageDTO messageDTO){
        if (messageDTO.isAttachment()){
            retriveAttachment(messageDTO);
        }else {
            retriveTextMessages(messageDTO);
        }
    }

    private void retriveTextMessages(MessageDTO messageDTO){
        String message = messageDTO.getMessageContent();
        String senderPhoneNumber = messageDTO.getSenderID();
        if(senderPhoneNumber.equals(UserToken.getInstance().getUser().getPhoneNumber())) {
            bubbleMessageSender(MessageContainer.labelText(message));
        } else {
            bubleReceiverMessage(messageDTO);
        }
    }

    private void retriveAttachment(MessageDTO messageDTO){
        File file = new File(messageDTO.getMessageContent());
        FileController fileController = prepareFileController(file);
        fileController.setAttachmentFromDB(messageDTO);
        Parent filePane = loadFilePane(fileController);
        if (messageDTO.getSenderID().equals( UserToken.getInstance().getUser().getPhoneNumber())) {
            bubbleMessageSender(filePane);
        } else {
            bubleReceiverMessage(messageDTO);
        }
    }



    public URL getFxmlUrl() {
        return url;
    }



    public Pane emojiPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        PaneLoaderFactory.getInstance().setMessage22Controller(this);
        this.url = url;
        try {
            this.callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }


        Platform.runLater(() -> {
            MessageRMI messages = (MessageRMI) StubContext.getStub("MessageControllerStub");
            try {
                List<MessageDTO> messageDTOS = messages.retrieveAllMessages(chatID);
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

                int delay = 0;
                for (MessageDTO messageDTO : messageDTOS) {
                    // Submit a new task to the thread pool for each message
                    executorService.schedule(() -> {
                        try {
                            retriveMessages(messageDTO);
                        } catch (Exception e) {
                            // Handle exceptions here
                            e.printStackTrace();
                        }
                    }, delay++, TimeUnit.MILLISECONDS);
                }
                executorService.shutdown();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });


    }
    public void setCallBackServer(CallBackServer callBackServer) {
        this.callBackServer = callBackServer;
    }

    public void showProfile(MouseEvent mouseEvent) {
    }

    private boolean chatBotBtn = false;
    private static final String BOT_ACTIVE_COLOR = "#6639a6";
    private static final String BOT_INACTIVE_COLOR = "#cfbceb";

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
    public boolean receiveAttachment(MessageDTO  messageDTO) {

        File file = new File(messageDTO.getMessageContent());
        FileController fileController = prepareFileController(file);
        // Pass the file path to the FileController
        fileController.setAttachmentFromDB(messageDTO);
        // Load the file pane and bubble the receiver message

        Parent filePane = loadFilePane(fileController);
//        bubleReceiverMessage(filePane , messageDTO.getSenderID());

        return true;
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

                Path destinationPath = destinationDirectory.resolve(file.getName());

                try (InputStream in = new FileInputStream(file);
                     OutputStream out = new FileOutputStream(destinationPath.toFile())) {

                    byte[] buffer = new byte[8192];
                    byte[] buffer1 =  new byte[(int)  file.length()];
                    int bytesReadThisTime;
                    double progress =  buffer.length / buffer1.length;

                    while ((bytesReadThisTime = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesReadThisTime);
                        bytesRead += bytesReadThisTime;


                        updateProgress(progress, fileSize);

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

                // After the file is uploaded, read the file from the destination directory into a byte array and send it
                try {
                    byte[] fileData = Files.readAllBytes(destinationPath);
                    System.out.println("this is file data" + fileData);
                    System.out.println("this is file data length" + fileData.length);
//                    for (int i = 0; i < fileData.length; i++) {
//                        System.out.println(fileData[i]);
//                    }

                    MessageDTO messageDTO1 = new MessageDTO();
                    messageDTO1.setMessageContent(file.getName());
                    messageDTO1.setAttachment(fileData);
                    messageDTO1.setChatID(chatID);
                    messageDTO1.setSenderID(UserToken.getInstance().getUser().getPhoneNumber());
                    messageDTO1.setIsAttachment(true);

                    callBackServer.sendMsg(messageDTO1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.err.println("Exception occurred in task:");
                newValue.printStackTrace();
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
                .owner(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .showInformation();
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


    public void bubbleMessageSender(Node node) {

        ImageView imageView = MessageContainer.getImageForReceiveMessage(UserToken.getInstance().getUser());

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(imageView, node);

        BorderPane borderPane = MessageContainer.getDateTime(new Timestamp(new Date().getTime()));

        VBox messageBubble = new VBox();
        messageBubble.setMaxHeight(Double.MAX_VALUE);
        messageBubble.getChildren().add(hBox);
        messageBubble.getChildren().add(borderPane);


        messageBubble.setStyle("-fx-background-color: rgba(0, 0, 0, 0); ; -fx-background-radius: 5px; -fx-padding: 10px;");


        Platform.runLater(() -> {
            vboxMessage.getChildren().add(messageBubble);
            scrollPane.setVvalue(1.0);
        });
    }






    public  void bubleReceiverMessage(MessageDTO messageDTO){

        UserDTO user = MessageContainer.user(messageDTO.getSenderID());


        ImageView imageView = MessageContainer.getImageForReceiveMessage(user);

        HBox hBox = MessageContainer.getHBoxForReceiveMessage(imageView , MessageContainer.labelText(messageDTO.getMessageContent()));

        BorderPane borderPane =MessageContainer.getDateTime(new Timestamp(new Date().getTime()));
        

        VBox messageBubble = new VBox();
        messageBubble.getChildren().add(hBox);
        messageBubble.getChildren().add(borderPane);


        messageBubble.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-background-radius: 5px; -fx-padding: 10px;");

        Platform.runLater(() -> {
            vboxMessage.getChildren().add(messageBubble);
            scrollPane.setVvalue(1.0);
        });
    }
    
    public void sendMessage(ActionEvent event) {

        String message = textField.getText();
        textField.setText("");

        bubbleMessageSender(MessageContainer.labelText(message));


        Platform.runLater(() -> {
            try {
                callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");

                System.out.println("callBackServer from message page" + callBackServer);

                messageDTO.setMessageContent(message);
                messageDTO.setChatID(chatID);
                messageDTO.setSenderID(UserToken.getInstance().getUser().getPhoneNumber());
                messageDTO.setIsAttachment(false);
                callBackServer.sendMsg(messageDTO);
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public  boolean receiveMessage(MessageDTO messageDTO){
        System.out.println("this is message page in receive message" + this);

        if (chatID != this.chatID){
            return false;
        }

        bubleReceiverMessage(messageDTO);

        if(chatBotBtn == true){
            new Thread(() -> {
                try {
                    callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");

                    System.out.println("callBackServer from message page" + callBackServer);

                    String botResult = BotClass.getBotResult(messageDTO.getMessageContent());

                    Platform.runLater(() ->
                            bubbleMessageSender(MessageContainer.labelText(botResult)));
                    messageDTO.setMessageContent(botResult);
                    messageDTO.setChatID(chatID);
                    messageDTO.setSenderID(UserToken.getInstance().getUser().getPhoneNumber());
                    messageDTO.setIsAttachment(false);

                    callBackServer.sendMsg(messageDTO);
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
