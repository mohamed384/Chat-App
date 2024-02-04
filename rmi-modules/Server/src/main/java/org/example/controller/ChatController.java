package org.example.controller;

import org.example.DTOs.ChatDTO;
import org.example.interfaces.ChatRMI;
import org.example.services.ChatService;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChatController extends UnicastRemoteObject implements ChatRMI {
    private final ChatService chatService;

    public ChatController() throws RemoteException {
        this.chatService= new ChatService();
    }

    @Override
    public boolean createChat(String name, byte[] img, String sender,String receiver) throws RemoteException {
        return chatService.createChat(name, img, sender, receiver);
    }

    @Override
    public ChatDTO getPrivateChat(String sender, String receiver) throws RemoteException {
        return chatService.getPrivateChat(sender, receiver);
    }

    @Override
    public List<ChatDTO> getAllChatsForUser(String phoneNumber) throws RemoteException {
        return chatService.getAllChatsForUser(phoneNumber);
    }

    @Override
    public String getReceiverPhoneNumber(String senderPhoneNumber, int chatID) throws RemoteException {
        return chatService.getReceiverPhoneNumber(senderPhoneNumber, chatID);
    }

}
