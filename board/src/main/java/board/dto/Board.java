package board.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
	List<Board> boardListAll;
	private int boardId;
	private int num;
	private String writerId;
	private String writerName;
	private String title;
	private String contents;
	private String createdDate;
	private int views;
	private String category;
	private String categoryName;
	private int countReply;
}
