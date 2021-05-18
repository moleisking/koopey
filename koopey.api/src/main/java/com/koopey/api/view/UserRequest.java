package com.koopey.api.view;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequest implements Serializable {

    private static final long serialVersionUID = -747683032287633266L;
    
    private String id; 

    private String username;
  
    private String email;

    private String name;  
   
    private Date birthday;
}