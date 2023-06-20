package com.springboot.app.users.controller;

import com.springboot.app.users.Data;
import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.models.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostControllerWebTestClientTest {

  @Autowired
  private WebTestClient client;

  @Test
  void testCreatePostByUserId() {
	User user = Data.createUser().orElseThrow();
	client.post().uri("http://localhost:8080/users")
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(user)
    .exchange()
    .expectStatus().isCreated()
    .expectBody()
    .jsonPath("$.lastName").isNotEmpty()
    .jsonPath("$.lastName").value(is(user.getLastName()));
	  
	  
    Post post = Data.createPost().orElseThrow();
    client.post().uri("http://localhost:8080/users/1/post")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(post)
      .exchange()
      .expectStatus().isCreated()
      .expectBody()
      .jsonPath("$.text").isNotEmpty()
      .jsonPath("$.text").value(is(post.getText()));
  }
}