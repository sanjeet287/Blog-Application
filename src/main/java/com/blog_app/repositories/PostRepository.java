package com.blog_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.Category;
import com.blog_app.entities.Post;
import com.blog_app.entities.User;


public interface PostRepository  extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByKeyword(String keyword);

}
