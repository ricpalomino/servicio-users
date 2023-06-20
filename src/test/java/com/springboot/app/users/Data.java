package com.springboot.app.users;

import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.models.entity.User;

import java.util.Optional;

public class Data {

  public static Optional<User> createUser() {
    return Optional.of(new User(1L, "Richard", "Palomino", "$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK", "997589427"));
  }

  public static Optional<Post> createPost() {
    User user =  new User(1L, "Richard", "Palomino", "$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK", "997589427");
    return Optional.of(new Post(1L, "Post de Richard", user));
  }
}
