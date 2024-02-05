package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HiddenSidesPane;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MessagePage implements Initializable {
    @FXML
    private BorderPane contactListview;

    @FXML
    private HiddenSidesPane hiddenSidesPane;

    @FXML
    private ListView<ChatDTO> listView;

    @FXML
    private ImageView profileImage;

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

    Boolean botBoolean = false;
    CallBackClientImp callBackClient;


    URL location;
    ChatRMI chatRMI;

    @FXML
    ImageView boxImage;

    @FXML
    Label boxLabelName;

    GroupChatRMI groupChatRMIController;

    CallBackClientImp callBackClientImp;

    public MessagePage() {

        try {
            this.callBackClient = new CallBackClientImp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
        groupChatRMIController = (GroupChatRMI) StubContext.getStub("GroupChatControllerStub");


    }

//    public void setCallBackClient(CallBackClient callBackClient) {
//        this.callBackClient = callBackClient;
//    }


    public void showProfile(MouseEvent mouseEvent) {
        if (hiddenSidesPane.getPinnedSide() == null) {
            hiddenSidesPane.setPinnedSide(Side.RIGHT);

        } else {
            hiddenSidesPane.setPinnedSide(null);
        }
    }


    public void startBot(ActionEvent event) {
        botBoolean = true;
    }

    public void sendEmoji(ActionEvent event) {
    }

    public void sendAttachment(ActionEvent event) {
    }

    //    UserDTO selectedUser ;
    void setCallBackClient(CallBackClientImp callBackClient) {
        this.callBackClient = callBackClient;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boxImage.setImage(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture())));
        boxLabelName.setText(UserToken.getInstance().getUser().getDisplayName());

        PaneLoaderFactory.getInstance().setMessagePage(this);
        System.out.println("this is message page in initialize" + this);
        location = url;
        updateList();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Parent borderPane = null;
            if (newValue != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/message22.fxml"));
                    borderPane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Message22Controller message22Controller = PaneLoaderFactory.getInstance().getMessage22Controller();
            if (newValue.getAdminID() != null) {
                message22Controller.setDataSource(newValue.getChatName(), newValue.getChatImage(), newValue.getChatID());

            } else {
                message22Controller.setDataSource(newValue.getReceiverName(), newValue.getReceiverImage(), newValue.getChatID());
            }
            callBackClient.setMessage22Controller(PaneLoaderFactory.getInstance().getMessage22Controller());
            //contactListview.setCenter(PaneLoaderFactory.getmessage22Pane().getKey());

//            FXMLLoader loader = new FXMLLoader();
//            BorderPane borderPane = null;
//            try {
//                loader.setLocation(message22Controller.getFxmlUrl());
//                borderPane = loader.load();
//                // Now you can use 'root' as the root of your scene
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            contactListview.setCenter(borderPane);


            // Add your action here
        });


//        if(!ChatListManager.getInstance().getContacts().isEmpty())
//             selectedUser = ChatListManager.getInstance().getContacts().get(0);
//        if(listView.getSelectionModel().getSelectedItem()!=null){
//            selectedUser = listView.getSelectionModel().getSelectedItem();
//        }


//        if(selectedUser !=null){
//            System.out.println("iam the selected user" + selectedUser.getDisplayName());
//            profileImagw.setImage(new Image(new ByteArrayInputStream(selectedUser.getPicture())));
//            reciver.setText(selectedUser.getDisplayName());
//        }

        listView.setCellFactory(param -> new ListCell<ChatDTO>() {
            @Override
            protected void updateItem(ChatDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ContactNode.fxml"));
                        VBox notificationItem = loader.load();
                        ContactController controller = loader.getController();
                        try {
                            System.out.println("admin in message page" + item.getAdminID());
                            if (item.getAdminID() == null) {
                                Task<Void> task = new Task<>() {
                                    @Override
                                    protected Void call() throws Exception {
                                        String receiverPhoneNumber = chatRMI.getReceiverPhoneNumber(UserToken.getInstance().getUser().getPhoneNumber(), item.getChatID());
                                        UserAuthentication userAuthentication = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
                                        UserDTO userDTO = userAuthentication.getUser(receiverPhoneNumber);

                                        Platform.runLater(() -> {
                                            controller.setUserName(item.getReceiverName());
                                            controller.setUserNumber(userDTO.getPhoneNumber());
                                            controller.setUserImg(userDTO.getPicture());
                                            controller.setStatus(userDTO.getUserMode(), userDTO.getUserStatus());
                                        });
                                        return null;
                                    }
                                };

                                new Thread(task).start();

                            } else {

                                    controller.setUserName(item.getChatName());
                                    controller.setUserImg(item.getChatImage());
                                    controller.setUserNumber("");
                                    controller.setStatus(null, null);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


//                        System.out.println("name from cell" + selectedUser.getDisplayName());
                        // controller.setMessageController(MessagePage.this); // Pass the reference
//                     //   Image image = new Image("/images/profile.jpg");
//                        Image image = new Image(new ByteArrayInputStream(selectedUser.getPicture()));
                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateList() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        listView.setPlaceholder(progressIndicator); // Show the progress indicator while data is loading

        Task<ObservableList<ChatDTO>> task = new Task<>() {
            @Override
            protected ObservableList<ChatDTO> call() throws Exception {
                List<ChatDTO> chatDTOList = chatRMI.getAllChatsForUser(UserToken.getInstance().getUser().getPhoneNumber());
                List<ChatDTO> groupChatDTOS = groupChatRMIController.getAllGroupChatsForUser(UserToken.getInstance().getUser().getPhoneNumber());
                List<ChatDTO> combinedList = new ArrayList<>(chatDTOList);
                combinedList.addAll(groupChatDTOS);
                return FXCollections.observableArrayList(combinedList);
            }
        };

        task.setOnSucceeded(event -> {
            listView.setItems(task.getValue());
            listView.refresh();
            listView.setPlaceholder(null); // Hide the progress indicator when data has loaded
        });

        task.setOnFailed(event -> {
            // Handle any exceptions here
            Throwable exception = task.getException();
            exception.printStackTrace();
            listView.setPlaceholder(null); // Hide the progress indicator if there was an error
        });

        new Thread(task).start();
    }


}
