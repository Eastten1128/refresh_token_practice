package com.example.jdr.notice.auth.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor

//JWT 인증 필터 작성 클래스
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    //필터 처리 로직
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //인증헤더 읽기
        String header = request.getHeader("Authorization");

        //헤더가 없으면 인증 정보를 등록하지 않고 다음 단계로 진행
        if (header == null){
            //다음 필터로 진행
            filterChain.doFilter(request, response);
            return;
        }

        //헤더 형식 검사
        if (!header.startsWith("Bearer ")){
            unauthorized(response);
            return;
        }

        //"Bearer " 이후 Access_token 추출
        String token = header.substring(7).trim();

        Long userId;

        try {
            //서명, 만료, 발급자, 토큰 타입 파싱 및 검증
            userId = jwtTokenProvider.parseAccessToken(token);
        } catch (JwtException | IllegalArgumentException e){
            //ContextHolder에서 clear
            SecurityContextHolder.clearContext();
            unauthorized(response);
            return;
        }

        /**
         * 여기 var 쓴 이유가 무엇?
         */
        //검증된 사용자 ID로 인증 완료 객체 생성
        var authentication =
                //Username과 Password를 담음
                UsernamePasswordAuthenticationToken.authenticated(
                        userId,
                        null,
                        List.of()
                );

        //현재 요청의 인증 정보 생성
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        //securityContext에 Username, Password를 담은 인증정보를 설정
        context.setAuthentication(authentication);
        //SecurityContextHolder에 인증 정보 담기
        SecurityContextHolder.setContext(context);

        //다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    private void unauthorized(
            HttpServletResponse response
    )throws IOException {
        //401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //JSON content-type 지정
        response.setContentType("application/json;charset=UTF-8");
        //문자열 응답 반환
        response.getWriter().write(
                "{\"message\":\"유효한 Access Token이 필요합니다.\"}"
        );
    }
}
