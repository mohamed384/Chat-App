package org.example.interfaces;

import org.example.models.Invitations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInvitation extends Remote {
    boolean sendInvitation(Invitations invitations) throws RemoteException;
    void rejectInvitation(String phoneNumber) throws RemoteException;
}
