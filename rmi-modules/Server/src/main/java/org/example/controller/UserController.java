package org.example.controller;



import org.example.DTOs.UserDTO;
import org.example.services.UserService;
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
        System.out.println(userDto.toString());
        return userService.signup(userDto);
    }

    @Override
    public UserDTO login(String phoneNumber , String password ) throws RemoteException {
        return userService.login( phoneNumber ,  password);

    }

    @Override
    public UserDTO getUser(String phoneNumber) throws RemoteException {
        return userService.getUser(phoneNumber);
    }


}

