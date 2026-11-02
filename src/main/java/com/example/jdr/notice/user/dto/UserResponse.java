package com.example.jdr.notice.user.dto;


import com.example.jdr.notice.user.entity.Role;
import com.example.jdr.notice.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long userId;
    private String username;
    private Role role;

    public UserResponse(Long userId, String username) {
        this.userId = userId;
        this.username = username;
        this.role = Role.USER;
    }

    public static UserResponse from(User user){
        return new UserResponse(
                user.getUserId(), user.getUsername()
        );
    }
}
