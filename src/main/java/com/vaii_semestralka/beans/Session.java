package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.users.Role;
import org.springframework.stereotype.Component;

@Component
public class Session {
    public String getUserFirstName() {
        return LoggedInUser.getActualUser().getFirst_name();
    }
    public String getUserLastName() {
        return LoggedInUser.getActualUser().getLast_name();
    }
    public boolean jePrihlasenyPouzivatelAdmin() {
        return LoggedInUser.getActualUser().getUserRole() == Role.ADMIN;
    }
    public boolean checkUrlAuthorisation() {
        return LoggedInUser.getActualUser() != null;
        }
}
