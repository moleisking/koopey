package com.koopey.api.model.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthenticationDto implements Serializable {
   
    private String alias;
    private String email;
    private String password;    
}