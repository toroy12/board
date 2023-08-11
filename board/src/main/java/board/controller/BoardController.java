package board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.dto.Board;
import board.dto.Category;
import board.dto.Pagination;
import board.dto.Search;
import board.mapper.BoardMapper;
import board.service.BoardService;
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
							Model model) {
		
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
						 Model model) {
		
		int boardListBySearch = boardMapper.boardListBySearch(category, searchKey, searchValue);
		Pagination pagination = new Pagination(boardListBySearch, curPage);
		
		List<Board> search = boardMapper.search(category, searchKey, searchValue, pagination.getStartIndex(), pagination.getPageSize());
		Search searchKeep = new Search(category, searchKey, searchValue);
		
		String categoryName = "";
		switch(category) {
		case "free":
			categoryName = "자유 - ";
			break;
		case "study":
			categoryName = "공부 - ";
			break;
		case "game":
			categoryName = "게임 - ";
			break;
		}
		
		model.addAttribute("title", categoryName + "게시판");
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
									  Model model) {
		
		int boardlistByCategoryCnt = boardMapper.boardListByCategoryCnt(category);
		Pagination pagination = new Pagination(boardlistByCategoryCnt, curPage);
		Search searchKeep = new Search(category, searchKey, searchValue);
		
		List<Board> boardListByCategory = boardMapper.boardList(category, pagination.getStartIndex(), pagination.getPageSize());
		
		String categoryName = "";
		switch(category) {
		case "free":
			categoryName = "자유";
			break;
		case "study":
			categoryName = "공부";
			break;
		case "game":
			categoryName = "게임";
			break;
		}
		
		
		model.addAttribute("title", categoryName + " - 게시판");
		model.addAttribute("boardList", boardListByCategory);
		model.addAttribute("pagination", pagination);
		model.addAttribute("search", searchKeep);
		
		return "board/boardList";
	}	
	
	@GetMapping("/{category}/{boardId}")
	public String detailBoard(@PathVariable int boardId, Model model) {
		
		Board detailBoard = boardService.detailBoard(boardId);
		
		model.addAttribute("title", detailBoard.getTitle() + " - 게시판");
		model.addAttribute("detailBoard", detailBoard);
		
		return "board/detailboard";
	}
	
	@GetMapping("/write")
	public String write(Model model) {
		
		List<Category> category = boardMapper.category();
		
		model.addAttribute("title", "글쓰기");
		model.addAttribute("category", category);
		
		return "board/write";
	}
	
	@PostMapping("/write")
	public String write(Board board) {
		
		boardMapper.write(board);
		
		return "redirect:/";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam(value = "boardId") int boardId, Model model) {
		
		Board modify = boardMapper.modifyList(boardId); 
		List<Category> category = boardMapper.category();
		
		model.addAttribute("title", "수정");
		model.addAttribute("modify", modify);
		model.addAttribute("boardId", boardId);
		model.addAttribute("category", category);
		
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(Board board) {
		
		boardMapper.modify(board);
		
		return "redirect:/";
	}
	
	 @GetMapping("/delete") 
	 public String delete(@RequestParam(value = "boardId") int boardId) {
	 
		 boardMapper.delete(boardId);
		 
		 return "redirect:/";
	 }
		 
}