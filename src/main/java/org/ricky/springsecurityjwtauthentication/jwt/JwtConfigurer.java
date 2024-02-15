package org.ricky.springsecurityjwtauthentication.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtProvider jwtProvider;

  @Override
  public void init(HttpSecurity builder) {

  }

  @Override
  public void configure(HttpSecurity builder) {
    JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtProvider);
    builder.addFilterBefore(
        customFilter,
        UsernamePasswordAuthenticationFilter.class
    );
  }
}
