package com.example.jdr.notice.service;

import com.example.jdr.notice.dto.UserLoginRequest;
import com.example.jdr.notice.dto.UserResponse;
import com.example.jdr.notice.dto.UserSignupRequest;
import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public UserResponse signup(UserSignupRequest request) {
        //아이디 중복 검사
        //이메일 중복 검사

        //
        User user = new User(
                request.getUsername(),
                //추후 Encoding
                request.getPassword()
        );

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);

    }
    //로그인
    public UserResponse login(UserLoginRequest request) {
        //로그인 검사
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("아이디를 찾을 수 없음"));

        //비밀번호 검증 -> 나중에 해싱으로 바꿀 예정
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    //유저 조회
    public UserResponse findById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "사용자를 찾을 수 없음."
                ));
        return UserResponse.from(user);
    }
}
