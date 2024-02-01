package org.example.interfaces;

import org.example.DTOs.ChatDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRMI extends Remote {
    public boolean createChat(String name, byte[] img, int adminId,String sender, String receiver) throws RemoteException;
    public ChatDTO getPrivateChat(String sender, String receiver) throws RemoteException;
}
