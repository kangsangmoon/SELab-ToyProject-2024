package com.example.project.auth.token;

import com.example.project.common.util.DateUtil;
import com.example.project.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "role";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final RedisService redisService;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds, RedisService redisService) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.redisService = redisService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long id, String role) {
        return Jwts.builder()
                .setClaims(createClaims(id, role))
                .setIssuedAt(DateUtil.getDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(DateUtil.getTokenValidTime(DateUtil.getDate(), tokenValidityInMilliseconds))
                //.setIssuer("/localhost:8080")
                .compact();
    }

    public String createRefreshToken(Long id, String role){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + tokenValidityInMilliseconds);

        String token = Jwts.builder()
                .setClaims(createClaims(id, role))
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        redisService.saveToken(token, id);

        return token;
    }

    /**
     * @param id   userId 부분의 값
     * @param role role 부분의 값
     * @implSpec JWT 토큰의 Claims를 생성해주는 메소드
     */
    private Claims createClaims(Long id, String role) {
        var claims = Jwts.claims().setSubject("Code-For-Code");
        claims.put("userId", id);                                            //Long id : user의 PK
        claims.put(AUTHORITIES_KEY, role);

        return claims;
    }

    public Authentication getAuthentication(String token) {
        log.info("JWT Token {}", token);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * @param * setSigningKey : jwt key
     *          * parseClaimsJws : 파싱할 token
     *          * Body : claims
     *          * Subject : userId
     * @return * Long userId
     * @brief * 토큰 파싱 (해석) / jjwt 라이브러리
     */
    public Long getUserIdByToken(String token) {
        var data = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return Long.parseLong(data);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}