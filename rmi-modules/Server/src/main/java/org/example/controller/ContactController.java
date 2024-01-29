package org.example.controller;


import org.example.DTOs.UserDTO;
import org.example.services.ContactService;
import org.example.interfaces.UserContact;
import org.example.models.Contact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ContactController extends UnicastRemoteObject implements UserContact {

    private final ContactService contactService;
    public ContactController() throws RemoteException {
        contactService = new ContactService();
    }

    @Override
    public boolean addContact(String sender, String receiver)throws RemoteException {

        Contact contact = new Contact(sender,receiver);
        return contactService.addContact(contact);
    }



    @Override
    public boolean removeContact(String sender, String receiver) throws RemoteException {

        Contact contact = new Contact(sender,receiver);
        return contactService.removeContact(contact);

    }

    @Override
    public List<UserDTO> getAllContactsByUserPhoneNumber(String sender) throws RemoteException {

        return contactService.getAllContactsByUserId(sender);
    }

    @Override
    public boolean acceptInvite(String senderId, String receiverId) throws RemoteException{
        return contactService.acceptInvite(senderId, receiverId);
    }


}
