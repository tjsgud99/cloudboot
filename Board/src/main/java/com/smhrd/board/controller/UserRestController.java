package com.smhrd.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	UserService userService;
	
	@ResponseBody 
	@GetMapping("user/check-id")
	public HashMap<String, Boolean> checkId(@RequestParam String id) {
		boolean hasId = userService.checkId(id);
		
		HashMap<String,Boolean> res = new HashMap<>();
		res.put("hasId",hasId);
		return res;
	}
}
