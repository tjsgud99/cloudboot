package com.smhrd.board.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.configure.FileUploadConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.service.BoardServie;

@RestController
public class BoardRestController {

	@Autowired
	BoardServie boardService;
	@Autowired
	FileUploadConfig fileUploadConfig;
	
	// 로컬서버 파일 이름 삭제(비동기)
	@DeleteMapping("/delete/{id}")
	public void deleteBoard(@PathVariable Long id) {
		// 로컬서버에서 실제 이미지 삭제
		String imgPath = boardService.getDetail(id).get().getImgPath();
		String uploadDir = fileUploadConfig.getUploadDir();
		
		if(imgPath != null && !imgPath.isEmpty()) {
			Path filePath = Paths.get(uploadDir, imgPath.replace("/uploads/", "")); 
			try {
				Files.deleteIfExists(filePath);
				System.out.println("imgPath : "+imgPath);
				System.out.println("uploadDir : "+uploadDir);
				System.out.println("filePath : "+filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		// DB값에서 삭제
		boardService.deleteBoard(id);
	}
	
	
	//검색기능
	@GetMapping("/search")
	public List<BoardEntity> search(@RequestParam String type, @RequestParam String keyword) {
		return boardService.searchResult(type, keyword);
	}
	
}
