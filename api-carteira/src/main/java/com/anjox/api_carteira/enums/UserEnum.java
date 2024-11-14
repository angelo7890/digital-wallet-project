package com.anjox.api_carteira.enums;

public enum UserEnum {
    USER("user"),
    ADM("adm");

    private final String value;
    UserEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
