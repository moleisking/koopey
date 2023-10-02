package com.koopey.model.authentication;

import com.koopey.model.Image;
import com.koopey.model.Messages;
import com.koopey.model.Transactions;
import com.koopey.model.User;

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
public class AuthenticationUser extends User {

    public static final String AUTH_USER_FILE_NAME = "authenticationUser.dat";
    private String ip ;
    private String device ;
    private String email;
    private String mobile;
    private String token;
    @Builder.Default
    boolean notify = false;
    @Builder.Default
    boolean terms = false;
    @Builder.Default
    boolean cookies = false;
    @Builder.Default
    Transactions transactions = new Transactions();
    @Builder.Default
    Messages messages = new Messages();

    public Image getAvatarImage() {
        Image img = new Image();
        img.uri = this.getAvatar();
        return img;
    }

    public boolean isEmpty() {
        if (token == null || token.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser() {
        return (User) this;
    }

    public void syncronize(User user) {
       // this.avatar = user.avatar;
        this.setDescription(user.getDescription());
       // this.mobile = user.mobile;
       // this.education = user.education;
       // this.currency = user.currency;
       // this.location = user.location;
    }
}
