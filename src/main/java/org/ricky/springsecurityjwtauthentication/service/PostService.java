package org.ricky.springsecurityjwtauthentication.service;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.model.Post;
import org.ricky.springsecurityjwtauthentication.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;

  public List<Post> findAll() {
    return postRepository.findAll();
  }
}
