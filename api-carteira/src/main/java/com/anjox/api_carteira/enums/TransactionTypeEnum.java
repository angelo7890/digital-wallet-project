package com.anjox.api_carteira.enums;

public enum TransactionTypeEnum {
    DEPOSIT("deposit"),
    SUBTRACT("subtract");

    private final String value;
    TransactionTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
