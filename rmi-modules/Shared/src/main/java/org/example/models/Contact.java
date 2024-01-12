package org.example.models;

import java.io.Serializable;

public class Contact implements Serializable {
    String userId;
    String contactPhoneNumber;
    int isBlocked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public Contact(String userId, String contactPhoneNumber) {
        this.userId = userId;
        this.contactPhoneNumber = contactPhoneNumber;
    }
}
