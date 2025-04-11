package com.team2.TodoList.common.security.jwt;

import com.team2.TodoList.user.security.CustomUserDetail;
import com.team2.TodoList.user.security.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String token = request.getHeader("Authorization");

        // 클라이언트가 Authorization 헤더에 "Bearer <토큰>" 형식으로 보내지 않았거나
        // 아예 헤더가 없으면 필터에서 토큰 검증을 하지 않고 다음 필터로 넘어가게 함
        // -> 인증이 필요한 요청만 해당 필터가 적용되며, 인증이 필요 없는 요청에는 필터가 넘어가도록 하기 위해
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        try {
            String userId = jwtProvider.validateAndGetUserId(token);
            Long id = Long.parseLong(userId);

            UserDetails userDetails = userDetailsService.loadUserById(id);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
