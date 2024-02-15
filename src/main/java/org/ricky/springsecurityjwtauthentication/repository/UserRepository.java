package org.ricky.springsecurityjwtauthentication.repository;

import org.ricky.springsecurityjwtauthentication.model.AppUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(String username);
}
