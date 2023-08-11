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
	private String contents;
	private String createDate;
	private int boardId;
}
