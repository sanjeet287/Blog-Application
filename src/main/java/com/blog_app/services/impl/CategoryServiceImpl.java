package com.blog_app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog_app.entities.Category;
import com.blog_app.exceptions.ResourceNotFoundException;
import com.blog_app.payloads.CategoryDto;
import com.blog_app.repositories.CategoryRepository;
import com.blog_app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		
		Category category=this.categoryRepository.save(this.modelMapper.map(categoryDto, Category.class));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category savedCategory = this.categoryRepository.save(category);
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id", categoryId));
		
		this.categoryRepository.delete(category);	

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = this.categoryRepository.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream().map(category->this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}
	
	

}
