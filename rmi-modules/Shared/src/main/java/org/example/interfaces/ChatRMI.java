package org.example.interfaces;

import org.example.DTOs.ChatDTO;
import org.example.models.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatRMI extends Remote {
    public boolean createChat(String name, byte[] img, int adminId, String sender, String receiver) throws RemoteException;

    public ChatDTO getPrivateChat(String sender, String receiver) throws RemoteException;

    public List<ChatDTO> getAllChatsForUser(String phoneNumber) throws RemoteException;
}


