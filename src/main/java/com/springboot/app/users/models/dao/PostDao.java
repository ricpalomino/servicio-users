package com.springboot.app.users.models.dao;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.users.models.entity.Post;

public interface PostDao extends JpaRepository<Post, Long> {
	
	List<Post> findByUserId(Long userId);

	@Transactional
	void deleteByUserId(long userId);
}
