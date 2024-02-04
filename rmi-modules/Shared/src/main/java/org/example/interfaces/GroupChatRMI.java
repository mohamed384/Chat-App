package org.example.interfaces;

import org.example.DTOs.ChatDTO;
import org.example.models.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupChatRMI extends Remote {
    boolean createGroupChat(String name, byte[] img, String adminId,List<String> phones) throws RemoteException;
     List<ChatDTO> getAllGroupChatsForUser(String phoneNumber) throws RemoteException;
}
