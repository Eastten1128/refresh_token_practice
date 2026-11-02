package com.example.jdr.notice.user.dto;

import com.example.jdr.notice.user.entity.Role;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserLoginResponse {

    private Long  userId;
    private String username;
    private Role role;

}
