package com.blog_app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id	
	private Integer id;
	
	private String name;
}
