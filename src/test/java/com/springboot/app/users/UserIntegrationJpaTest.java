package com.springboot.app.users;

import com.springboot.app.users.models.dao.UserDao;
import com.springboot.app.users.models.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserIntegrationJpaTest {

  @Autowired
  UserDao userDao;

  @Test
  void testFindById() {
    Optional<User> user = userDao.findById(1L);
    assertTrue(user.isPresent());
    assertEquals("Richard", user.orElseThrow().getName());
  }

  @Test
  void testFindAll() {
    List<User> users = userDao.findAll();
    assertFalse(users.isEmpty());
    assertEquals(1, users.size());
  }

  @Test
  void testSave() {
    User user = new User(null, "Luis", "Felix", "12345", "98789878");
    User userSaved = userDao.save(user);
    assertEquals("Felix", userSaved.getLastName());
    assertEquals("Luis", userSaved.getName());
  }

  @Test
  void testUpdate() {
    User user = new User(null, "Pepe", "Palomino", "12345", "98789878");
    User userSaved = userDao.save(user);
    assertEquals("Palomino", userSaved.getLastName());
    assertEquals("Pepe", userSaved.getName());
    userSaved.setLastName("Palomino" + "-update");
    User userUpdated = userDao.save(userSaved);
    assertEquals("Palomino-update", userUpdated.getLastName());
  }

  @Test
  void testDelete() {
    User user = userDao.findById(1L).orElseThrow();
    userDao.delete(user);

    assertThrows(NoSuchElementException.class, () -> {
      userDao.findById(1L).orElseThrow();
    });

  }
}
