package com.springboot.app.users.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.app.users.models.entity.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserDao extends JpaRepository<User, Long> {
			
	@RestResource(exported = false)
	public void deleteById(Long id);

}
