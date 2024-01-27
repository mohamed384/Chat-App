package org.example.Utils;

import java.util.regex.Pattern;

public class UserDataValidator {

    private static final Pattern EMAILPATTERN =
            Pattern.compile("^[A-Za-z0-9+.-]+@(.+)$");

    public static boolean isValidEmail(String email) {
        return EMAILPATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11 && phoneNumber.matches("[0-9]+");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }
    public static boolean isValidBio(String bio) {
        return bio.length() <= 50;
    }

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[a-zA-Z]{1,30}( [a-zA-Z]{1,30})?$");

    public static boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}