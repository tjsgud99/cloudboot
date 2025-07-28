package com.smhrd.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smhrd.board.BoardApplication;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    private final BoardApplication boardApplication;
	
	@Autowired
	private UserService userService;


    UserController(BoardApplication boardApplication) {
        this.boardApplication = boardApplication;
    }
	

	//로그인 기능
	@PostMapping(value = "/login.do")
	public String login(@RequestParam String id,@RequestParam String pw,HttpSession session) 
	{
		UserEntity user = userService.login(id,pw);
		System.out.println(user);
		if(user != null) {
			session.setAttribute("user", user);
			return "redirect:/";
			
		}else {
			return "redirect:/login";
		}
	}

	//로그아웃 기능
	@GetMapping(value = "/logout.do")
	public String logout(HttpSession session)
	{
	    // 세션 무효화
		session.invalidate();
		return "redirect:/";
	}
	
	//회원가입 기능
	@PostMapping(value = "/register.do")
	public String register( 
							@RequestParam String id,@RequestParam String pw,
							@RequestParam String name,@RequestParam int age,
							HttpSession session
						   )
	{
		UserEntity entity = new UserEntity();
		entity.setId(id);
		entity.setPw(pw);
		entity.setName(name);
		entity.setAge(age);
		
		String result = userService.register(entity);
		
		if(result.equals("success")) {
			return "redirect:/login";
		}else {
			return "redirect:/";
		}
	}
	

}
