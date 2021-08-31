package com.koopey.api.model.authentication;

import lombok.Data;

@Data
public class AuthenticationToken {

    private String token;
 
    public AuthenticationToken(){}

    public AuthenticationToken( String token){     
        this.token = token;       
    }

}