package com.myspring.study.vo;

public class UserVO {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

/*

VO(Value Object): 데이터 객체, DB에서 조회된 결과나 사용자의 입력값을 담는 데 사용.
주로 필드와 getter/setter만 있음

*/
