package com.example.jdr.notice.controller;

import com.example.jdr.notice.dto.UserResponse;
import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserResponse getUserName(@PathVariable("userId") Long userId){
        return userService.findById(userId);
    }
}
