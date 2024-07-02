package com.question.memo.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.util.StringUtils;

import com.question.memo.domain.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {
    /**
     * body 가 들어간 토큰 생성
     *
     * @param body
     * @param expired 토근 만료 시간
     * @return
     */
    private static final String secret = AesUtil.encrypt("jwtSecretWordsForTokenIssue");

    public static String token(Long memberSeq, Optional<LocalDateTime> expired) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);

        JwtBuilder builder = Jwts.builder()
                .claim("memberSeq", AesUtil.encrypt(String.valueOf(memberSeq)))
                .expiration(Timestamp.valueOf(LocalDateTime.now().plusHours(2)))
                .signWith(key);

        // 만료시간을 설정할 경우 expir 설정
        expired.ifPresent(exp -> {
            builder.expiration(Timestamp.valueOf(exp));
        });

        return builder.compact();
    }

    /**
     * 기본 만료시간 : 하루 30분 : LocalDateTime.now().plusMinutes(30)
     * 1시간 : LocalDateTime.now().plusHours(1)
     *
     * @param body
     * @return
     */
//    public static String token(Map<String, Object> body) {
//        return token(body, Optional.of(LocalDateTime.now().plusMinutes(30)));
//    }

    /**
     * Authorization Header를 통해 인증
     */
    public static String resolveToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public static Claims verify(String token) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
        Claims payload = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return payload;
    }

    /**
     * 토큰의 유효성 + 만료일자 확인
     */
    public static boolean validateToken(String token) {
        try{
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
            SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new JwtException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtException("JWT claims string is empty");
        }
    }
}
