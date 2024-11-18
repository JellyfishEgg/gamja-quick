    package com.sparta.gamjaquick.config.security.jwt;

    import io.jsonwebtoken.*;
    import io.jsonwebtoken.security.Keys;
    import jakarta.annotation.PostConstruct;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;

    @Component
    @RequiredArgsConstructor
    public class JwtProvider {

        private final JwtProperties jwtProperties;
        private final UserDetailsService userDetailsService;
        private SecretKey secretKey;

        @PostConstruct
        public void init() {
            secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        }

        /**
         * JWT 토큰 생성
         *
         * @param username 사용자 이름
         * @param roles    사용자 역할
         * @return 생성된 JWT 토큰
         */
        public String createToken(String username, String roles) {
            System.out.println(username);
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", roles);
            claims.put("sub", username);

            return Jwts.builder()
                    .setSubject(username)
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(secretKey)
                    .compact();
        }

        /**
         * JWT 토큰에서 인증 객체를 생성
         *
         * @param token JWT 토큰
         * @return Authentication 객체
         */
        public Authentication getAuthentication(String token) {
            String username = getUsernameFromToken(token);
            //System.out.println(username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }

        /**
         * JWT 토큰에서 사용자 이름 추출
         *
         * @param token JWT 토큰
         * @return 사용자 이름
         */
        public String getUsernameFromToken(String token) {
            //System.out.println(token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }

        /**
         * JWT 토큰의 유효성을 검증
         *
         * @param token JWT 토큰
         * @return 유효한 경우 true, 그렇지 않은 경우 false
         */
        public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }


    }