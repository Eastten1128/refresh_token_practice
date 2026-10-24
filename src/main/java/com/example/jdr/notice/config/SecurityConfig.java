package com.example.jdr.notice.config;

import com.example.jdr.notice.auth.jwt.JwtAuthenticationFilter;
import com.example.jdr.notice.auth.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//설정 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //요청에 적용할 보안 필터 체인
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenProvider jwtTokenProvider
    ) throws Exception{

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtTokenProvider);
        http
                //Authorization 헤더로만 인증하는 Api 기준
                .csrf(AbstractHttpConfigurer::disable)
                //Website FormLogin 방식 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                //Http기본인증 차단
                //Base64 기반 단순 인코딩이라 보안에 취약
                .httpBasic(AbstractHttpConfigurer::disable)
                //임시 비활성화
                .logout(AbstractHttpConfigurer::disable)
                //인증 상태를 HTTP 세션에 저장하지 않음
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                //인가 요청
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/users/signup" ).permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/login").permitAll()

                        //게시글 조회 공개
                        .requestMatchers(HttpMethod.GET,"/api/post").permitAll()


                        //나머지는 인증 필요
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception-> exception
                        //인증 없이 보호 API 요청
                        .authenticationEntryPoint(
                                (request, response, e) ->{
                                    //401 보내기
                                    response.setStatus(401);
                                    response.setContentType("application/json;charset=UTF-8");
                                    response.getWriter().write("{\"message\":\"인증이 필요합니다.\"}");
                                }
                        )
                        //인증했는데 권한 부족
                        .accessDeniedHandler(
                                (request, response, e) -> {
                                    response.setStatus(401);
                                    response.setContentType("application/json;charset=UTF-8");
                                    response.getWriter().write("{\"message\":\"접근 권한이 없습니다.\"}");

                                }
                        )
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
