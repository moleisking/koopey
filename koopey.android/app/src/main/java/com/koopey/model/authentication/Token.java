package com.koopey.model.authentication;

public class Token {
    public static final String TOKEN_FILE_NAME = "token.dat";
    public String token = "";

    public boolean isEmpty() {
        return token == null || token.length() <= 0 ? true : false;
    }
}
