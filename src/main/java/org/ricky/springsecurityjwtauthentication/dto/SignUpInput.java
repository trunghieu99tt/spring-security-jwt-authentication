package org.ricky.springsecurityjwtauthentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpInput {
  private String username;
  private String password;
  private String name;
}
