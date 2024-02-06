package org.example.controllerFx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.example.Utils.StubContext;
import org.example.callBackImp.CallBackServerImp;
import org.example.utils.NetworkManager;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ServiceController  implements Initializable {

    @FXML
    private Button onOffButton;
    @FXML
    private ImageView toggleImageView;

    private Image oldImage;
    private Image newImage;
    @FXML
    private Label serverStatus;

    CallBackServerImp callBackServerImp;

    static boolean  btnRunning= true;
    static boolean  isFirst= true;

    private final  NetworkManager networkManager;

    public ServiceController() {
        networkManager = NetworkManager.getInstance();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newImage = new Image("/images/green.png");
        oldImage = new Image("/images/red.png");
        if(isFirst) {
            networkManager.bindAll();
            isFirst=false;
        }

        if (btnRunning){
            toggleImageView.setImage(newImage);
            serverStatus.setText("Service is currently running..");
            serverStatus.setStyle("-fx-font-weight: bold;");

        }else{
            toggleImageView.setImage(oldImage);
            serverStatus.setText("Service is currently offline..");
            serverStatus.setStyle("-fx-font-weight: bold;");
        }
        try {
            callBackServerImp = new CallBackServerImp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        onOffButton.setOnAction(event -> {
            System.out.println("service controller " + StubContext.isRunning);

            btnRunning =!btnRunning;

            if (btnRunning) {
                toggleImageView.setImage(newImage);
                serverStatus.setText("Service is currently running..");
                serverStatus.setStyle("-fx-font-weight: bold;");
                networkManager.bindAll();
                callBackServerImp.serverStandUpMessage();
                System.out.println("Binding happen in service Controller");
            } else {
                toggleImageView.setImage(oldImage);
                serverStatus.setText("Service is currently offline..");
                serverStatus.setStyle("-fx-font-weight: bold;");
                callBackServerImp.logoutAll();
                networkManager.unbindAll();


            }



        });


    }

    public void onOffServer(ActionEvent event) {



    }


}
