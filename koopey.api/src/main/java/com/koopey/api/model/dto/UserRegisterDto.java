package com.koopey.api.model.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserRegisterDto implements Serializable {
       
    private String avatar;
  
    private Long birthday;
  
    private String email;
 
    private String mobile;

    private String name;
  
    private String password;
    
    private String username;
  
}
