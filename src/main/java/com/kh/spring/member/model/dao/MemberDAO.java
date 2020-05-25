package com.kh.spring.member.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository("mDAO")
public class MemberDAO {

	public Member memberLogin(SqlSessionTemplate sqlSession, Member m) {
		return (Member)sqlSession.selectOne("memberMapper.memberLogin", m);
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}

	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember", m);
	}

	public int pwdUpdateMember(SqlSessionTemplate sqlSession, String userId, String encNewPwd) {
		HashMap<String,String> hs = new HashMap<String, String>();
		System.out.println("dao"+userId+encNewPwd);
		hs.put("userId", userId);
		hs.put("newpwd", encNewPwd);
		return sqlSession.update("memberMapper.pwdUpdateMember",hs);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String userId, String userpwd) {
		HashMap<String,String> hs = new HashMap<String, String>();
		hs.put("userId", userId);
		hs.put("userpwd", userpwd);
		return sqlSession.update("memberMapper.deleteMember", hs);
	}

	public int duplicateId(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.selectOne("memberMapper.duplicateId", userId);
	}
	
}
