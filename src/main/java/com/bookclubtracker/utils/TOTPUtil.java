package com.bookclubtracker.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class TOTPUtil {
    public static String generateSecret() {
        GoogleAuthenticatorKey key = new GoogleAuthenticator().createCredentials();
        return key.getKey();
    }

    public static String getTOTPCode(String secret) {
        return Integer.toString(new GoogleAuthenticator().getTotpPassword(secret)); // Convert int to String
    }

    public static boolean validateTOTPCode(String secret, String code) {
        return new GoogleAuthenticator().authorize(secret, Integer.parseInt(code));
    }
}
