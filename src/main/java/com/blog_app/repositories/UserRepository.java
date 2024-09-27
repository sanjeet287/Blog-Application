package com.blog_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User>  findByEmail(String email);

}
