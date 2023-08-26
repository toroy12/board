package board.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.config.auth.SessionUser;
import board.dto.Board;
import board.dto.Category;
import board.dto.Pagination;
import board.dto.Search;
import board.mapper.BoardMapper;
import board.service.BoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final BoardMapper boardMapper;
	
	@GetMapping("/favicon.ico")
	public ResponseEntity<byte[]> favicon() {
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/")
	public String boardList(@RequestParam(value = "category", required = false) String category,
							@RequestParam(defaultValue="1", required=false) int curPage,
							@RequestParam(value = "searchKey", required = false, defaultValue = "title") String searchKey,
						    @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue, 
							Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		
		int boardlistCnt = boardMapper.boardListCnt();
		Pagination pagination = new Pagination(boardlistCnt, curPage);
		Search searchKeep = new Search(category, searchKey, searchValue);
		
		if(category == null) {
			
			List<Board> boardList = boardMapper.boardList(category, pagination.getStartIndex(), pagination.getPageSize());
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("pagination", pagination);
		}else {
			List<Board> boardListByCategory = boardMapper.boardList(category, pagination.getStartIndex(), pagination.getPageSize());
			model.addAttribute("boardList", boardListByCategory);
			model.addAttribute("pagination", pagination);
		}
		
		model.addAttribute("title", "게시판");
		model.addAttribute("search", searchKeep);
		
		return "board/boardList";
	}	
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "category", defaultValue = "") String category, 
						 @RequestParam(value = "searchKey", required = false, defaultValue = "title") String searchKey,
						 @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue, 
						 @RequestParam(defaultValue="1", required=false) int curPage,
						 Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		
		int boardListBySearch = boardMapper.boardListBySearch(category, searchKey, searchValue);
		Pagination pagination = new Pagination(boardListBySearch, curPage);
		
		List<Board> search = boardMapper.search(category, searchKey, searchValue, pagination.getStartIndex(), pagination.getPageSize());
		Search searchKeep = new Search(category, searchKey, searchValue);
		
		if(boardListBySearch != 0) {
			
			model.addAttribute("title", search.get(0).getCategoryName() + " - 게시판");

		}else {
			
			model.addAttribute("title", "게시판");
		}
		
		model.addAttribute("boardList", search);
		model.addAttribute("search", searchKeep);
		model.addAttribute("category", category);
		model.addAttribute("pagination", pagination);
		
		return "board/boardList";
	}	
	
	@GetMapping("/{category}")
	public String boardListByCategory(@PathVariable(value = "category") String category,
									  @RequestParam(defaultValue="1", required=false) int curPage,
									  @RequestParam(value = "searchKey", required = false, defaultValue = "title") String searchKey,
									  @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue, 
									  Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		
		int boardlistByCategoryCnt = boardMapper.boardListByCategoryCnt(category);
		Pagination pagination = new Pagination(boardlistByCategoryCnt, curPage);
		Search searchKeep = new Search(category, searchKey, searchValue);
		
		List<Board> boardListByCategory = boardMapper.boardList(category, pagination.getStartIndex(), pagination.getPageSize());
		
		if(boardlistByCategoryCnt != 0) {
			
			model.addAttribute("title", boardListByCategory.get(0).getCategoryName() + " - 게시판");

		}else {
			
			model.addAttribute("title", "게시판");
		}
		
		model.addAttribute("boardList", boardListByCategory);
		model.addAttribute("pagination", pagination);
		model.addAttribute("search", searchKeep);
		
		return "board/boardList";
	}	
	
	@GetMapping("/{category}/{boardId}")
	public String detailBoard(@PathVariable int boardId, @PathVariable String category, 
			Model model, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		 
		 Board detailBoard = boardService.detailBoard(boardId);

		 if ( detailBoard != null ) {   //  상세조회를 했을경우

		        String userIdCheck = "";   // 현재 로그인한멤버와, 글쓴멤버가 같은지 체크 하기위한 변수초기화

		        if ( user != null ) {        
		            userIdCheck = user.getEmail();    
		        }
		        if ( detailBoard.getWriterId() != userIdCheck ) {  // 글쓴이와 로그인한 사람이 같지 않은경우 조회수증가
		            Cookie cookie = null ;               // 기존에 존재하던 쿠키를 저장하는 변수
		            Cookie[] cookies = request.getCookies(); // 저장되있는 쿠키 싹 다 얻어오기

		            if ( cookies != null && cookies.length > 0 ) {    // 얻어온쿠키가 있을 경우
		                // 얻어온 쿠키중 이름이 "readBoardNo" 가 있으면 얻어오기.
		                for ( Cookie c : cookies) {
		                    if ( c.getName().equals("boardViews")) {
		                        cookie = c;
		                    }
		                }
		            }
		            int result=0;

		            if ( cookie == null ) {      // "readBoardNo" 쿠키가 없을 경우
		                cookie = new Cookie("boardViews",boardId+"");  
		                result = boardService.boardViewsCount(boardId);   // 조회수증가 서비스호출

		            }else {            // 기존에 "readBoardNo" 쿠키가 있을 경우
		                //--> 쿠키에 저장된 값 뒤쪽에 현재 조회된 게시글 번호를 추가 
		                // 단, 기존 쿠키값에 중복되는 번호가 없어야한다 !!!

		                String[] temp = cookie.getValue().split("/");
		                // "readBoardNo"  :  "1/2/11/10/20/300/1000"  == [1,2,11,20,300,1000]
		                List<String> list = Arrays.asList(temp);   //  배열 --> List 변환

		                // List.indexOf(Object) 
		                // -- List에서 Object 와 일치하는 부분의 인덱스 반환,  없으면 -1 

		                if ( list.indexOf( boardId+"") == -1 ) {  // 기존값에 같은글번호가 없다면 추가
		                    cookie.setValue( cookie.getValue() +"/"+ boardId );
		                    result = boardService.boardViewsCount(boardId);   // 조회수증가 서비스호출
		                }
		            }
		            if ( result > 0 ) {   // 결과값 이용한 DB 동기화
		            	detailBoard.setViews(detailBoard.getViews()+1);   // 이미 조회된 데이터 DB동기화
		                cookie.setPath(request.getContextPath());
		                cookie.setMaxAge(60 * 60 * 1);
		                response.addCookie(cookie);
		            }
		        }
		    }
		
		model.addAttribute("title", detailBoard.getTitle() + " - 게시판");
		model.addAttribute("detailBoard", detailBoard);
		
		return "board/detailboard";
	}
	
	@GetMapping("/write")
	public String write(Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		        model.addAttribute("userEmail", user.getEmail());
		    }
		
		List<Category> category = boardMapper.category();
		
		model.addAttribute("title", "글쓰기");
		model.addAttribute("category", category);
		
		return "board/write";
	}
	
	@PostMapping("/write")
	public String write(Model model, Board board, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		 
		boardMapper.write(board);
		
		return "redirect:/";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam(value = "boardId") int boardId, Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		
		Board modify = boardMapper.modifyList(boardId); 
		List<Category> category = boardMapper.category();
		
		model.addAttribute("title", "수정");
		model.addAttribute("modify", modify);
		model.addAttribute("boardId", boardId);
		model.addAttribute("category", category);
		
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(Board board, Model model, HttpSession httpSession) {
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		 if (user != null) {
		        model.addAttribute("userName", user.getName());
		    }
		
		boardMapper.modify(board);
		
		return "redirect:/";
	}
	
	 @GetMapping("/delete") 
	 public String delete(@RequestParam(value = "boardId") int boardId) {
	 
		 boardMapper.delete(boardId);
		 
		 return "redirect:/";
	 }
		 
}