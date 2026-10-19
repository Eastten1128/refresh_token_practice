package com.example.jdr.notice.dto;


import com.example.jdr.notice.entity.Role;
import com.example.jdr.notice.entity.User;
import jakarta.persistence.Column;
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
