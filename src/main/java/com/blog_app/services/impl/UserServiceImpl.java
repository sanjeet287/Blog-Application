package com.blog_app.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog_app.constants.AppConstants;
import com.blog_app.entities.Role;
import com.blog_app.entities.User;
import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.payloads.UserDto;
import com.blog_app.repositories.RoleRepository;
import com.blog_app.repositories.UserRepository;
import com.blog_app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user = this.dtoToUser(userdto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setName(userdto.getName());
		user.setAbout(userdto.getAbout());

		User updatedUser = this.userRepository.save(user);
		UserDto userDto = this.userToDto(updatedUser);
		return userDto;
	}

	@Override
	public UserDto getUser(Integer id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> UserDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return UserDto;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		this.userRepository.delete(user);

	}

	public User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	public UserDto userToDto(User user) {

		UserDto userdto = this.modelMapper.map(user, UserDto.class);
		return userdto;
	}

	@Override
	public UserDto registerNewUser(UserDto userdto) {
		User user = this.modelMapper.map(userdto,User.class);
		
		//encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//set role
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User saveUser = this.userRepository.save(user);
		
		return this.modelMapper.map(saveUser, UserDto.class);
	}

}
