package org.example.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {

        private String phoneNumber;
        private String displayName;
        private String email;
        private String passwordHash;
        private String gender;
        private String country;
        private Date dateOfBirth;
        private String bio;
        private UserStatus status;
        private Timestamp lastSeen;

        private String picture;


        public User(String phoneNumber, String displayName, String email,
                    String passwordHash, String gender, String country,
                    Date dateOfBirth, String bio, UserStatus status, String picture) {
            this.phoneNumber = phoneNumber;
            this.displayName = displayName;
            this.email = email;
            this.passwordHash = passwordHash;
            this.gender = gender;
            this.country = country;
            this.dateOfBirth = dateOfBirth;
            this.bio = bio;
            this.status = status;
            this.picture = picture;
        }

        public UserStatus getStatus() {
            return status;
        }

        public void setStatus(UserStatus status) {
            this.status = status;
        }
        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword_hash(String password_hash) {
            this.passwordHash = password_hash;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }


        public String getDisplayName() {
            return displayName;
        }

        public String getEmail() {
            return email;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public String getGender() {
            return gender;
        }

        public String getCountry() {
            return country;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public String getBio() {
            return bio;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }


    }