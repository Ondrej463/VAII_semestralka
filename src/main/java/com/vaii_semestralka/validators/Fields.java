package com.vaii_semestralka.validators;

import lombok.Getter;

public enum Fields {
    NAME("nameErrorMessage", "nameResult"),
    LAST_NAME("lastNameErrorMessage", "lastNameResult"),
    ADDRESS("addressErrorMessage", "addressResult"),
    BIRTH_DATE("birthDateErrorMessage", "birthResult"),
    EMAIL("emailErrorMessage", "emailResult"),
    PASSWORD("passwordErrorMessage", "passwordResult"),
    REPEAT_PASSWORD("repeatPasswordErrorMessage", "repeatPasswordResult");

    @Getter
    private String messageName;
    @Getter private String result;
    Fields(String messageName, String result) {
        this.messageName = messageName;
        this.result = result;
    }
    public static Fields getField(String messageName) {
        for (Fields field : Fields.values()) {
            if (field.getMessageName().equals(messageName)) {
                return field;
            }
        }
        return null;
    }
}
