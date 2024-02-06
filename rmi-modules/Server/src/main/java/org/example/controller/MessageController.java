package org.example.controller;

import org.example.DTOs.MessageDTO;
import org.example.interfaces.MessageRMI;
import org.example.services.MessageService;
import org.example.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MessageController extends UnicastRemoteObject implements MessageRMI {
    private final MessageService messageService;

    public MessageController() throws RemoteException {
        messageService = new MessageService();
    }

    @Override
    public List<MessageDTO> getMessagesByChatId(int chatId) throws RemoteException {
        return messageService.getMessagesByChatId(chatId);
    }


    @Override
    public MessageDTO retrieveFileFromDB(int messageID) throws RemoteException {
        return messageService.retrieveFileFromDB(messageID);
    }

}
