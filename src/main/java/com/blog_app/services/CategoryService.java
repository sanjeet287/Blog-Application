package com.blog_app.services;

import java.util.List;

import com.blog_app.payloads.CategoryDto;

public interface CategoryService {
	
	//create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update  category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete category
	void deleteCategory(Integer categoryId);
	
	//fetch  single category 
	CategoryDto getCategory(Integer categoryId);
	
	//fetch all category
	List<CategoryDto> getAllCategories();

}
