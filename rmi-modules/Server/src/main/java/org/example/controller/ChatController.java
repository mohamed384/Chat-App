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

    @Override
    public List<String> getChatParticipants(String senderPhoneNumber, int chatID) throws RemoteException {
        return chatService.getChatParticipants(senderPhoneNumber,chatID);
    }
    @Override
    public boolean deleteChat(String sender, String receiver) throws RemoteException{
        return chatService.deleteChat(sender,receiver);
    }

    @Override
    public boolean isGroupChat(int chatID) throws RemoteException {
        return chatService.isGroupChat(chatID);
    }

    @Override
    public String getGroupAdminID(int chatID) throws RemoteException {

        String id = chatService.getGroupAdminID(chatID);
        System.out.println("ChatService getGroupAdminID id: "+id);
        return id;
    }
    @Override
    public void deleteGroupParticipant(int chatId, String participantId)throws RemoteException{
        chatService.deleteGroupParticipant(chatId,participantId);
    }

    @Override
    public boolean contactExists(int chatId, String groupName) throws RemoteException {
        return chatService.contactExists(chatId,groupName);
    }
    @Override
    public boolean addNewUserToGroup(int chatId,String participantUserID) throws RemoteException {
        return chatService.addNewUserToGroup(chatId,participantUserID);
    }
    @Override
    public boolean updateChatGroup(int chatId, byte[] chatImage, String chatName) throws RemoteException{
       return chatService.updateChatGroup(chatId,chatImage,chatName);
    }
}
