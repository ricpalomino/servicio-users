package com.springboot.app.users;

import com.springboot.app.users.models.dao.PostDao;
import com.springboot.app.users.models.dao.UserDao;
import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.models.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.springboot.app.users.Data.createPost;
import static com.springboot.app.users.Data.createUser;


@SpringBootTest
class ServicioUsersApplicationTests {

	@MockBean
	UserDao userDao;

	@MockBean
	PostDao postDao;

	@BeforeEach
	void setUp() {

	}

	@Test
	void contextLoads() {
		when(userDao.findById(1L)).thenReturn(createUser());

		User user = userDao.findById(1L).get();

		assertEquals("Richard", user.getName());
		assertEquals("Palomino", user.getLastName());

		verify(userDao, times(1)).findById(1L);
	}

	@Test
	void contextLoads2() {
		when(postDao.findById(1L)).thenReturn(createPost());

		Post post = postDao.findById(1L).get();

		assertEquals("Post de Richard", post.getText());

		verify(postDao, times(1)).findById(anyLong());
		verify(postDao, never()).findAll();
	}

}
