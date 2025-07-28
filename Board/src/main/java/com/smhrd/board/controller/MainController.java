package com.smhrd.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.service.BoardServie;


@Controller
public class MainController {

	
	@Autowired
	BoardServie boardService;

	
	// 인덱스 페이지 이동	
	@GetMapping(value = "/")
	public String indexPage(Model model) {
		
		ArrayList<BoardEntity> boardList = boardService.selectAll();
		model.addAttribute("boardList",boardList);
		
		return "index";
	}
	
	
	// 로그인 페이지 이동	
	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
	

	
	// 회원가입 페이지 이동	
	@GetMapping(value = "/register")
	public String registerPage() {
		return "register";
	}
	
	
	// 글쓰기 페이지 이동	
	@GetMapping(value = "/write")
	public String writePage() {
		return "write";
	}
	
	
	
	
	
	
}
