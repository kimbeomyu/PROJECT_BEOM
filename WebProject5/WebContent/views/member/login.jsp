<!-- 로그인 창  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/loginServlet" method="POST"> 
		아이디 <br> <input type="text" name="id"> <br>
		비밀번호 <br> <input type="text" name="pw"> <br>
		<input type="submit" value="로그인">
	</form>
	
</body>
</html>