package com.sun.tingle.member.util;

import com.sun.tingle.member.api.dto.request.MemberReqDto;
import com.sun.tingle.member.api.service.MemberService;
import com.sun.tingle.member.auth.UserAuthDetail;
import com.sun.tingle.member.db.entity.MemberEntity;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil{
    private static String SECRET_KEY;
    private static Integer EXPIRE_TIME;
    private final MemberService memberService;
 
    @Autowired
    public JwtUtil(@Value("${jwt.secret}") String SECRET_KEY, @Value("${jwt.expiration}") Integer EXPIRE_TIME, MemberService memberService) {
        this.SECRET_KEY = SECRET_KEY;
        this.EXPIRE_TIME = EXPIRE_TIME;
        this.memberService = memberService;
    }

    public <T> String createToken(MemberEntity memberEntity) {
        Date now = new Date();
        //HS256 방식으로 암호화 방식 설정
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setIssuer("tingle") // 발급자
                .claim("id", memberEntity.getId())
                .claim("email", memberEntity.getEmail())
                .claim("name", memberEntity.getName())
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, signingKey); //암호화 알고리즘

        return builder.compact();
    }

    //Jwt Token을 복호화해 Id를 return
    public Long getIdFromJwt(String jwt){
        return getClaims(jwt).getBody().get("id", Long.class);
    }

    public static boolean validateToken(String jwt){
        return getClaims(jwt) != null;
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Long id = this.getIdFromJwt(token);
        MemberEntity memberEntity = memberService.getMemberById(id).get();
        if(memberEntity != null) {
            UserAuthDetail userAuthDetail = new UserAuthDetail(memberEntity);
            UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(id,
                    memberEntity.getPassword(), userAuthDetail.getAuthorities());
            jwtAuthentication.setDetails(userAuthDetail);
            return jwtAuthentication;
        }
        return null;
    }

//    public static JWTVerifier getVerifier() {
//        return JWT
//                .require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
//                .build();
//    }

    //claims : 속성 정보(?), 권한 집합
    //JWT는 속성 정보 (Claim)를 JSON 데이터 구조로 표현한 토큰
    //Jwt토큰 유효성 검증 메서드
    private static Jws<Claims> getClaims(String jwt){
        try{
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
        }catch (SignatureException ex) { // 기존 서명을 확인하지 못했을 때
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) { // JWT가 올바르게 구성되지 않았을 때
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) { // 유효기간이 초과되었을 때
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) { // : 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

//    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
//    @Override
//    public boolean isUsable(String jwt) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                    .parseClaimsJws(jwt).getBody();
//            return true;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return false;
//        }
//    }
}
