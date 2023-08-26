package board.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.dto.Board;
import board.mapper.BoardMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	public Board detailBoard(int boardId) {
		
		Board detailBoard = boardMapper.detailBoard(boardId);
		
		return detailBoard;
	}
	
	// 조회수 증가 로직
	@Transactional
	public int boardViewsCount(int boardId) {
	    return boardMapper.boardViewCount(boardId);
	}
}
