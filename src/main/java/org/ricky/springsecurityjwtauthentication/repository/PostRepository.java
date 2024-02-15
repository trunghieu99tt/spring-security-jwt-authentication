package org.ricky.springsecurityjwtauthentication.repository;

import org.ricky.springsecurityjwtauthentication.model.Post;
import org.springframework.data.repository.ListCrudRepository;

public interface PostRepository extends ListCrudRepository<Post, Long> {
}
