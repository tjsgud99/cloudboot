package com.smhrd.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.repository.BoardRepository;

@Service
public class BoardServie {

	@Autowired
	BoardRepository boardRepository;
	
	// 게시글 작성기능
	public BoardEntity write(BoardEntity entity) {
		BoardEntity result = boardRepository.save(entity); 
		return result;
	}
	
	
    // 게시판 전체 조회 기능
    public ArrayList<BoardEntity> selectAll() { 
    	ArrayList<BoardEntity> boardList = (ArrayList<BoardEntity>) boardRepository.findAllByOrderByWriteDayDesc();
    	return boardList;
    }
	
	// bid로 게시글 정보 조회 기능
    public Optional<BoardEntity> getDetail(Long id) {
    	Optional<BoardEntity> details = boardRepository.findById(id);
    	return details ;
    }
	
    // bid로 게시글 삭제 기능
    public void deleteBoard(Long id) {
    	boardRepository.deleteById(id);
    }
    
    // bid로 게시글 수정 기능
    public void updateBoard(Long id) {
    	boardRepository.deleteById(id);
    }
    
    // 검색기능
    public List<BoardEntity> searchResult(String type,String keyword) {
    	
    	List<BoardEntity> list = null; 
    	
    	switch (type) {
		case "title":
			list = boardRepository.findByTitleContaining(keyword);
			break;
		case "content":
			list = boardRepository.searchContent(keyword);
			break;
		case "writer":
			list = boardRepository.findByWriterContaining(keyword);
			break;
		default :
			break;
		}
    	return list;
    	
    }
    
}
