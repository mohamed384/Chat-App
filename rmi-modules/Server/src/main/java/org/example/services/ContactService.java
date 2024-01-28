package org.example.services;

import org.example.DAO.ContactDAOImpl;
import org.example.models.Contact;

public class ContactService {
    private final ContactDAOImpl contactDAO;

    public ContactService(){
        this.contactDAO= new ContactDAOImpl();
    }
    public boolean addContact(Contact contact) {
       return contactDAO.create(contact);
    }
}
