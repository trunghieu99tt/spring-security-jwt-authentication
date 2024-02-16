package org.ricky.springsecurityjwtauthentication.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  public static final String AUTHORIZATION_HEADER = "Authorization";

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    System.out.println("JwtAuthenticationFilter doFilterInternal is called");
    String jwt = resolveToken(request);
    String requestURI = request.getRequestURI();
    if (StringUtils.hasText(jwt)) {
      final boolean isTokenValid = jwtProvider.validateToken(jwt);
      if (isTokenValid) {
        final Authentication authentication = jwtProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug(
            "set Authentication to security context for '{}', uri: {}",
            authentication.getName(), requestURI
        );
      } else {
        log.debug("Invalid token for uri: {}", requestURI);
      }
    }
    filterChain.doFilter(request, response);
  }


  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
