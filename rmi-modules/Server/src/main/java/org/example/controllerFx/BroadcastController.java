package org.example.controllerFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.callBackImp.CallBackServerImp;

import java.rmi.RemoteException;

public class BroadcastController {

   @FXML
   private TextArea messageServer;

   @FXML
   private  TextField titleMessage;
   CallBackServerImp callBackServerImp ;

   public BroadcastController(){
       try {
           callBackServerImp = new CallBackServerImp();
       } catch (RemoteException e) {
           throw new RuntimeException(e);
       }
   }

    public void sendServerMsg(ActionEvent event) {
        String title = titleMessage.getText();
        String message = messageServer.getText();
        callBackServerImp.sendAnnouncement(title,message);
        titleMessage.setText("");
        messageServer.setText("");
    }

   }

