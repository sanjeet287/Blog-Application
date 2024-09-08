package com.blog_app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
	
	
		
	@NotEmpty
	@Size(min=2,max=20,message="title should not blank!!")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=10,max=100,message="description  should not be empty !!")
	private String categoryDescription;
	
	

}
