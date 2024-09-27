package com.blog_app.exceptions.responses;

import java.util.List;

import com.blog_app.payloads.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse {
	
	
	private Integer pageNumber;
	private Integer pageSize;
	private List<PostDto>content;
	private Long totalElements;
	private Integer totalSize;
	private boolean lastPage;
}
