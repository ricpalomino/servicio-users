package com.springboot.app.users;

import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.springboot.app.users.models.entity.Post;
import com.springboot.app.users.models.entity.User;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(User.class, Post.class);
	}


    @Bean
    Jackson2ObjectMapperBuilder jacksonBuilder() {
	    Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
	    b.timeZone(TimeZone.getTimeZone("UTC"));
	    return b;
	}
}
