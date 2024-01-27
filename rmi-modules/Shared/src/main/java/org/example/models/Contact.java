package org.example.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Contact implements Serializable {
    private String friendID;
    private String userID;


    //private Timestamp creationDate;


    public Contact(String friendID, String userID) {
        this.friendID = friendID;
        this.userID = userID;
        //this.creationDate = creationDate;
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

//    public Timestamp getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Timestamp creationDate) {
//        this.creationDate = creationDate;
//    }
}