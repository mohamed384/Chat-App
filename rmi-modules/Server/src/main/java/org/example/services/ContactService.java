package org.example.services;

import org.example.DAO.ContactDAOImpl;
import org.example.DTOs.UserDTO;
import org.example.models.Contact;
import org.example.models.Mapper.UserMapper;
import org.example.models.User;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private final ContactDAOImpl contactDAO;
    private UserMapper userMapper = UserMapper.INSTANCE;

    public ContactService(){
        this.contactDAO= new ContactDAOImpl();
    }
    public boolean addContact(Contact contact) {
        return contactDAO.create(contact);
    }

    public boolean removeContact(Contact contact) {
        return contactDAO.deleteContact(contact.getUserID() , contact.getFriendID());
    }
    public List<UserDTO>  getAllContactsByUserId(String sender){
        List<UserDTO> contactDTOList = new ArrayList<>();
        for (User contact : contactDAO.getAllContactsByUserId(sender)) {
            contactDTOList.add(userMapper.toDTO(contact));
        }
        return contactDTOList;
    }
    public boolean acceptInvite(String senderId, String receiverId) {
        return contactDAO.acceptInvite(senderId, receiverId);
    }

    public boolean contactExists(String userPhone, String friendPhone) {
      return   contactDAO.contactExists(userPhone,friendPhone);
    }
    public boolean deleteContact(String sender, String receiver) {
        return contactDAO.deleteContact(sender, receiver);
    }
}
