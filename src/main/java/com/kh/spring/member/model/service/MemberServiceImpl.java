package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

@Service("mService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Member memberLogin(Member m) {
		
 		return mDAO.memberLogin(sqlSession, m);
	}

	@Override
	public int insertMember(Member m) {
		
		return mDAO.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		return mDAO.updateMember(sqlSession, m);
	}

	@Override
	public int pwdUpdateMember(String userId, String encNewPwd) {
		return mDAO.pwdUpdateMember(sqlSession, userId, encNewPwd);
	}

	@Override
	public int deleteMember(String userId, String userpwd) {
		return mDAO.deleteMember(sqlSession, userId, userpwd);
	}

	@Override
	public int duplicateId(String userId) {
		return mDAO.duplicateId(sqlSession, userId);
	}



	
	
	
}
