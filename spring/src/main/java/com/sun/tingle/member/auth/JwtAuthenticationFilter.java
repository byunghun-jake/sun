package com.sun.tingle.member.auth;

import com.sun.tingle.member.api.service.MemberService;
import com.sun.tingle.member.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private MemberService memberService;

    private JwtUtil jwtUtil;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, MemberService memberService) {
        super(authenticationManager);
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // request에 Header(jwtToken)를 획득한다.
        // Read the Authorization header, where the JWT Token should be
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // If header is null delegate to Spring impl and exit
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            token = token.substring("Bearer ".length());
            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication = jwtUtil.getAuthentication(token);    // 인증 객체 생성
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            log.error("토큰 인증 에러 :{}" + ex);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
