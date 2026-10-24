package com.example.jdr.notice.auth.dto;

import com.example.jdr.notice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String tokenType;
    private String accessToken;
    private Long userId;
    private String username;
    private Role role;

    public static LoginResponse of(
            String accessToken,
            Long userId,
            String username,
            Role role
    ) {
        return new LoginResponse(
                "Bearer",
                accessToken,
                userId,
                username,
                role
        );
    }
}