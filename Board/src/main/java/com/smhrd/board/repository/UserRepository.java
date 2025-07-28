package com.smhrd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.board.entity.UserEntity;


@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long>{
	
	
	// 아이디가 DB에 있는지 체크하는 기능 (커스텀SQL코드)
	boolean existsById(String id);
	
	// 로그인용 조회기능
	UserEntity findByIdAndPw(String id,String pw);
	
}
