package com.springboot.app.users;

import com.springboot.app.users.models.dao.PostDao;
import com.springboot.app.users.models.dao.UserDao;
import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.models.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostIntegrationJpaTest {

  @Autowired
  PostDao postDao;

  @Autowired
  UserDao userDao;

  @Test
  void testFindById() {
    Optional<Post> post = postDao.findById(1L);
    assertTrue(post.isPresent());
    assertEquals("post 1", post.orElseThrow().getText());
  }

  @Test
  void testFindAll() {
    List<Post> posts = postDao.findAll();
    assertFalse(posts.isEmpty());
    assertEquals(1, posts.size());
  }

  @Test
  void testSave() {
    Post post = new Post(null, "Post 2", userDao.findById(1L).orElseThrow());
    Post postSaved = postDao.save(post);
    assertEquals("Post 2", postSaved.getText());
  }

  @Test
  void testUpdate() {
    User user = userDao.findById(1L).orElseThrow();
    Post post = new Post(null, "Post 3", user);
    Post postSaved = postDao.save(post);
    assertEquals("Post 3", postSaved.getText());
    postSaved.setText("Post 3" + "-update");
    Post postUpdated = postDao.save(postSaved);
    assertEquals("Post 3-update", postUpdated.getText());
  }

  @Test
  void testDelete() {
    Post post = postDao.findById(1L).orElseThrow();
    postDao.delete(post);

    assertThrows(NoSuchElementException.class, () -> {
      postDao.findById(1L).orElseThrow();
    });

  }
}
