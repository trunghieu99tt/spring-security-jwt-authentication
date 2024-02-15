package org.ricky.springsecurityjwtauthentication.controller;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.dto.AuthResponse;
import org.ricky.springsecurityjwtauthentication.dto.LoginInput;
import org.ricky.springsecurityjwtauthentication.dto.SignUpInput;
import org.ricky.springsecurityjwtauthentication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(
      @RequestBody LoginInput loginInput
  ) {
    return ResponseEntity.ok(authService.login(loginInput));
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(
      @RequestBody SignUpInput signUpInput
  ) {
    return ResponseEntity.ok(authService.signUp(signUpInput));
  }
}
