package org.ricky.springsecurityjwtauthentication.configuration;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.jwt.JwtAccessDeniedHandler;
import org.ricky.springsecurityjwtauthentication.jwt.JwtAuthenticationEntryPoint;
import org.ricky.springsecurityjwtauthentication.jwt.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class WebSecurityConfiguration {
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers("/",
            "/*.html",
            "/favicon.ico",
            "/*/*.html",
            "/*/*.css",
            "/*/*.js",
            "/h2-console/**",
            "/swagger-ui/**");
  }

  @Bean
  @Order(1)
  public SecurityFilterChain filterChainAuth(HttpSecurity http) throws Exception {
    http
        .securityMatchers(
            (securityMatcher) -> securityMatcher
                .requestMatchers(
                    "/api/auth/**")
        )
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        )
        .authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
        );
    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .securityMatchers(
            (securityMatcher) -> securityMatcher
                .requestMatchers(
                    "/api/posts/**")
        )
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        )
        .authorizeHttpRequests(
            (authorize) -> authorize
                .anyRequest()
                .authenticated()
        )
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );
    return http.build();
  }

  @Bean
  public FilterRegistrationBean<JwtAuthenticationFilter> tenantFilterRegistration(
      JwtAuthenticationFilter jwtAuthenticationFilter
  ) {
    FilterRegistrationBean<JwtAuthenticationFilter> registration =
        new FilterRegistrationBean<>(jwtAuthenticationFilter);
    registration.setEnabled(false);
    return registration;
  }
}
