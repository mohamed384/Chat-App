package org.example.interfaces;

import java.rmi.Remote;

public interface CallBackClient extends Remote {

    public void receiveMsg(String msg , String senderPhoneNumber) throws Exception;
    public void serverShoutdownMessage() throws Exception;
    public void notification(String msg) throws Exception;


}
