package com.pccwglobal.email.entity;

public enum EmailMessage {
    EMAIL_ALREADY_REGISTERED("Email address is already registered"),
    EMAIL_INVALID("Invalid email address");

    private final String message;

    EmailMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
