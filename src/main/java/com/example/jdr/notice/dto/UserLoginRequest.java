package com.example.jdr.notice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class UserLoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
