package com.blog_app.exceptions.responses;

import java.util.Date;

import lombok.Data;

@Data
public class JwtAuthResponse {

	
	private boolean success;
	
	private String token;
	
	private Date createdIn;
	
	private Date expiresIn;
	
	
}
