package org.example.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAuthentication extends Remote {
    public boolean signup() throws RemoteException;
}
