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

    protected MessageController() throws RemoteException {
        messageService = new MessageService();
    }

    @Override
    public List<MessageDTO> getMessagesByChatId(int chatId) throws RemoteException {
        return messageService.getMessagesByChatId(chatId);
    }

}
