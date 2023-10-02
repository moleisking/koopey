package com.koopey.api.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserRegisterDto extends UserDto {

    
    private Long birthday;
   
    private String ip;
    //private String avatar;
    private String email;
    private String mobile;
     private String device;
   // private String name;
    private String password;
   // private String language;
   // private String timeZone;
}
