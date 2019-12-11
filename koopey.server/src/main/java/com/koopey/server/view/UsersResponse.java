package com.koopey.server.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsersResponse implements Serializable {

    private static final long serialVersionUID = -747686732290633287L;
    
   // private <List>User users;
}