package org.ricky.springsecurityjwtauthentication.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.ricky.springsecurityjwtauthentication.model.JwtToken;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {
  private static final String AUTHORITIES_KEY = "authorities";


  @Value("${app.jwt.base64-secret}")
  private String base64Secret;

  @Value("${app.jwt.ttl.access-token}")
  private long accessTokenTtl;

  @Value("${app.jwt.ttl.refresh-token}")
  private long refreshTokenTtl;

  private SecretKey key;

  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public JwtToken createTokens(
      String username
  ) {
    final long now = Instant.now().toEpochMilli();
    final Date accessTokenValidity = new Date(now + this.accessTokenTtl);
    final Date refreshTokenValidity = new Date(now + this.refreshTokenTtl);
    return new JwtToken(
        createTokens(username, accessTokenValidity),
        createTokens(username, refreshTokenValidity)
    );
  }

  private String createTokens(
      Authentication authentication,
      Date validity,
      String authorities
  ) {
    return Jwts.builder()
        .subject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key)
        .expiration(validity)
        .compact();
  }

  private String createTokens(
      String username,
      Date validity
  ) {
    return Jwts.builder()
        .subject(username)
        .claim(AUTHORITIES_KEY, Collections.emptyList())
        .signWith(key)
        .expiration(validity)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    final Claims claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    final Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(
                claims.get(AUTHORITIES_KEY).toString()
                    .split(",")
            )
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    final User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: %s", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace: ", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("UnSupported JWT token trace: %s", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: %s", e);
    }
    return false;
  }
}
