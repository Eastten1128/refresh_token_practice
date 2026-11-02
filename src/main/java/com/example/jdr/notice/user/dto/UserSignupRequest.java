package com.example.jdr.notice.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserSignupRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
