package com.blog_app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog_app.entities.Category;
import com.blog_app.entities.Post;
import com.blog_app.entities.User;
import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.payloads.PostDto;
import com.blog_app.repositories.CategoryRepository;
import com.blog_app.repositories.PostRepository;
import com.blog_app.repositories.UserRepository;
import com.blog_app.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);

		post.setAddedDate(LocalDateTime.now());
		post.setImage("default.png");
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepository.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());

		post.setAddedDate(LocalDateTime.now());
		post.setImage("default.png");

		Post savedPost = this.postRepository.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto getPost(Integer id) {
		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPosts = this.postRepository.findAll();
		if(allPosts.isEmpty() || allPosts==null) {
			throw new ResourceNotFoundException("Posts");
		}

		List<PostDto> allPostDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return allPostDtos;
	}

	@Override
	public void deletePost(Integer id) {
		this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryid) {
		Category category = this.categoryRepository.findById(categoryid).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryid));
		List<Post> allPosts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = allPosts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userid) {
		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User","Id",userid));
		List<Post> allPosts = this.postRepository.findByUser(user);
		List<PostDto> postDtos = allPosts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.findByKeyword(keyword);	
		posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return null;
	}

}
