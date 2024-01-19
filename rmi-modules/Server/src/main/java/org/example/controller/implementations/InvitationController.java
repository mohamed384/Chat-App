package org.example.controller.implementations;

import org.example.controller.services.InvitationService;
import org.example.interfaces.UserInvitation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InvitationController extends UnicastRemoteObject implements UserInvitation {

    InvitationService invitationService;
    protected InvitationController() throws RemoteException {
        invitationService = new InvitationService();
    }

//    @Override
//    public boolean sendInvitation(Invitations invitations) {
//       return invitationService.sendInvitation(invitations);
//    }

    @Override
    public void rejectInvitation(String phoneNumber) {
    }

//    public void deleteInvitation(Invitations invitations){
//        invitationService.deleteInvitation(invitations);
//    }
}
