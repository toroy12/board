package board.controller;

import java.util.List;
import java.util.Map;

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
	public String allBoardList(@RequestParam(value ="category", required = false) String category, Model model) {
		if(category == null) {
			
			List<Board> boardList = boardMapper.boardList(category);
			model.addAttribute("boardList", boardList);
		}else {
			List<Board> boardListByCategory = boardMapper.boardList(category);
			model.addAttribute("boardList", boardListByCategory);
		}
		
		return "board/boardList";
	}	

	@GetMapping("/{category}")
	public String boardListByCategory(@PathVariable (value = "category") String category, Model model) {
		
		List<Board> boardListByCategory = boardMapper.boardList(category);
		
		model.addAttribute("boardList", boardListByCategory);
		
		return "board/boardList";
	}	
	
	@GetMapping("/{category}/{boardId}")
	public String detailBoard(@PathVariable int boardId, Model model) {
		
		Board detailBoard = boardService.detailBoard(boardId);
		
		model.addAttribute("detailBoard", detailBoard);
		
		return "board/detailboard";
	}
	
	@GetMapping("/write")
	public String write(Model model) {
		
		List<Category> category = boardMapper.category();
		
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