    package com.sparta.gamjaquick.config.security.jwt;

    import jakarta.servlet.Filter;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.ServletRequest;
    import jakarta.servlet.ServletResponse;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Component;
    import java.io.IOException;

    /**
     * JWT 인증 필터 클래스
     * 클라이언트 요청에서 JWT 토큰을 검증 ->  인증 컨텍스트에 설정
     */
    @Component
    @RequiredArgsConstructor
    public class JwtAuthenticationFilter implements Filter {

        private final JwtProvider jwtProvider;

        /**
         * 필터 처리 메서드
         * @param request 클라이언트 요청
         * @param response 서버 응답
         * @param chain    필터체인
         * @throws IOException 입출력 예외처리
         * @throws ServletException 서블릿 예외
         */
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String token = extractToken(httpRequest);

            // 토큰 검증 & 인증 객체 설정 부분
            if (token != null && jwtProvider.validateToken(token)) {
                System.out.println(token);
                var authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(httpRequest, httpResponse);
        }

        /**
         * 요청 헤더에서 JWT 토큰을 추출
         * @param request HTTP 요청 객체
         * @return JWT 토큰 문자열 or Null
         */
        private String extractToken(HttpServletRequest request) {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return null;
        }
    }