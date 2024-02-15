package org.ricky.springsecurityjwtauthentication.controller;

import lombok.RequiredArgsConstructor;
import org.ricky.springsecurityjwtauthentication.model.Post;
import org.ricky.springsecurityjwtauthentication.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @GetMapping
  public ResponseEntity<List<Post>> findAll() {
    return ResponseEntity.ok(postService.findAll());
  }
}
