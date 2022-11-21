package com.vaii_semestralka;


import com.vaii_semestralka.users.UserEntity;

public class LoggedInUser {
    private static UserEntity LOGGED_IN_USER;

    public static void logIn(UserEntity user) {
        LOGGED_IN_USER = user;
    }
    public static void logOut() {
        LOGGED_IN_USER = null;
    }
    public static UserEntity getActualUser() {
        return LOGGED_IN_USER;
    }
}
