package org.example.controller.services;

import org.example.controller.DAO.ContactDAO;
import org.example.controller.DAO.UserDAO;
import org.example.models.Contact;
import org.example.models.User;

public class ContactService {
    private final ContactDAO contactDAO;

    public ContactService(){
        this.contactDAO= new ContactDAO();
    }
    public boolean addContact(Contact contact) {
       return contactDAO.create(contact);

    }
}
