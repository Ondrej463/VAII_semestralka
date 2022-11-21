package com.vaii_semestralka.beans;

import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import com.vaii_semestralka.validators.Fields;
import com.vaii_semestralka.validators.NewUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.util.HashMap;


@Configuration
public class RegisterBean {
    @Autowired private UserService userService;
    @Autowired private NewUserValidator userValidator;


    public boolean validate(UserEntity userEntity) {
        userValidator.validateUser(userEntity);
        return userValidator.isValid();
    }
    public void save(UserEntity user) {
        this.userService.save(user);
    }
    public void addErrorMessagesToModel(Model model) {
        HashMap<String, String> messages = this.userValidator.getMessages();
        for (String messageKey : messages.keySet()) {
            if (!messages.get(messageKey).equals("")) {
                model.addAttribute(messageKey, messages.get(messageKey));
                model.addAttribute(Fields.getField(messageKey).getResult(), "error");
            } else {
                model.addAttribute(Fields.getField(messageKey).getResult(), "success");
            }
        }
    }
    public boolean validatePasswords(String password, String repeatedPassword) {
        return this.userValidator.validatePasswords(password, repeatedPassword);
    }

    public void refreshPasswords(Model model) {
        if (this.userValidator.isSomeFieldExceptForPasswordInError()) {
            model.addAttribute(Fields.PASSWORD.getResult(), "");
            model.addAttribute(Fields.REPEAT_PASSWORD.getResult(), "");
        }
    }
}
