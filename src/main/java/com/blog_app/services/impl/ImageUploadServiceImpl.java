package com.blog_app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog_app.services.ImageService;

@Service
public class ImageUploadServiceImpl implements ImageService {

	@Override
	public String uploadImg(String path, MultipartFile file) throws IOException {
		
		 if (file.isEmpty()) {
	            throw new IOException("Failed to upload empty file.");
	        }

	        String originalFilename = file.getOriginalFilename();
	        if (originalFilename == null) {
	            throw new IOException("Failed to get original file name.");
	        }

	        // Generate a unique file name
	        String randomId = UUID.randomUUID().toString();
	        String fileName=randomId.
	        		concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

//	        // Normalize the directory path and construct the full file path
//	        Path directoryPath = Paths.get(path).normalize();
//	        Path filePath = directoryPath.resolve(uniqueFileName);
	        String filePath=path+File.separator+originalFilename;

	        // Create the directory if it does not exist
	        File directory = new File(path);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Copy the file to the destination
	        Files.copy(file.getInputStream(), Paths.get(filePath));

	        // Return the unique file name
	        return fileName;

	}

	@Override
	public InputStream serveImage(String path, String fileName) throws FileNotFoundException {
		String filePath=path+File.separator+fileName;
		InputStream is=new FileInputStream(filePath);
		return is;
	}

}
