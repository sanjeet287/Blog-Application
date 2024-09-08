package com.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
