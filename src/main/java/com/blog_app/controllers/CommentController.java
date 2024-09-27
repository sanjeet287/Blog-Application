package com.blog_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_app.payloads.CommentDto;
import com.blog_app.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/create/post/{post_id}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer post_id){
		CommentDto commentDto2 = this.commentService.createComment(commentDto,post_id);
		return new ResponseEntity<CommentDto>(commentDto2,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/comments/{comm_id}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer comm_id){
		this.commentService.deleteComment(comm_id);
		
		return new ResponseEntity<String>("Comment deleted Successfully!",HttpStatus.MOVED_PERMANENTLY);
	}

}
