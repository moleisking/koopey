package com.koopey.api.model.authentication;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticationToken implements Serializable {

    private String token;
 
    public AuthenticationToken(){}

    public AuthenticationToken( String token){     
        this.token = token;       
    }

}