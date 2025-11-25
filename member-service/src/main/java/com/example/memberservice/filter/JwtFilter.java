package com.example.memberservice.filter;

import ch.qos.logback.core.util.StringUtil;
import com.example.memberservice.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        if (StringUtils.isNotBlank(token) || request.getServletPath().endsWith("/authorizations/check")) {

            jwtProvider.validateToken(token);
            log.info("valid token {}", token);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {

        String barerToken = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(barerToken) && barerToken.startsWith("Bearer ")) {
            return barerToken.substring("Bearer ".length());
        }

        return null;
    }



}
