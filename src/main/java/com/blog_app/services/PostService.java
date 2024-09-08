package com.blog_app.services;

import java.util.List;

import com.blog_app.payloads.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
	
	PostDto updatePost(PostDto postDto,Integer id);
	
	PostDto getPost(Integer id);
	
	List<PostDto> getAllPost();
	
	void deletePost(Integer id);
	
	List<PostDto> getPostsByCategory(Integer categoryid);
	
	List<PostDto> getPostsByUser(Integer userid);
	
	List<PostDto> searchPosts(String keyword);
	
	

}
