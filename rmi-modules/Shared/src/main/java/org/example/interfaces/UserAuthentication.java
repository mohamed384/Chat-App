package org.example.interfaces;

import org.example.DTOs.UserDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAuthentication extends Remote {
    public Boolean signup(UserDTO userDTO) throws RemoteException;
    public  UserDTO login(String phoneNumber , String password) throws RemoteException;
    public UserDTO getUser(String phoneNumber) throws RemoteException;
    public boolean updateUser(UserDTO userDto) throws RemoteException;
}
