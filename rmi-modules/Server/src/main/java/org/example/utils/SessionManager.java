package org.example.utils;

import org.example.DTOs.UserDTO;

import java.util.UUID;

public class SessionManager {

    private static SessionManager instance;
    private UserDTO currentUser;
    private UUID sessionToken;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void startSession(UserDTO user) {
        this.currentUser = user;
        this.sessionToken = UUID.randomUUID();

    }

    public void endSession() {
        this.currentUser = null;
        this.sessionToken = null;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public UUID getSessionToken() {
        return sessionToken;
    }
}

