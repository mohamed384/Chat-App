package org.example.responses;

import org.example.DTOs.UserDTO;

import java.util.UUID;

public class LoginResponse {
    private UserDTO user;
    private UUID sessionId;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }
}
