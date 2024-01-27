package org.example.DTOs;

import java.io.Serializable;

public class ContactDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String friendID;
    private String userID;

    public ContactDTO() {
    }

    public ContactDTO(String friendID, String userID) {
        this.friendID = friendID;
        this.userID = userID;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "friendID='" + friendID + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}