package com.koopey.model.authentication;

import java.math.BigDecimal;
import java.util.Date;

public class RegisterUser {
    private Boolean cookie;
    private Boolean gdpr;
    private Date birthday;
    private BigDecimal altitude;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String alias;
    private String avatar;
    private String email;
    private String mobile;
    private String name;
    private String password;
    private String language;
    private String timeZone;

    boolean isEmpty() {
        return alias == null || name == null || email == null || alias.length() <= 0 || name.length() <= 0 || email.length() <= 0 ? true : false;
    }
}
