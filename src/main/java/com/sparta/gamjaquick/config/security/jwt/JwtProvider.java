    package com.sparta.gamjaquick.config.security.jwt;

    import io.jsonwebtoken.*;
    import io.jsonwebtoken.security.Keys;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Date;

    @Component
    @RequiredArgsConstructor
    public class JwtProvider {

        private final JwtProperties jwtProperties;
        private final UserDetailsService userDetailsService;
        private final SecretKey secretKey = Keys.hmacShaKeyFor("6B3E3D511FB5EBB3BDFB865F46A54".getBytes());

        /**
         * JWT 토큰 생성
         *
         * @param username 사용자 이름
         * @param roles    사용자 역할
         * @return 생성된 JWT 토큰
         */
        public String createToken(String username, String roles) {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

            return Jwts.builder()
                    .setSubject(username)
                    .claim("roles", roles)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(secretKey, SignatureAlgorithm.HS256)
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