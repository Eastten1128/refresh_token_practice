package com.example.jdr.notice.service;

import com.example.jdr.notice.auth.dto.LoginResponse;
import com.example.jdr.notice.auth.jwt.JwtTokenProvider;
import com.example.jdr.notice.dto.UserLoginRequest;
import com.example.jdr.notice.dto.UserResponse;
import com.example.jdr.notice.dto.UserSignupRequest;
import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    //회원가입
    public UserResponse signup(UserSignupRequest request) {
        //아이디 중복 검사
        //이메일 중복 검사

        //패스워드 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //
        User user = new User(
                request.getUsername(),
                encodedPassword
                //Role = Role.USER
        );

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);

    }

    @Transactional(readOnly = true)
    //로그인
    public LoginResponse login(UserLoginRequest request) {


        //로그인 검사
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("아이디를 찾을 수 없음"));

        //비밀번호 검증 -> 추후에 해싱으로 비밀번호 암호화 축
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        String accessToken =
                jwtTokenProvider.createAccessToken(user.getUserId());

        return LoginResponse.of(accessToken, user.getUserId(),user.getUsername(), user.getRole());
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
