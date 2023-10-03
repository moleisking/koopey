package com.koopey.model;

import com.koopey.model.base.Base;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class User extends Base {

    public static final String USER_FILE_NAME = "user.dat";
    @Builder.Default
    private boolean available = false;
    @Builder.Default
    private boolean notify = false;
    @Builder.Default
    private boolean verify = false;
    @Builder.Default
    private boolean cookie = false;
    @Builder.Default
    private boolean track = false;
    @Builder.Default
    private boolean gdpr = false;
    @Builder.Default
    private int distance = 0;
    @Builder.Default
    private int score = 0;
    @Builder.Default
    private int negative = 0;
    @Builder.Default
    private int positive = 0;
    @Builder.Default
    private Double altitude = 0.0d;
    @Builder.Default
    private Double latitude = 0.0d;
    @Builder.Default
    private Double longitude = 0.0d;
    private long birthday;
    private String alias;
    // private String username;
    private String avatar;
    private String education;
    private String measurement;
    private String ip;
    private String device;
    private String email;
    private String mobile;
    @Builder.Default
    private String currency = "eur";
    @Builder.Default
    private String language = "en";
    @Builder.Default
    private String measure = "metric";
    @Builder.Default
    private String player = "grey";
    private Location location;
    @Builder.Default
    private Wallets wallets = new Wallets();

    public Image getAvatarAsImage() {
        Image img = new Image();
        img.uri = this.getAvatar();
        return img;
    }



   /* public void setAlias(String alias) {
        this.username = alias;
    }

    public String getAlias() {
        return username;
    }*/


    public String getBirthdayAsString() {
        return new SimpleDateFormat("yyyy/MM/dd").format(birthday);
    }

    public User getUserBasicWithAvatar() {



       /* User. userBasic ;
        userBasic.id = this.id;
        userBasic.type = "basic";
        userBasic.setAlias(this.getAlias());
        userBasic.name = this.name;
        userBasic.score = this.score;
        userBasic.player = this.player;
        userBasic.avatar = this.avatar;
        userBasic.currency = this.currency;
        userBasic.language = this.language;
       // userBasic.reviews = this.reviews;
        userBasic.wallets = this.wallets;
        // userBasic.locations = this.locations;
        return userBasic;*/

        return null;
    }

   /* public boolean equals(User user) {
        if (user != null && user.equals(this) && user.username.equals(this.username)) {
            return true;
        } else {
            return false;
        }
    }*/


    public boolean isEmpty() {
        return alias == null || alias.length() <= 0 || avatar == null || avatar.length() <= 0 ||
                birthday <= 0 || currency == null || currency.length() <= 0 || language == null ||
                language.length() <= 0 ||latitude <= 0 || longitude <= 0 || super.isEmpty() ? true : false;
    }
}
