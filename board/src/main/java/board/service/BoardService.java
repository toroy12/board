package board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import board.dto.Board;
import board.mapper.BoardMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	public Board detailBoard(int boardId) {
		
		Board detailBoard = boardMapper.detailBoard(boardId);
		
		return detailBoard;
	}
}
