package org.example.controller;

import org.example.services.ContactService;
import org.example.interfaces.UserContact;
import org.example.models.Contact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ContactController extends UnicastRemoteObject implements UserContact {

    private final ContactService contactService;
    public ContactController() throws RemoteException {
        contactService = new ContactService();
    }

    @Override
    public boolean addContact(Contact contact) {
        return contactService.addContact(contact);
    }
}
