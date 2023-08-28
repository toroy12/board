package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.Board;
import board.dto.Category;
import board.dto.Reply;

@Mapper
public interface BoardMapper {
	
	List<Board> boardList(String category, int startIndex, int pageSize);
	
	int boardListCnt();
	
	int boardListByCategoryCnt(String category);
	
	int boardListBySearch(String category, String searchKey, String searchValue);

	List<Board> search(String category, String searchKey, String searchValue, int startIndex, int pageSize);
	
	Board detailBoard(int boardId);
	
	List<Category> category();
	
	int write(Board board);
	
	Board modifyList(int boardId);
	
	int modify(Board board);
	
	int delete(int boardId);
	
	int boardViewCount(int boardId);
	
	int reply(String email, int boardId, String contents, int cDepth, Long cGroup);
	
	List<Reply> showReply(int boardId);
	
	int countReply(int boardId);
	
	int replyAjax(String contents, int boardId, String email);
}
