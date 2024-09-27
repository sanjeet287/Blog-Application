package com.blog_app.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {
	
	private Integer id;

	@Size(min=10, max=200)
	private String content;

	@Size(min = 4, max = 100)
	private String title;
	
	@JsonIgnore
	@Transient
	private MultipartFile imagefile;
	
	private String image;
	
	private LocalDateTime addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comment=new HashSet<>();

}
