package com.example.jdr.notice.dto;

import com.example.jdr.notice.entity.Role;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserLoginResponse {

    private Long  userId;
    private String username;
    private Role role;

}
