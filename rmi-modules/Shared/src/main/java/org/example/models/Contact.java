package org.example.models;

import java.sql.Timestamp;

public class Contact {
    private int friendID;
    private int userID;
    //private Timestamp creationDate;


    public Contact(int friendID, int userID) {
        this.friendID = friendID;
        this.userID = userID;
        //this.creationDate = creationDate;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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