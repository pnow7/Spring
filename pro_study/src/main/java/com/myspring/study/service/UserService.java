package com.myspring.study.service;

import com.myspring.study.dao.UserDAO;
import com.myspring.study.vo.UserVO;

public class UserService {
	
	private UserDAO userDAO = new UserDAO();
	
	public boolean authenticate(UserVO user) {
		return userDAO.login(user); // DAO 호출
	}
}

/*

Service: 비지니스 로직 처리, 여러 DAO 조합 가능, 트랜잭션 관리 등 포함

*/