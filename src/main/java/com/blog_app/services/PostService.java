package com.blog_app.services;

import java.util.List;

import com.blog_app.exceptions.responses.PostResponse;
import com.blog_app.payloads.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
	
	PostDto updatePost(PostDto postDto,Integer id);
	
	PostDto getPost(Integer id);
	
	PostResponse getAllPost(String sortBy);
	
	void deletePost(Integer id);
	
	List<PostDto> getPostsByCategory(Integer categoryid,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	List<PostDto> getPostsByUser(Integer userid,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	List<PostDto> findByTitle(String keyword);
	
	

}
