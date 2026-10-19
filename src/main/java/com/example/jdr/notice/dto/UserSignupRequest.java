package com.example.jdr.notice.dto;

import jakarta.validation.constraints.NotNull;


public class UserSignupRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;


}
