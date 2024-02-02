package org.example.DTOs;

import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

public class ContactDTO {
    private String displayName;
    private String bio;
    private String phoneNumber;
    private String emailAddress;
    private byte[] picture;

    private UserMode userMode;
    private UserStatus userStatus;

    public ContactDTO(String displayName, String bio, String phoneNumber, String emailAddress, byte[] picture) {
        this.displayName = displayName;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.picture = picture;
    }

    public ContactDTO() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public byte[] getPicture() {
        return picture;
    }

}
