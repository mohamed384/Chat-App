package org.example.DTOs;

import org.example.models.UserStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


    public class UserDTO implements Serializable {

        private static final long serialVersionUID = 2756980860496063096L;

        // Other fields...
        private String phoneNumber;
        private String displayName;
        private String email;
        private String passwordHash;
        private String confirmPassword;
        private String gender;
        private String country;
        private Date dateOfBirth;
        private String bio;
        private UserStatus status;
        private Timestamp lastSeen;

        private String picture;

        public UserDTO() {
        }

        public UserDTO(String phoneNumber, String displayName, String email, String passwordHash,
                       String confirmPassword, String gender, String country, Date dateOfBirth,
                       String bio, UserStatus status, String picture) {

            this.phoneNumber = phoneNumber;
            this.displayName = displayName;
            this.email = email;
            this.passwordHash = passwordHash;
            this.confirmPassword = confirmPassword;
            this.gender = gender;
            this.country = country;
            this.dateOfBirth = dateOfBirth;
            this.bio = bio;
            this.status = status;
            this.picture = picture;

        }

        public UserDTO(String phoneNumber, String displayName, String email, String passwordHash, String confirmPassword,
                       String gender, String country, Date dateOfBirth, String bio, UserStatus status, Timestamp lastSeen,
                       String picture) {

            this(phoneNumber, displayName, email, passwordHash, confirmPassword, gender, country,
                    dateOfBirth, bio, status, picture);
            this.lastSeen = lastSeen;
        }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "phoneNumber='" + phoneNumber + '\'' +
                    ", displayName='" + displayName + '\'' +
                    ", email='" + email + '\'' +
                    ", passwordHash='" + passwordHash + '\'' +
                    ", confirmPassword='" + confirmPassword + '\'' +
                    ", gender='" + gender + '\'' +
                    ", country='" + country + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", bio='" + bio + '\'' +
                    ", status=" + status +
                    ", lastSeen=" + lastSeen +
                    ", picture='" + picture + '\'' +
                    '}';
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public UserStatus getStatus() {
            return status;
        }

        public void setStatus(UserStatus status) {
            this.status = status;
        }

        public Timestamp getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(Timestamp lastSeen) {
            this.lastSeen = lastSeen;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }


