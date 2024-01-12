package org.example.interfaces;

import org.example.DTOs.UserDTO;
import org.example.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAuthentication extends Remote {
    public Boolean signup(UserDTO userDTO) throws RemoteException;
    public  User login(String phoneNumber , String password) throws RemoteException;
}
