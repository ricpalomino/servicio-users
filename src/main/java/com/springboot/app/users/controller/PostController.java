package com.springboot.app.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.users.models.dao.PostDao;
import com.springboot.app.users.models.dao.UserDao;
import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.exception.*;

@RestController
@RequestMapping("/")
public class PostController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PostDao postDao;

	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<Post>> getAllPostsByUserId(@PathVariable(value = "userId") Long userId) {
		if (!userDao.existsById(userId)) {
			throw new ResourceNotFoundException("Not found User with id = " + userId);
		}

		List<Post> posts = postDao.findByUserId(userId);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long id) {
		Post post = postDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Post with id = " + id));

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PostMapping("/users/{userId}/post")
	public ResponseEntity<Post> createPost(@PathVariable(value = "userId") Long userId, @RequestBody Post postRequest) {
		Post post = userDao.findById(userId).map(user -> {
			postRequest.setUser(user);
			return postDao.save(postRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

		return new ResponseEntity<>(post, HttpStatus.CREATED);
	}

	@PutMapping("/post/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post postRequest) {
		Post post = postDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + "not found"));

		post.setText(postRequest.getText());

		return new ResponseEntity<>(postDao.save(post), HttpStatus.OK);
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
		postDao.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/users/{userId}/posts")
	public ResponseEntity<List<Post>> deleteAllPostOfUser(
			@PathVariable(value = "userId") Long userId) {
		if (!userDao.existsById(userId)) {
			throw new ResourceNotFoundException("Not found User with id = " + userId);
		}

		postDao.deleteByUserId(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
