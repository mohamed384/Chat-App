package org.example.controller.implementations;

import org.example.interfaces.UserContact;
import org.example.models.Contact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ContactController extends UnicastRemoteObject implements UserContact {
    protected ContactController() throws RemoteException {
    }

    @Override
    public boolean addContact(Contact contact) {
        return false;
    }
}
