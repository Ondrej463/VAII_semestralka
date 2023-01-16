package com.vaii_semestralka.validators;
import com.vaii_semestralka.users.UserEntity;
import com.vaii_semestralka.users.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.HashMap;

@Component
public class NewUserValidator {
    @Autowired private UserService userService;
    @Getter private final HashMap<String, String> messages = new HashMap<>();

    public void validateUser(UserEntity user) {
       this.initializeMessages();
       this.validateName(user.getFirst_name());
       this.validateLastName(user.getLast_name());
       this.validateAddress(user.getAdress());
       this.validateEmail(user.getEmail());
       this.validateBirthDate(user.getBorn_date());
       this.validatePassword(user.getPasswd());
    }
    public void addMessage(String key, String value) {
        this.messages.put(key, value);
    }
    public boolean validatePasswords(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            addMessage(Fields.PASSWORD.getMessageName(), "Heslá sa nezhodujú!");
            addMessage(Fields.REPEAT_PASSWORD.getMessageName(), "Heslá sa nezhodujú!");
            return false;
        }
        return true;
    }
    public boolean isSomeFieldExceptForPasswordInError() {
        for (String message : this.messages.keySet()) {
            if (!this.messages.get(message).equals("")) {
                Fields fields = Fields.getField(message);
                if (fields != Fields.PASSWORD && fields != Fields.REPEAT_PASSWORD) {
                    return true;
                }
            }
        }
        return false;
    }
    private void validateName(String name) {
      if (name.length() == 0) {
          this.messages.put(Fields.NAME.getMessageName(), "Meno nesmie byť prázdne!");
      }
    }
    private void validateLastName(String lastName) {
        if (lastName.length() == 0) {
            this.messages.put(Fields.LAST_NAME.getMessageName(), "Priezvisko nesmie byť prázdne!");
        }
    }
    private void validateAddress(String address) {
        if (address.length() == 0) {
            this.messages.put(Fields.ADDRESS.getMessageName(), "Adresa nesmie byť prázdna!");
        }
    }
    private void validateBirthDate(String birthDate) {
        if (birthDate.length() == 0) {
            this.messages.put(Fields.BIRTH_DATE.getMessageName(), "Dátum narodenia nesmie byť prázdny!");
        } else {
            LocalDate birth = LocalDate.parse(birthDate);
            LocalDate now = LocalDate.now();
            if (birth.isAfter(now)) {
                this.messages.put(Fields.BIRTH_DATE.getMessageName(), "Neplatný dátum narodenia!");
            } else if (Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears() < 18) {
                this.messages.put(Fields.BIRTH_DATE.getMessageName(), "Zamietnuté, nedovŕšili ste 18 rokov!");
            }
        }
    }
    private void validateEmail(String email) {
        if (email.length() == 0) {
            this.messages.put(Fields.EMAIL.getMessageName(), "Email nesmie byť prázdny!");
        } else if (!this.isValidEmail(email)) {
            this.messages.put(Fields.EMAIL.getMessageName(), "Email nie je platný!");
        } else if (this.userService.getByEmail(email) != null) {
            this.addMessage(Fields.EMAIL.getMessageName(), "Používateľ so zadaným emailom už existuje!");
        }
    }
    private void validatePassword(String password) {
        if (password.length() == 0) {
            this.messages.put(Fields.PASSWORD.getMessageName(), "Heslo nesmie byť prázdne!");
            this.messages.put(Fields.REPEAT_PASSWORD.getMessageName(), "Heslo nesmie byť prázdne!");
        } else if (!password.chars().anyMatch(Character::isUpperCase)) {
            this.messages.put(Fields.PASSWORD.getMessageName(), "Heslo musí obsahovať aspoň jedno veľké písmeno!");
            this.messages.put(Fields.REPEAT_PASSWORD.getMessageName(), "Heslo musí obsahovať aspoň jedno veľké písmeno!");
        } else if (!password.chars().anyMatch(Character::isDigit)) {
            this.messages.put(Fields.PASSWORD.getMessageName(), "Heslo musí obsahovať aspoň jednu číslicu!");
            this.messages.put(Fields.REPEAT_PASSWORD.getMessageName(), "Heslo musí obsahovať aspoň jednu číslicu!");
        } else if (password.length() < 8) {
            this.messages.put(Fields.PASSWORD.getMessageName(), "Heslo musí mať aspoň 8 znakov!");
            this.messages.put(Fields.REPEAT_PASSWORD.getMessageName(), "Heslo musí mať aspoň 8 znakov!");
        }
    }
    private boolean isValidEmail(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regex);
    }
    public boolean isValid() {
        Collection<String> values = this.messages.values();
        for (String value : values) {
            if (!value.equals("")) {
                return false;
            }
        }
        return true;
    }
    private void initializeMessages() {
        this.messages.put(Fields.NAME.getMessageName(), "");
        this.messages.put(Fields.LAST_NAME.getMessageName(), "");
        this.messages.put(Fields.ADDRESS.getMessageName(), "");
        this.messages.put(Fields.BIRTH_DATE.getMessageName(), "");
        this.messages.put(Fields.EMAIL.getMessageName(), "");
        this.messages.put(Fields.PASSWORD.getMessageName(), "");
        this.messages.put(Fields.REPEAT_PASSWORD.getMessageName(), "");
    }
}

