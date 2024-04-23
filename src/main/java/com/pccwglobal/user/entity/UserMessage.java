package com.pccwglobal.user.entity;

public enum UserMessage {
    USER_ID_NOT_FOUND("User id not found."),
    USER_ID_UPDATED("User id updated."),
    USER_ID_DELETED("User id deleted."),
    GET_USER_BY_ID("Get user by id."),
    REGISTER_USER("Register user."),
    GET_ALL_USERS("Get all users.");

    private final String message;

    UserMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
