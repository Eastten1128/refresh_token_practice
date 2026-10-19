package com.example.jdr.notice.controller;

import com.example.jdr.notice.dto.UserLoginRequest;
import com.example.jdr.notice.dto.UserResponse;
import com.example.jdr.notice.dto.UserSignupRequest;
import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    //회원가입
    @PostMapping("/signup")
    public UserResponse signup(@Valid @RequestBody UserSignupRequest request){
        return userService.signup(request);

    }
    //로그인
    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody UserLoginRequest request){
        return userService.login(request);
    }

    //유저 조회
    @GetMapping("/{userId}")
    public UserResponse getUserName(@PathVariable("userId") Long userId){
        return userService.findById(userId);
    }
}
