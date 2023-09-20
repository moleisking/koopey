package com.koopey.model.authentication;

import com.koopey.model.User;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
//import lombok.Data;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RegisterUser extends User {
    public Boolean cookie;
    public Boolean gdpr;
    public Boolean notify;

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
