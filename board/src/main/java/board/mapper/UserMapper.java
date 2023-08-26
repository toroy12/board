package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.Board;
import board.dto.Reply;
import board.dto.User;

@Mapper
public interface UserMapper {
	
	User findByEmail(String email);
	
	void save(User user);
	
	List<Board> boardListByMe(String email);
	
	List<Reply> replyListByMe(String email);
}
