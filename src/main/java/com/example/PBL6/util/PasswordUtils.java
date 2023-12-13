package com.example.PBL6.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    public static String encodePassword(String password) {
        if(password == null) {
            return "";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean comparePassword(String pass, String hashedPass) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(pass, hashedPass);
    }
}
