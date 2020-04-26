package com.koopey.server.model;

import lombok.Data;

@Data
public class AuthToken {

    private String token;
    //private String username;

    public AuthToken(){}

    /*public AuthToken(String token, String username){
        this.token = token;
        this.username =username;
    }*/

    public AuthToken(String token){
        this.token = token;
    }
}