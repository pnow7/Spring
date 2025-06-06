<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>

<body>
	<form id="frmLogin">
		<table border="1" width="80%" align="center">
			<tr align="center">
				<td>아이디</td>
				<td>비밀번호</td>
			</tr>
			<tr align="center">
				<td><input type="text" name="id" value="" size="20"></td>
				<td><input type="password" name="pwd" value="" size="20">
				</td>
			</tr>
			<tr align="center">
				<td colspan="2"><input type="button" value="로그인"
					onclick="login()"> <input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
	<script>
		const contextPath = '${contextPath}';
		const urlParams = new URLSearchParams(window.location.search);
		const action = urlParams.get('action');

		function login() {

			$.ajax({
				url : contextPath + '/member/api/login',
				type : 'POST',
				data : $('#frmLogin').serialize(),
				dataType : 'json',
				success : function(response) {
					if (response.success) {
						if (action) {
							location.href = contextPath + action;
						} else {
							location.href = contextPath;
						}
					} else {
						alert(response.message || ' 로그인에 실패했습니다');
					}
				},
				error : function() {
					alert("서버 통신 중 오류가 발생했습니다");
				}
			})

		}
	</script>
</body>
</html>
