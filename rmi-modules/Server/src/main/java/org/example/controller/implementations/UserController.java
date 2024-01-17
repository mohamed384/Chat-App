package org.example.controller.implementations;



import org.example.DTOs.UserDTO;
import org.example.controller.services.UserService;
import org.example.models.User;
import org.example.interfaces.UserAuthentication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserController extends UnicastRemoteObject implements UserAuthentication {

    private UserService userService;

    public UserController() throws RemoteException {
        this.userService = new UserService();
    }

    @Override
    public Boolean signup(UserDTO userDto) throws RemoteException {
        return userService.signup(userDto);
    }

    @Override
    public UserDTO login(String phoneNumber , String password ) throws RemoteException {
        return userService.login( phoneNumber ,  password);

    }


}

