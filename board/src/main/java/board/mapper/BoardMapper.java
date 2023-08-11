package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.Board;
import board.dto.Category;

@Mapper
public interface BoardMapper {
	
	public List<Board> boardList(String category);

	public List<Board> search(String category, String searchKey, String searchValue);
	
	public Board detailBoard(int boardId);
	
	public List<Category> category();
	
	public int write(Board board);
	
	public Board modifyList(int boardId);
	
	public int modify(Board board);
	
	public int delete(int boardId);
}
