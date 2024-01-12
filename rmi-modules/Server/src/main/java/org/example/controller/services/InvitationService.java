package org.example.controller.services;

import org.example.controller.DAO.InvitationDAO;
import org.example.models.Invitations;

public class InvitationService {
    private final InvitationDAO invitationDAO;

    public InvitationService() {
        invitationDAO = new InvitationDAO();
    }
    public boolean sendInvitation(Invitations invitations) {
       return invitationDAO.create(invitations);
    }

    public void deleteInvitation(Invitations invitations){
        invitationDAO.delete(invitations);
    }

}
