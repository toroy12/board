package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import board.mapper.UserMapper;
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
	  
	  
	/*
	 * @PostMapping("/signUp") public String signUp(@Valid User user, Errors errors,
	 * Model model) {
	 * 
	 * if (errors.hasErrors()) { model.addAttribute("user", user); Map<String,
	 * String> validateResult = userService.validateHandler(errors);
	 * 
	 * for (String key : validateResult.keySet()) { model.addAttribute(key,
	 * validateResult.get(key)); }
	 * 
	 * return "user/signUp"; }
	 * 
	 * userMapper.signUp(user);
	 * 
	 * return "redirect:/"; }
	 */
	
	// 마이페이지
	/*
	 * @GetMapping("/info") public String userInfo(Model model, Authentication auth)
	 * { model.addAttribute("loginType", "security-login");
	 * model.addAttribute("pageName", "Security 로그인");
	 * 
	 * User loginUser = userService.getLoginUserByLoginId(auth.getName());
	 * 
	 * if(loginUser == null) { return "redirect:/security-login/login"; }
	 * 
	 * model.addAttribute("user", loginUser); return "info"; }
	 */
	
}
