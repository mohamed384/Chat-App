package org.example.Utils;

import org.example.DTOs.UserDTO;

public class UserToken {
    private static UserToken instance;
    private UserDTO userDTO;

    private UserToken() {
    }

    public static UserToken getInstance() {
        if (instance == null) {
            instance = new UserToken();
        }
        return instance;
    }

    public void setUser(UserDTO user) {
        this.userDTO = user;
    }

    public UserDTO getUser() {
        return this.userDTO;
    }
}