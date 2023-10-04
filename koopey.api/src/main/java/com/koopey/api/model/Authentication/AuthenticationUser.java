package com.koopey.api.model.authentication;

import com.koopey.api.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationUser extends User {

    private String token;   
 
    public AuthenticationUser(){}

    public AuthenticationUser( String token, User user){          
        this.setId(user.getId());
        this.setName(user.getName()); 
        this.setDescription(user.getDescription()); 
        this.setTimeZone(user.getTimeZone());
        this.setAlias(user.getAlias());
        this.setType(user.getType());
        this.setBirthday(user.getBirthday());
        this.setToken(token);
        this.setCookie(user.getCookie())   ;
        this.setGdpr(user.getGdpr())   ; 
        this.setCurrency(user.getCurrency());
        this.setEmail(user.getEmail());
        this.setAvatar(user.getAvatar());
        this.setLanguage(user.getLanguage());
        this.setLatitude(user.getLatitude());
        this.setLongitude(user.getLongitude());
        this.setMeasure(user.getMeasure());
        this.setNotifyByDevice(getNotifyByDevice());
          this.setNotifyByEmail(user.getNotifyByEmail());
        this.setTrack(user.getTrack());
        this.setVerify(user.getVerify());
    }

}