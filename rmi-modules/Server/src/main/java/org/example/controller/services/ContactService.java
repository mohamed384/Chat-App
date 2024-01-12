package org.example.controller.services;

import org.example.controller.DAO.UserDAO;
import org.example.models.Contact;
import org.example.models.User;

public class ContactService {
    private UserDAO userDao;

    public ContactService(){
        this.userDao= new UserDAO();
    }
    public boolean addContact(Contact contact) {
       User user = UserDAO.findByPhoneNumber(contact.getContactPhoneNumber());
        return false;
    }
}
