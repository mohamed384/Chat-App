package org.example.services;

import org.example.DAO.ContactDAOImpl;
import org.example.models.Contact;

import java.util.List;

public class ContactService {
    private final ContactDAOImpl contactDAO;

    public ContactService(){
        this.contactDAO= new ContactDAOImpl();
    }
    public boolean addContact(Contact contact) {
       return contactDAO.create(contact);
    }

    public boolean removeContact(Contact contact) {
        return contactDAO.deleteContact(contact.getUserID() , contact.getFriendID());
    }
    public List<Contact>  getAllContactsByUserId(String sender){
        return contactDAO.getAllContactsByUserId(sender);
    }

}
