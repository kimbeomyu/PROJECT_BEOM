<!-- 회원가입 창 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="../../js/jquery-2.0.0.js"></script>

</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>

	<form action="/memberJoinServlet" method="POST">
		아이디 <br> <input type="text" name="id"> <br>
		<span id="test1"> </span> <button id="buto3">중복검사</button> <br> 
		 비밀번호 <br> <input type="text" name="pw"> <br>
		 비밀번호 확인 <br> <input type="text" name="pw_re"> <br> 
		이름 <br> <input type="text" name="name"> <br> 
		이메일 <br> <input type="text" name="email"> @ <input type="text" name="email2"> <br> 
		주소  <br> <input type="text" name="address"> <br>
		핸드폰 <br> <input type="text" name="phone"> <br> 
		<input type="submit" value="회원가입">
	</form>

	<span id="test1">우르끼끼으리으</span>
	

	<script>
	 	$("#buto3").click(function() {
			$.ajax({
				url : "/overlapCheck.do",
				type : "POST",
				data : "memberId=" + $("input[name=id]").val(),
				success : function(data) {
					$("#test1").html(data);
				}
			});
		}); 
	 	
		$("input[name=id]").on('blur',function() {
			$.ajax({
				url : "/overlapCheck.do",
				type : "POST",
				data : "memberId=" + $("input[name=id]").val(),
				success : function(data) {
					$("#test1").html(data);
				}
			});
		});
	</script>
</body>
</html>