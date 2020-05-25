package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	Member memberLogin(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int pwdUpdateMember(String userId, String encNewPwd);

	int deleteMember(String userId, String userpwd);

	int duplicateId(String userId);


	
}
