package org.example;
import org.example.controller.DAO.ContactDAO;
import org.example.controller.DAO.InvitationDAO;
import org.example.controller.implementations.ContactController;
import org.example.controller.implementations.UserController;
import org.example.interfaces.UserAuthentication;
import org.example.models.Contact;
import org.example.models.InvitaionStatus;
import org.example.models.Invitations;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.Timestamp;


public class Server {

    public static void main(String[] args) {
        try {
            UserAuthentication stub = new UserController();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmiObject", stub);
            System.out.println("RMI Server is running...");

            InvitationDAO invitationDAO= new InvitationDAO();
            Invitations invitations= new Invitations("01125","011514",
                    InvitaionStatus.pending,new Timestamp(System.currentTimeMillis()));
            //invitationDAO.delete(invitations);
//            ContactController contactController = new ContactController();
//            Contact contact= new Contact("01125","011514");
//           contactController.addContact(contact);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
