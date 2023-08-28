package board.service;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.dto.Board;
import board.dto.Reply;
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
	
	// 조회수 증가 로직
	@Transactional
	public int boardViewsCount(int boardId) {
	    return boardMapper.boardViewCount(boardId);
	}
	
	public HashMap<String, Object> commentPost(int boardId, String contents, int cDepth, Long cGroup, String username) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        
        boardMapper.reply(username, boardId, contents, cDepth, cGroup);

        map.put("result","success");
        
        return map;
    }
}
