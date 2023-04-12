package com.process.model.Enum;

public enum ResponseStatus {
    WRONG_DATA("512"),
    INTERNAL_GENERAL_SERVER_ERROR("500"),
    VALIDATION_ERROR("400");
    public final String code;

    ResponseStatus(String s) {
        code = s;
    }
}
