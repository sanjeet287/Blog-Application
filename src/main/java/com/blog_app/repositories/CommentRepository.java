package com.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
	
	
}
