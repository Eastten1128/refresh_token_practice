package com.example.jdr.notice.service;

import com.example.jdr.notice.entity.Role;
import com.example.jdr.notice.entity.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    public User getUser() {return user;}

    public Long getUserId() {return user.getUserId();}

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    //사용자에게 부여된 권한 그룹을 변환한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("Role_" + user.getRole().name())
        );
    }


}
