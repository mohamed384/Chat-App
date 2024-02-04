package org.example.controller;

import org.example.DTOs.ChatDTO;
import org.example.interfaces.GroupChatRMI;
import org.example.services.GroupChatService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GroupChatController extends UnicastRemoteObject implements GroupChatRMI {
    private final GroupChatService groupChatService;
    public GroupChatController() throws RemoteException {
        groupChatService = new GroupChatService();
    }

    @Override
    public boolean createGroupChat(String name, byte[] img, String adminId, List<String> phones) throws RemoteException {
      return   groupChatService.createChat(name,img,adminId,phones);
    }

    @Override
    public List<ChatDTO> getAllGroupChatsForUser(String phoneNumber) throws RemoteException {
        return groupChatService.getAllGroupChatsForUser(phoneNumber);
    }
}
