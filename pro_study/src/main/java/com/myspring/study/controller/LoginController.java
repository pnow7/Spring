package com.myspring.study.controller;

import com.myspring.study.service.UserService;
import com.myspring.study.vo.UserVO;

public class LoginController {
	
	private UserService userService = new UserService();
	
	public void login(String username, String password) {
		UserVO user = new UserVO();
		user.setUsername(username);
		user.setPassword(password);
		
		boolean result = userService.authenticate(user);
		
		if(result) {
			System.out.println("로그인 성공");
		} else {
			System.out.println("로그인 실패");
		}
	}
}

/*

Controller: 클라이언트 요청을 받고, 알맞은 Service를 호출한 뒤 결과를 View에 전달

-구조 요약도
Client (View)
     ↓
Controller → Service → DAO → DB
			 ↑
			 VO(데이터 객체)

*/