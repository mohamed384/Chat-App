package org.example.utils;

import org.example.DTOs.UserDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SessionManager {

    private static SessionManager instance;
    private Set<UserDTO> currentUser;
//    private UUID sessionToken;

    private SessionManager() {
        currentUser= new HashSet<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void startSession(UserDTO user) {
        currentUser.add(user);
//        this.sessionToken = UUID.randomUUID();

    }

    public void endSession(UserDTO user) {
//        this.currentUser = null;
        currentUser.remove(user);
    }
    public void endAllSessions(){
        currentUser.clear();
    }

//    public UserDTO getCurrentUser() {
//        return currentUser;
//    }
//
//    public UUID getSessionToken() {
//        return sessionToken;
//    }
}

