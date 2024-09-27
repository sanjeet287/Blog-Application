package com.blog_app.services;

import java.util.List;
import com.blog_app.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userdto);

	UserDto createUser(UserDto userdto);

	UserDto updateUser(UserDto userdto, Integer id);

	UserDto getUser(Integer id);

	List<UserDto> getAllUsers();

	void deleteUser(Integer id);

}
