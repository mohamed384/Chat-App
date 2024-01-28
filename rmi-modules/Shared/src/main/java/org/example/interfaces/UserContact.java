package org.example.interfaces;

import org.example.models.Contact;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserContact extends Remote {
    boolean addContact(Contact contact) throws RemoteException;
}
