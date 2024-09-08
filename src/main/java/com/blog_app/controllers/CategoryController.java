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

import com.blog_app.payloads.CategoryDto;
import com.blog_app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// POST - create category

	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto categoryDto2 = this.categoryService.createCategory(categoryDto);

		return new ResponseEntity<>(categoryDto2, HttpStatus.CREATED);
	}

	@PutMapping("/update/category/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer id) {

		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, id);

		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id) {

		CategoryDto categoryDto = this.categoryService.getCategory(id);

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.FOUND);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {

		 List<CategoryDto> allCategories = this.categoryService.getAllCategories();

		return new ResponseEntity<List<CategoryDto>>(allCategories, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Integer id) {
		
		this.categoryService.deleteCategory(id);
	}

}
