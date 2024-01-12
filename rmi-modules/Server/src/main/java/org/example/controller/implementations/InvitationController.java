package org.example.controller.implementations;

import org.example.controller.DAO.UserDAO;
import org.example.controller.services.InvitationService;
import org.example.interfaces.UserInvitation;
import org.example.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InvitationController extends UnicastRemoteObject implements UserInvitation {

    InvitationService invitationService;
    protected InvitationController() throws RemoteException {
        invitationService = new InvitationService();
    }

    @Override
    public void sendInvitation(String phoneNumber) {

    }

    @Override
    public void rejectInvitation(String phoneNumber) {

    }
}
