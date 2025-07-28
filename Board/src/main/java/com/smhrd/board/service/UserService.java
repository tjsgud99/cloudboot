package com.smhrd.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.repository.UserRepository;

// 비지니스 로직 구현 공간

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	// 회원가입 기능
	public String register(UserEntity entity) {
		UserEntity user = userRepository.save(entity);
		
		if(user != null) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	// ID중복체크 기능
	public boolean checkId(String id) {
		boolean hasId = userRepository.existsById(id);
		return hasId;
	}

	// 로그인 기능
	public UserEntity login(String id,String pw) {
		UserEntity user = userRepository.findByIdAndPw(id,pw);
		return user;
	}
	
}
