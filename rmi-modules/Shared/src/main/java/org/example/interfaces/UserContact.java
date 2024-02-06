package org.example.interfaces;

import org.example.DTOs.UserDTO;
import org.example.models.Contact;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserContact extends Remote  {
    boolean addContact(String sender, String receiver) throws RemoteException;

    boolean removeContact(String sender, String receiver) throws RemoteException;

    List<UserDTO> getAllContactsByUserPhoneNumber(String sender) throws RemoteException;
    boolean contactExists(String userPhone, String friendPhone) throws RemoteException;
    boolean acceptInvite(String senderId, String receiverId) throws RemoteException;

    boolean deleteContact(String sender, String receiver) throws RemoteException;
}
