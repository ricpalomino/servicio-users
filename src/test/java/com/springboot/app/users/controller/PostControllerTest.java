package com.springboot.app.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.app.users.Data;
import com.springboot.app.users.models.dao.PostDao;
import com.springboot.app.users.models.dao.UserDao;
import com.springboot.app.users.models.entity.Post;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
class PostControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PostDao postDao;
  
  @MockBean
  private UserDao userDao;

  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testGetAllPostsByUserId() throws Exception {
    when(userDao.existsById(1L)).thenReturn(true);
    List<Post> list = new ArrayList<>();
    list.add(Data.createPost().orElseThrow());
    when(postDao.findByUserId(1L)).thenReturn(list);
    mvc.perform(get("/users/1/posts").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].text").value("Post de Richard"));
    verify(postDao).findByUserId(1L);
  }

  @Test
  void testCreatePostByUser() throws Exception {
    when(userDao.findById(any())).thenReturn(Data.createUser());
    Post postRequest = new Post();
    postRequest.setText("Post 2");
    postRequest.setCreateAt(new Date());
    when(postDao.save(any())).thenReturn(postRequest);
    mvc.perform(post("/users/1/post")
      .contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(postRequest)))
    .andExpect(status().isCreated())
    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    .andExpect(jsonPath("$.text").value("Post 2"));
  }
}