package com.blog_app.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog_app.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

		private Integer id;
	@NotNull
	@Size(min = 3,max=40,message="length should not be less than 3 chars !! ")
	private String name;
	
	@Email(message="Enter valid email !!")
	private String email;
	
	@NotEmpty
	@Size(min=4,max=20,message="length should not less than 4 chars and max of 20 chars !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<Role> roles=new HashSet<>();
}
