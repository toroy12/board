package board.mapper;

import org.apache.ibatis.annotations.Mapper;

import board.dto.User;

@Mapper
public interface UserMapper {
	
	User findByEmail(String email);
	
	void save(User user);
}
