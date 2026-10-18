package com.example.jdr.notice.dto;

import jakarta.validation.constraints.NotNull;

public class UserRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;


}
