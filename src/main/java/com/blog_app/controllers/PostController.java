package com.blog_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_app.payloads.PostDto;
import com.blog_app.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/create/post/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid 
			@RequestBody PostDto postDto,
			@PathVariable Integer categoryId,
			@PathVariable Integer userId) {

		PostDto createdPost = this.postService.createPost(postDto,categoryId,userId);

		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	@PutMapping("/update/post/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer id) {

		PostDto updatePost = this.postService.updatePost(postDto, id);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer id) {

		PostDto postDto = this.postService.getPost(id);

		return new ResponseEntity<PostDto>(postDto, HttpStatus.FOUND);
	}
	

	@GetMapping("/allPosts")
	public ResponseEntity<List<PostDto>> getAllPost() {

		 List<PostDto> allPostDtos = this.postService.getAllPost();

		return new ResponseEntity<List<PostDto>>(allPostDtos, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/post/{id}")
	public void deletePost(@PathVariable Integer id){
		this.postService.deletePost(id);
	}

}
