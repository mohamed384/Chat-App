package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.controlsfx.control.HiddenSidesPane;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;


public class MessagePage implements Initializable {
    public VBox NoChatsVbox;
    @FXML
    private BorderPane contactListview;

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

    @FXML
    private HBox minProfile;

    Boolean botBoolean = false;
    CallBackClientImp callBackClient;


    URL location;
    ChatRMI chatRMI;

    @FXML
    Circle boxImage;

    @FXML
    Label boxLabelName;

    GroupChatRMI groupChatRMIController;

    CallBackClientImp callBackClientImp;

    String selectedContact;
//    ChatDTO selectedUser;

    public MessagePage() {

        try {
            this.callBackClient = new CallBackClientImp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");
        groupChatRMIController = (GroupChatRMI) StubContext.getStub("GroupChatControllerStub");



    }
    public void setSelectedContact(String selectedContact) {
        this.selectedContact = selectedContact;
//        for(){}
//        listView.getSelectionModel().select(selectedUser);


    }
    //    public void setCallBackClient(CallBackClient callBackClient) {
//        this.callBackClient = callBackClient;
//    }


    public void showProfile(MouseEvent mouseEvent) {

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

    @FXML
    public void goToProfile(MouseEvent event) {

//        BorderPane borderPane = PaneLoaderFactory.getInstance().getMainBorderPane();
//        BorderPane pane = PaneLoaderFactory.profilePageLoader().getKey();
//        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
//        borderPane.setCenter(null);
//        BorderPane.setAlignment(pane, Pos.CENTER);
//        BorderPane.setMargin(pane, new Insets(0, 0, 0, 0));
//        borderPane.setCenter(pane);

        BorderPane profilePane = PaneLoaderFactory.profilePageLoader().getKey();
        // UserProfileController userProfileController = PaneLoaderFactory.getInstance().getUserProfileController();

        Platform.runLater(() -> {
            BorderPane mainBorder = (BorderPane) StageUtils.getMainStage().getScene().getRoot();
            mainBorder.setCenter(profilePane);
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NoChatsVbox.setVisible(false);
        listView.setPlaceholder(null);
        boxImage.setFill(new ImagePattern(new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture()))));
        boxLabelName.setText(UserToken.getInstance().getUser().getDisplayName());

        PaneLoaderFactory.getInstance().setMessagePage(this);
        System.out.println("this is message page in initialize" + this);
        location = url;

        updateList();
        handleSelection();

        minProfile.setOnMouseClicked(event -> {

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
//                            System.out.println("admin in message page" + item.getAdminID());
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
                                controller.setUserNumber("Group chat");
                                controller.setStatus(null, null);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void handleSelection(){
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Parent borderPane = null;
            System.out.println("newal"+newValue);
            if (newValue != null) {
                System.out.println("newValue" +newValue);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MessageChat.fxml"));
                    hiddenSidesPane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MessageChatController messageChatController = PaneLoaderFactory.getInstance().getMessage22Controller();

                if ( newValue.getAdminID() != null) {
                    messageChatController.setDataSource(newValue.getChatName(), newValue.getChatImage(), newValue.getChatID());

                } else {
                    System.out.println("this is message page checking admin id" + newValue.getChatID());
                    messageChatController.setDataSource(newValue.getReceiverName(), newValue.getReceiverImage(), newValue.getChatID());
                }


            }

            callBackClient.setMessage22Controller(PaneLoaderFactory.getInstance().getMessage22Controller());
            contactListview.setCenter(hiddenSidesPane);


        });

    }

    public void updateList() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        listView.setPlaceholder(progressIndicator);
        // Show the progress indicator while data is loading
        Task<ObservableList<ChatDTO>> task = new Task<>() {
            @Override
            protected ObservableList<ChatDTO> call() throws Exception {
                List<ChatDTO> chatDTOList = chatRMI.getAllChatsForUser(UserToken.getInstance().getUser().getPhoneNumber());
                List<ChatDTO> groupChatDTOS = groupChatRMIController.getAllGroupChatsForUser(UserToken.getInstance().getUser().getPhoneNumber());
                ObservableList<ChatDTO> combinedList = FXCollections.observableList(chatDTOList);
                combinedList.addAll(groupChatDTOS);
                FilteredList<ChatDTO> filteredList =combinedList.filtered(chatDTO -> chatDTO.getChatName().equals(selectedContact));
                listView.setPlaceholder(null);
                if (!filteredList.isEmpty()) {
                    Platform.runLater(() -> {
                        listView.getSelectionModel().select(filteredList.get(0));
                        handleSelection();
                    });
                    System.out.println("name " + filteredList.get(0).getReceiverName());

                }else{
                    if(!combinedList.isEmpty()){
                        Platform.runLater(() -> {
                            listView.getSelectionModel().select(combinedList.get(0));
                            handleSelection();
                        });
                    }

                }
                return FXCollections.observableArrayList(combinedList);
            }
        };


        task.setOnSucceeded(event -> {
            ObservableList<ChatDTO> result = task.getValue();
            if (result.isEmpty()) {
                NoChatsVbox.toFront();
                NoChatsVbox.setVisible(true);
                listView.setVisible(false);
                listView.setPlaceholder(null);
            } else {
                listView.setItems(result);
                listView.toFront();
                NoChatsVbox.setVisible(false);
            }
        });
        new Thread(task).start();



    }



}