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

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RegisterUser extends User {
    private Boolean cookie;
    private Boolean gdpr;
    private Boolean notify;
    private BigDecimal altitude;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String alias;
    private String avatar;
    @Builder.Default
    private String currency = "eur";
    private String email;
    private String device;
    private String mobile;
    private String password;
    @Builder.Default
    private String language = "en";
    private String timeZone;

}
