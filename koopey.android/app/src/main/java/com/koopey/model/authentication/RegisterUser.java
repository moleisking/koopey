package com.koopey.model.authentication;

import com.koopey.model.User;

import java.math.BigDecimal;
import java.util.Date;

//import lombok.Data;

//@Data
public class RegisterUser extends User {
    public Boolean cookie;
    public Boolean gdpr;
    public Boolean notify;
    public Date birthday;
    public BigDecimal altitude;
    public BigDecimal latitude;
    public BigDecimal longitude;
    public String alias;
    public String avatar;
    public String currency;
    public String email;
    public String device;
    public String mobile;
    public String password;
    public String language;
    public String timeZone;

}
