package com.blog_app.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_app.exceptions.AuthApiException;
import com.blog_app.exceptions.responses.JwtAuthResponse;
import com.blog_app.payloads.LoginDto;
import com.blog_app.payloads.UserDto;
import com.blog_app.security.JwtTokenHelper;
import com.blog_app.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody LoginDto request) {

		this.authenticate(request.getUsername(), request.getPassword());

		// UserDetails userDetails =
		// this.userDetailsService.loadUserByUsername(request.getUsername());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setSuccess(true);
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setCreatedIn(new Date(System.currentTimeMillis()));
		jwtAuthResponse.setExpiresIn(this.jwtTokenHelper.getExpirationDateFromToken(token));

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.CREATED);

	}

	public void authenticate(String username, String password) {

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

			this.authenticationManager.authenticate(token);

		} catch (BadCredentialsException e) {
			throw new AuthApiException("username or password are not correct!");
		}

	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}

}
