package com.question.memo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.question.memo.util.JwtUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = JwtUtil.resolveToken(request);
        try {
            JwtUtil.validateToken(token);
            return true;
        } catch (JwtException ex) {
            log.error("유효하지 않은 토큰 입니다.");
            throw new JwtException(ex.getMessage());
        }
    }
}
