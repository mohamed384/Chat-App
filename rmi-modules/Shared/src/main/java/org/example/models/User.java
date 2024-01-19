package org.example.models;

import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
public class User {
    private int userID;
    private String phoneNumber;
    private String displayName;
    private String emailAddress;
    private String picture;
    private String passwordHash;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private UserStatus userStatus;
    private String userMode;

    private Timestamp lastLogin;

    public User( String phoneNumber, String displayName, String emailAddress, String profilePicture, String passwordHash, String gender, String country, Date dateOfBirth, String bio, UserStatus userStatus, String userMode, Timestamp lastLogin) {

        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.picture = profilePicture;
        this.passwordHash = passwordHash;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.userStatus = userStatus;
        this.userMode = userMode;
        this.lastLogin = lastLogin;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String profilePicture) {
        this.picture = profilePicture;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
}
