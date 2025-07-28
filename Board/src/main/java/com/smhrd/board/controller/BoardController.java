package com.smhrd.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.board.configure.FileUploadConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.BoardServie;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	BoardServie boardService;
	@Autowired
	FileUploadConfig fileUploadConfig;
	
	//----------------------------------------------------------------------------------------------	
	// 게시글 작성 기능 
	@PostMapping(value = "/write")
	public String writer(
						@RequestParam String title,@RequestParam String content,
						@RequestParam MultipartFile image, HttpSession session			
						) 
	{
		// 파라미터 설정 및 초기화
		BoardEntity board = new BoardEntity();
		LocalDate writeDay = LocalDate.now();
		UserEntity user = (UserEntity) session.getAttribute("user");
		String id = user.getId();
		String imgPath = "";
		String uploadDir = fileUploadConfig.getUploadDir();
		
		System.out.println("uploadDir:"+uploadDir);

		if(!image.isEmpty()) {
			// 1. 파일이름설정
			String fileName = UUID.randomUUID()+"_"+image.getOriginalFilename();
			
			// 2. 파일 경로설정
			String filePath = Paths.get(uploadDir, fileName).toString();
			
			// 3. 서버에 이미지 저장
			try {
				image.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 4. DB경로 문자열 설정
			imgPath = "/uploads/"+fileName;
		}
		
		
		// 파라미터 세팅
		board.setTitle(title);
		board.setContent(content);
		board.setWriter(id);
		board.setImgPath(imgPath);
		board.setWriteDay(writeDay);
				
		boardService.write(board);
		return "redirect:/";
	}
	
	//----------------------------------------------------------------------------------------------	
	// 게시글 뷰 페이지
	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable Long id,Model model) {
		Optional<BoardEntity> detail = boardService.getDetail(id);
		model.addAttribute("detail",detail.get());
		return "detail";
	}
	

	//----------------------------------------------------------------------------------------------	
	// 게시글 수정 페이지
	@GetMapping(value = "/edit/{id}")
	public String editPage(@PathVariable Long id, Model model){
	    Optional<BoardEntity> boardOptional = boardService.getDetail(id); 
	    
	    if (boardOptional.isPresent()) {
	        BoardEntity board = boardOptional.get(); 
	        model.addAttribute("board", board); 
	    } else {
	        // ID에 해당하는 게시글이 없는 경우의 처리
	        System.out.println("Board with ID " + id + " not found.");
	        return "redirect:/board/list"; 
	    }
	    
	    return "edit"; // edit.html 템플릿 렌더링
	}
	
	
	//----------------------------------------------------------------------------------------------	
	// 게시글 수정 기능
	@PostMapping(value = "/update")
	public String update(
						@RequestParam String title,@RequestParam String content,
						@RequestParam String oldImgPath,@RequestParam Long id,
						@RequestParam MultipartFile image		
						)throws Exception
	{
		
		// DB내부 정보 가져오기
		BoardEntity entity = boardService.getDetail(id).get();
		
		
		// 기존 파일 처리
		String uploadDir = fileUploadConfig.getUploadDir();
		// 기존이미지 변경시 활성		
		if(!image.isEmpty()) {
			if(oldImgPath != null && !oldImgPath.isEmpty()) {
				Path oldFilePath = Paths.get(uploadDir,oldImgPath.replace("/uploads/", ""));
				try {
					Files.deleteIfExists(oldFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 새로운 이미지 저장
			String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
			Path newFilePath = Paths.get(uploadDir,newFileName);
			try {
				image.transferTo(newFilePath);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}// End (try & catch)
			
			entity.setImgPath("/uploads/"+newFileName);
			
		}// End (if)
		
		
		entity.setTitle(title);
		entity.setContent(content);
		
		// update문 쿼리실행
		boardService.write(entity);
		
		return "redirect:/board/detail/"+id;
	}
	//----------------------------------------------------------------------------------------------	
	
	
	
}
