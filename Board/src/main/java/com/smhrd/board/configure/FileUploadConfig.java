package com.smhrd.board.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class FileUploadConfig {
	// 업로드될 경로설정
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	

	public String getUploadDir() {
		return uploadDir;
	}
	
} 