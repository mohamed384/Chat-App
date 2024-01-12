package org.example.interfaces;

import org.example.models.Contact;

import java.rmi.Remote;

public interface UserContact extends Remote {
    boolean addContact(Contact contact);
}
