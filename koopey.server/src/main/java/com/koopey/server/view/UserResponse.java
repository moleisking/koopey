package com.koopey.server.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserResponse {

    private  String id;

    private  String alias;

    private  String name;

    private  String description;

    private  String avatar;

    //private final Date birthday;
}