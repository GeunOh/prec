package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.kh.spring.board.model.exception.BoardException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.Pagenation;
import com.kh.spring.member.model.vo.Member;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	
	// 게시판 불러오기
	@RequestMapping("blist.bo")
	public ModelAndView boardList(@RequestParam(value="page", required = false) Integer page, ModelAndView mv) {
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = bService.getListCount();
		
		PageInfo pi = Pagenation.getPageInfo(currentPage, listCount);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		if(list != null) {
			// list, pi, view
			
			mv.addObject("list",list);
			mv.addObject("pi",pi);
			mv.setViewName("boardListView");
			
		} else {
			throw new BoardException("게시글 전체 조회에 실패했습니다.");
		}
		
		
		return mv;
	}
	
	//게시글 쓰기 페이지 이동
	@RequestMapping("binsertView.bo")
	public String boardInsertView() {
		return "BoardInsertForm";
	}
	
	//게시글 추가
	@RequestMapping("binsert.bo")
	public String boardInsert(@ModelAttribute Board b, @RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request) {
		
		System.out.println(b);
		System.out.println(uploadFile);
		System.out.println(uploadFile.getOriginalFilename());
		
		if(uploadFile != null && !uploadFile.isEmpty()) {
			String renameFileName = saveFile(uploadFile, request);
			
			if(renameFileName != null) {
				b.setOriginalFileName(uploadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		
		int result = bService.insertBoard(b);
		
		if(result > 0) {
			return "redirect:blist.bo";
		}else {
			throw new BoardException("게시물 등록에 실패했습니다.");
		}
		
	}
	
	//파일 이름 변경
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		
		File folder = new File(savePath);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date(System.currentTimeMillis()))
								+ "."
								+ originFileName.substring(originFileName.lastIndexOf(".")+1);
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return renameFileName;
	}
	
	@RequestMapping("bdetail.bo")
	public ModelAndView boardDetail(@RequestParam("bId") int bid, @RequestParam("page") int page, ModelAndView mv ) {
		
		Board board = bService.selectBoard(bid);
		
		if(board != null) {
			mv.addObject("board", board)
			  .addObject("page",page)
			  .setViewName("BoardDetailView");
		}else {
			throw new BoardException("게시글 조회에 실패했습니다.");
		}
		return mv;
	}
	@RequestMapping("bupView.bo")
	public ModelAndView boardUpdateForm(@RequestParam("bId") int bId,  @RequestParam("page") int page,  ModelAndView mv) {
		
		Board board = bService.selectUpdateBoard(bId);
		if (board != null) {
			mv.addObject("board", board)
			  .addObject("page", page)
			  .setViewName("BoardUpdateForm");
			return mv;
		}else {
			throw new BoardException("게시글 수정 폼 요정에 실패");
		}
	
	}
	
	@RequestMapping("bupdate.bo")
	public ModelAndView boardUpdate(@ModelAttribute Board b, @RequestParam("reloadFile") MultipartFile reloadFile, 
						      @RequestParam("page") int page, HttpServletRequest request, ModelAndView mv) {
		
		if(reloadFile != null && !reloadFile.isEmpty()) {
			if(b.getRenameFileName() != null) {
				deleteFile(b.getRenameFileName(), request);
			}
			
			String renameFileName = saveFile(reloadFile, request);
			
			if(renameFileName != null) {
				b.setOriginalFileName(reloadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		int result = bService.selectUpdateBoard(b);
		
		if(result>0) {
			mv.addObject("page", page)
			  .setViewName("redirect:bdetail.bo?bId=" + b.getbId());
		} else {
			throw new BoardException("게시글 수정을 실패했습니다");
		}
		return mv;
	}
	
	public void deleteFile(String fileName, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		File f = new File(savePath + "\\" + fileName);
		
		if(f.exists()) {
			f.delete();
		}
		
	}
	
	@RequestMapping("bdelete.bo")
	public String deleteBoard(@RequestParam("bId") int bId) {
		int result = bService.deleteBoard(bId);
		
		if (result > 0) {
			return "redirect:blist.bo";
		} else {
			throw new BoardException("게시글 삭제에 실패했습니다.");
		}
	}
	
	@RequestMapping("rList.bo")
	public void ReplyList(@RequestParam("bId") int bid, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8"); 
		ArrayList<Reply> list = bService.ReplyList(bid);
		
//		try {
//			for(Reply r : list) {
//					r.setrContent(URLEncoder.encode(r.getrContent(),"UTF-8"));
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
			
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		try {
			gson.toJson(list, response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("addReply.bo")
	@ResponseBody
	public String addReply(@ModelAttribute Reply r, HttpSession session) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		String rWriter = loginUser.getId();
		
		r.setrWriter(rWriter);
		
		int result = bService.insertReply(r);
	
		if(result > 0) {
			return "success";
		}else {
			throw new BoardException("댓글 등록에 실패했습니다.");
		}
	}
	
	@RequestMapping("topList.bo")
	public void topList(HttpServletResponse response) {
		
		response.setContentType("application/json; charset=UTF-8");
		
		ArrayList<Board> list = bService.selectTopList();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		try {
			gson.toJson(list, response.getWriter());
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
