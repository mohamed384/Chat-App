package org.example.models;

import java.io.Serializable;

public class Contact implements Serializable {
    String userId;
    String contactPhoneNumber;
    int isBlocked;

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public Contact(String userId, String contactPhoneNumber) {
        this.userId = userId;
        this.contactPhoneNumber = contactPhoneNumber;
    }
}
