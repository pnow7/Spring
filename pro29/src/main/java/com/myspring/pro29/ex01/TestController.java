package com.myspring.pro29.ex01;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/*")
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	// /hello로 요청 시 브라우저로 문자열을 전송
	@RequestMapping("/hello")
	public String hello() {
		return "Hello REST!!";
	}
	
	// MemberVO 객체의 속성 값을 저장한 후 JSON으로 전송
	@RequestMapping("/member")
	public MemberVO member() {
		MemberVO vo = new MemberVO();
		vo.setId("dooly");
		vo.setPwd("1212");
		vo.setName("둘리");
		vo.setEmail("dooly@test.com");
		return vo;
	}
	
	// MemberVO 객체를 저장할 ArrayList 객체를 생성
	@RequestMapping("/membersList")
	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		for (int i = 0; i < 10; i++) { 
			MemberVO vo = new MemberVO(); // MemberVO 객체를 10개 생성해 ArrayList에 저장
			vo.setId("dooly" + i);
			vo.setPwd("123" + i);
			vo.setName("둘리" + i);
			vo.setEmail("dooly" + i + "@test.com");
			list.add(vo);
		}
		return list;
	}

	@RequestMapping("/membersMap")
	public Map<Integer, MemberVO> membersMap() {
		Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("dooly" + i + i);
			vo.setPwd("123" + i + i);
			vo.setName("둘리" + i + i);
			vo.setEmail("dooly" + i + i + "@test.com");
			map.put(i, vo);
		}
		return map;
	}
	
	// 브라우저 요청 시 {num} 부분의 값이 @PathVariable로 지정
	// @PathVariable("num") -> 요청 URL에서 지정된 값이 num에 자동으로 할당
	// ex) notice/112로 전송할 경우 112가 num에 할당
	@RequestMapping(value = "/notice/{num}", method = RequestMethod.GET)
	public int notice(@PathVariable("num") int num) throws Exception {
		return num;
	}
	
	// REST는 Ajax 기능과 연동해서 자주 사용,
	// 브라우저에서 JSON 데이터를 컨트롤러로 전송할 때 컨트롤러에서 JSON을 객체로 변환하는 기능 구현
	
	// @RequestBody를 사용하면 브라우저에서 전달되는 JSON 데이터를 객체로 자동 변환
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	// JSON으로 전송된 데이터를 MemberVO 객체의 속성에 자동으로 설정
	public void modify(@RequestBody MemberVO vo) {
		System.out.println("===================1");
		logger.info(vo.toString());
		System.out.println("===================2");
	}
	
	// @RestController는 별도의 view를 제공하지 않은 채 데이터를 전달하므로 전달 과정에서 예외가 발생할 수 있다.
	// 예외에 대해 좀 더 세밀한 제어가 필요한 경우 ResponseEntity 클래스를 사용
	// ResponseEntity로 응답
	@RequestMapping("/membersList2")
	public ResponseEntity<List<MemberVO>> listMembers2() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("dooly" + i);
			vo.setPwd("123" + i);
			vo.setName("둘리" + i);
			vo.setEmail("dooly" + i + "@test.com");
			list.add(vo);
		}
		
		// 주로 쓰는 상수
		// OK(200), BAD_REQUEST(400), UNAUTHORIZED(401), 
		// NOT_FOUND(404), INTERNAL_SERVER_ERROR(500)
		
		// return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	// ResponseEntity로 전송할 데이터의 종류와 한글 인코딩 설정
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
		
		// 전송할 데이터의 종류와 인코딩 설정
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		// 전송할 자바스크립트코드를 문자열로 작성
		String message = "<script>";
		message += " alert('새 회원을 등록합니다');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		
		// ResponseEntity를 이용해 HTML 형식으로 전송
		return new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
}