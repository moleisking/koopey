package com.koopey.api.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class UserRegisterDto implements Serializable {

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
}
