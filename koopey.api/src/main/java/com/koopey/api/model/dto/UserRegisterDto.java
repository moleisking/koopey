package com.koopey.api.model.dto;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserRegisterDto extends UserDto {

    private Boolean cookie;
    private Boolean gdpr;
    private Date birthday;
   
    //private String alias;
    //private String avatar;
    private String email;
    private String mobile;
     private String device;
   // private String name;
    private String password;
   // private String language;
   // private String timeZone;
}
