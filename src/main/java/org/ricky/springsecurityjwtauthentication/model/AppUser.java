package org.ricky.springsecurityjwtauthentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
  @Id
  private Long id;
  private String username;
  private String password;
  private String name;
}
