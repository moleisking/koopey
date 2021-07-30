package com.koopey.api.model.authentication;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class CreateUser implements Serializable{
    
   
    private String avatar;

  
    private Date birthday;

  
    private String email;

 
    private String mobile;

  
    private String password;

    
    private String username;
}
