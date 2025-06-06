<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>JSONTest</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$(function() {
		$("#checkJson").click(function() {
			/* 회원정보를 JSON으로 생성 */
			var member = {
				id : "짱구",
				name : "짱아",
				pwd : "1234",
				email : "흰둥쓰@test.com"
			};
			$.ajax({
				type : "post",
				
				/*/test/info로 요청 */
				url : "${contextPath}/test/info",
				
				contentType : "application/json",
				
				/* 회원정보를 JSON 문자열로 변환 */
				data : JSON.stringify(member),
				success : function(data, textStatus) {
				},
				error : function(data, textStatus) {
					alert("에러가 발생했습니다.");
				},
				complete : function(data, textStatus) {
				}
			}); //end ajax    

		});
	});
</script>
</head>
<body>
	<input type="button" id="checkJson" value="회원 정보 보내기" />
	<br>
	<br>
	<div id="output"></div>
</body>
</html>