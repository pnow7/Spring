package com.myspring.pro30.board.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.member.vo.MemberVO;

//controller : 요청받는 곳

@Controller("boardController")
public class BoardControllerImpl implements BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private ArticleVO articleVO;

	@Override
	@RequestMapping(value = "/board/listArticles.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listArticles(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// viewName : listArticle
		// 인터셉터에서 전달된 뷰이름을 가져옴
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("=============BoardControllerImpl listArticles viewName:" + viewName);
		
		// 모든 글 정보를 조회
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		
		// 조회한 글 정보를 바인딩한 후 JSP로 전달
		mav.addObject("articlesList", articlesList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(
			MultipartHttpServletRequest multipartRequest, 
			HttpServletResponse response) throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		// 글 정보를 저장하기 위한 articleMap을 생성
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		// 글쓰기창에서 전송된 글 정보를 Map에 key/value로 저장
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}

		// 이미지 저장 안하므로 파일명 부분을 삭제함
		// String imageFileName= upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		
		// 세션에 저장된 회원정보로부터 회원 ID를 가져옴
		String id = memberVO.getId();
		
		// 회원 ID, 부모 글 번호를 articleMap에 저장
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		// 이미지 저장 안하므로 파일명 부분을 삭제함
		// articleMap.put("imageFileName", imageFileName);

		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			
			// 글 정보가 저장된 articleMap을 Service 클래스의 addArticle() 메서드로 전달
			int articleNO = boardService.addNewArticle(articleMap);
			// 이미지 저장 안하므로 파일명 부분을 삭제함
//			if(imageFileName!=null && imageFileName.length()!=0) {
//				File srcFile = new 
//				File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
//				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
//				FileUtils.moveFileToDirectory(srcFile, destDir,true);
//			}
			
			// 글 정보를 추가한 후 업로드한 글을 글 번호로 명명한 폴더로 이동
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/listArticles.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {

			// File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			// srcFile.delete();
			
			// 오류 발생 시 오류 메시지를 전달
			message = " <script>";
			message += " alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/articleForm.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	// 글쓰기창을 나타냄
	@RequestMapping(value = "/board/*Form.do", method = { RequestMethod.GET, RequestMethod.POST })
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//viewName : articleForm
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("=============BoardControllerImpl form viewName:" + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

}