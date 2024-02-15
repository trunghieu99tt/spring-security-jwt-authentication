package org.ricky.springsecurityjwtauthentication.service;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.model.AppUser;
import org.ricky.springsecurityjwtauthentication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public Optional<AppUser> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void save(AppUser appUser) {
    userRepository.save(appUser);
  }
}
