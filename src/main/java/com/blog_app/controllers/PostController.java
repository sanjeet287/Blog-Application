package com.blog_app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog_app.exceptions.responses.PostResponse;
import com.blog_app.payloads.PostDto;
import com.blog_app.services.ImageService;
import com.blog_app.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping(value="/create/user/{userId}/category/{categoryId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostDto> createPost(@Valid 
			@ModelAttribute PostDto postDto,
			@PathVariable Integer categoryId,
			@PathVariable Integer userId,
			@RequestParam MultipartFile imagefile
			) throws IOException{
		
		 
	            String fileName = this.imageService.uploadImg(path, imagefile);
	            postDto.setImage(fileName); 
	        


		PostDto createdPost = this.postService.createPost(postDto,categoryId,userId);

		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	@PutMapping(value="/update/post/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostDto> updatePost(@Valid @ModelAttribute PostDto postDto, 
			@PathVariable Integer id,
			@RequestParam MultipartFile imagefile
			) throws IOException {
		
		//PostDto post = this.postService.getPost(id);
		String img = this.imageService.uploadImg(path, imagefile);
		postDto.setImage(img);

		PostDto updatePost = this.postService.updatePost(postDto, id);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer id) {

		PostDto postDto = this.postService.getPost(id);

		return new ResponseEntity<PostDto>(postDto, HttpStatus.FOUND);
	}
	

	@GetMapping("/allPosts")
	public ResponseEntity<PostResponse> getAllPost(@RequestBody String sortBy){
		
		  PostResponse allPostDtos = this.postService.getAllPost(sortBy);

		return new ResponseEntity<PostResponse>(allPostDtos, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/post/{id}")
	public void deletePost(@PathVariable Integer id){
		this.postService.deletePost(id);
	}
	
	@GetMapping("/search/{query}")
	public ResponseEntity<List<PostDto>> search(@PathVariable String query) {
		List<PostDto> allPosts = this.postService.findByTitle(query);
		return new ResponseEntity<List<PostDto>>(allPosts,HttpStatus.OK);
	}
	
	@GetMapping("/allPosts/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId,
			@RequestParam(defaultValue = "1",required = false)Integer pageNumber,
			@RequestParam(defaultValue = "10",required = false)Integer pageSize,
			@RequestParam(defaultValue = "id",required = false)String sortBy,
			@RequestParam(defaultValue = "asc",required = false)String sortDir
			){
		
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId,pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
	}
	
	@GetMapping("/allPosts/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId,
			@RequestParam(defaultValue = "1",required = false)Integer pageNumber,
			@RequestParam(defaultValue = "10",required = false)Integer pageSize,
			@RequestParam(defaultValue = "id",required = false)String sortBy,
			@RequestParam(defaultValue = "asc",required = false)String sortDir
			){
		
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
	}
	
	@PostMapping("/upload/post/{postid}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postid,@RequestParam MultipartFile image) throws IOException{
		
		PostDto postDto = this.postService.getPost(postid);
		String img = this.imageService.uploadImg(path, image);
		
		postDto.setImage(img);
		
		PostDto updatePost = this.postService.updatePost(postDto, postid);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}

}
