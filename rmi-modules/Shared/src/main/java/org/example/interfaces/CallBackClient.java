package org.example.interfaces;

import org.example.DTOs.UserDTO;

import java.rmi.Remote;
import java.util.List;

public interface CallBackClient extends Remote {

    public void receiveMsg(String msg , int chtID , String senderPhoneNumber , String reciverPhoneNumber) throws Exception;
    public void serverShoutdownMessage() throws Exception;
    public void notification(String msg) throws Exception;
    public void announce( String title ,  String msg) throws Exception;

    public void updateContactList() throws Exception;

}
