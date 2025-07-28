package com.smhrd.board.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.board.entity.BoardEntity;


@Repository
public interface BoardRepository  extends JpaRepository<BoardEntity, Long>{
	
	
	// 내림차순정렬(writeDay기준)
	ArrayList<BoardEntity> findAllByOrderByWriteDayDesc();
	
	
	// 검색조회기능LIKE(내림차순정렬) - title
	List<BoardEntity> findByTitleContaining(String keyword);
	// 검색조회기능LIKE(내림차순정렬) - Writer
	List<BoardEntity> findByWriterContaining(String keyword);
	
	// 커스텀쿼리문 작성
	// 검색조회기능LIKE(내림차순정렬) - Content
	@Query("SELECT b FROM BoardEntity b WHERE b.content LIKE %:keyword% ORDER BY b.writeDay DESC")
	List<BoardEntity> searchContent(@Param("keyword") String keyword);
	
}
