package com.koopey.model.authentication;

import com.koopey.model.User;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RegisterUser extends User {
    private String device;
    private String email;
    private String mobile;
    private String password;

    public boolean isEmpty() {
        return email == null ||  email.length() <= 0 || mobile == null ||  mobile.length() <= 0 ||
                password == null ||  password.length() <= 0 ||super.isEmpty() ? true : false;
    }
}
