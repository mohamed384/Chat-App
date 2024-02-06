package org.example.interfaces;

import org.example.DTOs.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessageRMI extends Remote {
     List<MessageDTO> getMessagesByChatId(int chatId) throws RemoteException;
     MessageDTO retrieveFileFromDB(int messageID) throws RemoteException;

}
