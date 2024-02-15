package org.ricky.springsecurityjwtauthentication.dto;

import lombok.*;
import org.ricky.springsecurityjwtauthentication.model.AppUser;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
  private String accessToken;

  private String refreshToken;

  private AppUser appUser;
}
