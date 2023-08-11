package board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String userId;
	private String userPw;
	private String userName;
	private String nickName;
	private int tlno;
	private String address1;
	private String address2;
}
