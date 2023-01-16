package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.users.Hash;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class LoginBean {
    @Autowired private UserService userService;
    private String emailErrorMessage;
    private String passwordErrorMessage;

    public boolean validate(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean vysledok = true;
        UserEntity userEntity = this.userService.getByEmail(email);
        if (userEntity == null) {
            this.emailErrorMessage = "Používateľ s emailom " + email + " neexistuje!";
            this.passwordErrorMessage = null;
            vysledok = false;
        } else {
            this.emailErrorMessage = "";
            if (!userEntity.getPasswd().equals(Hash.hashPassword(password, userEntity.getSalt()))) {
                this.passwordErrorMessage = "Zadali ste nerpávne heslo!";
                vysledok = false;
            } else {
                this.passwordErrorMessage = "";
            }
        }
        return vysledok;
    }

    public void login(String email) {
        LoggedInUser.logIn(this.userService.getByEmail(email));
    }

    public void addErrorMessagesToModel(Model model) {
        if (!this.emailErrorMessage.equals("")) {
            model.addAttribute("emailErrorMessage", this.emailErrorMessage);
            model.addAttribute("emailResult", "error");
            model.addAttribute("passwordResult", "error");
        } else {
            model.addAttribute("emailResult", "success");
        }
        if (this.passwordErrorMessage != null) {
            if (!this.passwordErrorMessage.equals("")) {
                model.addAttribute("passwordErrorMessage", this.passwordErrorMessage);
                model.addAttribute("passwordResult", "error");
                model.addAttribute("emailResult", "error");
            } else {
                model.addAttribute("passwordResult", "success");
            }
        }
    }
}
