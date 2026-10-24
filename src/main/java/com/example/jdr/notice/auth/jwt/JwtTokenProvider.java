package com.example.jdr.notice.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

//JWT 생성 클래스
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessExpirationSeconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration-seconds}") long expirationSeconds
    ){
        //Secret Key Decode
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        if(expirationSeconds <= 0){
            throw new IllegalArgumentException("토큰 유효 시간이 만료됐습니다.");
        }
        this.accessExpirationSeconds = expirationSeconds;
    }

    // Access Token 발급
    public String createAccessToken(Long userId){
        //LocalDateTime보다 기계친화적인 Instant 시간 객체
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId.toString())
                .issuer("notice")
                .claim("token_type", "ACCESS")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessExpirationSeconds)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
    // Access Token 검증
    public Long parseAccessToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .requireIssuer("notice")
                .require("token_type", "ACCESS")
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getExpiration() == null){
            throw new JwtException("만료 시간이 없는 토큰입니다.");
        }

        //주체 꺼내기
        String subject = claims.getSubject();
        //주체 체킹
        if (subject == null || subject.isBlank()) {
            throw new JwtException("사용자 ID가 없는 토큰입니다.");
        }

        try{
            Long userId = Long.valueOf(subject);

            if (userId <= 0){
                throw new JwtException("올바르지 않은 사용자 ID입니다.");
            }

            return userId;
        } catch (NumberFormatException e){
            throw new JwtException("올바르지 않은 사용자 ID 형식", e);
        }
    }
}
