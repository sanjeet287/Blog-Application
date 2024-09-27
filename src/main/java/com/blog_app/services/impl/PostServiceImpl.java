package com.blog_app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog_app.constants.AppConstants;
import com.blog_app.entities.Category;
import com.blog_app.entities.Post;
import com.blog_app.entities.User;
import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.exceptions.responses.PostResponse;
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
		if(postDto.getImage()!=null || !postDto.getImage().isEmpty()) {
		post.setImage(postDto.getImage());
		}else {
			post.setImage("default.png");
		}
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepository.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		 if (postDto.getTitle() != null && !postDto.getTitle().trim().isEmpty()) {
		        post.setTitle(postDto.getTitle());
		    }
		  if (postDto.getContent() != null && !postDto.getContent().trim().isEmpty()) {
		        post.setContent(postDto.getContent());
		    }

		post.setAddedDate(LocalDateTime.now());
		post.setImage(postDto.getImage());

		Post savedPost = this.postRepository.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto getPost(Integer id) {
		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase(AppConstants.SORT_DIR_ASC))?Sort.by(AppConstants.SORT_DIR_ASC).ascending():Sort.by(AppConstants.SORT_DIR_ASC).descending();
		
		Pageable page=PageRequest.of(AppConstants.PAGE_NUMBER,AppConstants.PAGE_SIZE,sort);
		
		 Page<Post> pagePost = this.postRepository.findAll(page);
		 List<Post> allPosts = pagePost.getContent();
		
		 if(allPosts.isEmpty() || allPosts==null) {
			throw new ResourceNotFoundException("Posts");
		}

		List<PostDto> allPostDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		
		postResponse.setContent(allPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalSize(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public void deletePost(Integer id) {
		this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryid,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Category category = this.categoryRepository.findById(categoryid).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryid));
		List<Post> allPosts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = allPosts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userid,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User","Id",userid));
		List<Post> allPosts = this.postRepository.findByUser(user);
		List<PostDto> postDtos = allPosts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> findByTitle(String title) {
		List<Post> posts = this.postRepository.searchByTitle(title);	
		List<PostDto> searchedPost = posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return searchedPost;
	}

}
