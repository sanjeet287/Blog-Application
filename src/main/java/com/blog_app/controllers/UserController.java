package com.blog_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_app.exceptions.responses.APIResponse;
import com.blog_app.payloads.UserDto;
import com.blog_app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST create user
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userdto) {
		UserDto createdUser = this.userService.createUser(userdto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// PUT update user
	@PutMapping("/update/user/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto,@PathVariable("userid") Integer id) {
		UserDto updateUser = this.userService.updateUser(userdto, id);
		return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
	}

	// Delete delete user
	//only admin can delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userid}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable("userid") Integer id) {
		this.userService.deleteUser(id);
		return new ResponseEntity<APIResponse>((HttpStatusCode) new APIResponse("User deleted successfully!!",true));
	}

	// GET fetch all users
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	// GET fetch single user
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userid") Integer id) {
		UserDto userDto = this.userService.getUser(id);
		return new ResponseEntity<>(userDto, HttpStatus.FOUND);
	}

}
