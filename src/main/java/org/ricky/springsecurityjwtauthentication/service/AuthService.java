package org.ricky.springsecurityjwtauthentication.service;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.dto.AuthResponse;
import org.ricky.springsecurityjwtauthentication.dto.LoginInput;
import org.ricky.springsecurityjwtauthentication.dto.SignUpInput;
import org.ricky.springsecurityjwtauthentication.jwt.JwtProvider;
import org.ricky.springsecurityjwtauthentication.model.AppUser;
import org.ricky.springsecurityjwtauthentication.model.JwtToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtProvider jwtProvider;
  private final AtomicLong userId = new AtomicLong(1);

  @Transactional
  public AuthResponse login(LoginInput loginInput) {
    final Optional<AppUser> userOpt = userService.findByUsername(loginInput.getUsername());
    if (userOpt.isEmpty()) {
      throw new RuntimeException("User not found");
    }
    final JwtToken jwtTokens = jwtProvider.createTokens(loginInput.getUsername());
    return AuthResponse.builder()
        .accessToken(jwtTokens.accessToken())
        .refreshToken(jwtTokens.refreshToken())
        .appUser(userOpt.get())
        .build();
  }

  @Transactional
  public AuthResponse signUp(SignUpInput signUpInput) {
    final AppUser appUser = new AppUser(
        userId.getAndIncrement(),
        signUpInput.getUsername(),
        signUpInput.getPassword(),
        signUpInput.getName()
    );
    final JwtToken jwtTokens = jwtProvider.createTokens(signUpInput.getUsername());
    userService.save(appUser);
    return AuthResponse.builder()
        .accessToken(jwtTokens.accessToken())
        .refreshToken(jwtTokens.refreshToken())
        .appUser(appUser)
        .build();
  }
}
