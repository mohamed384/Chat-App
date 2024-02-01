package org.example.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRMI extends Remote {
    public boolean createChat(String name, byte[] img,int adminId) throws RemoteException;
}
