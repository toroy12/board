package board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import board.config.auth.SessionUser;
import board.dto.Board;
import board.dto.Reply;
import board.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {

	private final UserMapper userMapper;
	
	  @GetMapping("/signUp") 
	  public String signUp(Model model) {
	  
		  model.addAttribute("title", "회원가입");
	  
	  return "user/signUp"; 
	  }
	  
	  @GetMapping("/info")
	  public String userInfo(Model model, HttpSession httpSession) {
		  
		  SessionUser user = (SessionUser) httpSession.getAttribute("user");
			
			 if (user != null) {
			        model.addAttribute("userName", user.getName());
			    }
			 
		  List<Board> boardListByMe = userMapper.boardListByMe(user.getEmail());
		  List<Reply> replyListByMe = userMapper.replyListByMe(user.getEmail());
		  
		  model.addAttribute("title", user.getName() + "님의 정보");
		  model.addAttribute("boardListByMe", boardListByMe);
		  model.addAttribute("replyListByMe", replyListByMe);
		  
		  return "user/info";
	  }
	  
	
}
