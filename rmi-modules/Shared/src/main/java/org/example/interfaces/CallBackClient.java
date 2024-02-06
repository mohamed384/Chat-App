package org.example.interfaces;

import org.example.DTOs.MessageDTO;

import java.rmi.Remote;

public interface CallBackClient extends Remote {

    public void receiveMsg(MessageDTO messageDTO) throws Exception;
    public void serverShutdownMessage() throws Exception;
    public void notification(String msg) throws Exception;
    public void announce( String title ,  String msg) throws Exception;
    public void serveStandUp() throws Exception;
    public void updateContactList() throws Exception;


}
