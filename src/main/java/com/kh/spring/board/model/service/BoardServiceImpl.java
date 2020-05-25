package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;

@Service("bService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO bDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getListCount() {
		return bDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return bDAO.selectList(sqlSession, pi);
	}

	@Override
	public int insertBoard(Board b) {
		return bDAO.insertBoard(sqlSession, b);
	}

	@Override
	public Board selectBoard(int bid) {
		
		Board b = null;
		
		int result = bDAO.addReadCount(sqlSession, bid);
		
		if(result > 0) {
			b = bDAO.selectBoard(sqlSession, bid);
		}
		
		return b;
	}

	@Override
	public Board selectUpdateBoard(int bId) {
		return bDAO.selectBoard(sqlSession, bId);
	}

	@Override
	public int selectUpdateBoard(Board b) {
		return bDAO.updateBoard(sqlSession, b);
	}

	@Override
	public int deleteBoard(int bId) {
		return bDAO.deleteBoard(sqlSession, bId);
	}

	@Override
	public ArrayList<Reply> ReplyList(int bid) {
		return bDAO.ReplyList(sqlSession, bid);
	}

	@Override
	public int insertReply(Reply r) {
		return bDAO.insertReply(sqlSession, r);
	}

	@Override
	public ArrayList<Board> selectTopList() {
		return bDAO.selectTopList(sqlSession);
	}


}
