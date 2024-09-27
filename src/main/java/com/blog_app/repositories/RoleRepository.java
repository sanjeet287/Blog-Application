package com.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog_app.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{

}
