package board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reply {
	private String replyId;
	private String writerId;
	private String writerName;
	private String contents;
	private String createdDate;
	private int boardId;
	private int cDepth;
	private String category;
	private String categoryName;
}
