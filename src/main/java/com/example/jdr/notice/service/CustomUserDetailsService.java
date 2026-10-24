package com.example.jdr.notice.service;

import com.example.jdr.notice.entity.User;
import com.example.jdr.notice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//사용자 조회 담당
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //username 기준 사용자 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없음"));

        //Spring Security가 이해하는 객체 반환
        return new CustomUserDetails(user);
    }
}
