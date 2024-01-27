package org.example.interfaces;

import org.example.models.Contact;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserContact extends Remote {
    boolean addContact(String sender, String receiver) throws RemoteException;

    boolean removeContact(String sender, String receiver) throws RemoteException;

    List<Contact> getAllContactsByUserPhoneNumber(String sender) throws RemoteException;
}
