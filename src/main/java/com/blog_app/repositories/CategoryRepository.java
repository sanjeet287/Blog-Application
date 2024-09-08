package com.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {

}
