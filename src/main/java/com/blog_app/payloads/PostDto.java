package com.blog_app.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

	@NotEmpty
	private String content;

	@Size(min = 4, max = 100)
	private String title;
	
	private String image;
	
	private LocalDateTime addedDate;
	
	private CategoryDto category;
	
	private UserDto user;

}
