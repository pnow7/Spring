package com.myspring.study.dao;

import com.myspring.study.vo.UserVO;

public class UserDAO {
	
	public boolean login(UserVO user) {
		
		if("admin".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
			return true;
		}
		
		return false;
	}
}

/*

DAO(Data Access Object): DB와 직접 통신하며 CRUD 수행, SQL 실행을 담당함

-CRUD: 데이터 처리의 기본 네 가지 기능을 의미
C(Create): 데이터 생성(예: 회원가입, 게시글 작성 등)
R(Read): 데이터 조회(예: 회원 목록 보기, 게시글 읽기 등)
U(Update): 데이터 수정(예: 회원 정보 수정, 글 내용 수정 등)
D(Delete): 데이터 삭제(예: 게시글 삭제, 회원 탈퇴 등) 

*/