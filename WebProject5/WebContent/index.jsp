<!-- 메인 홈페이지  -->
<%@page import="member.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% Member mOne = (Member)session.getAttribute("member"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pet.COM</title>
</head>
<body>
<% if (mOne != null) { %>
		<%=mOne.getMember_ID() %> 야 반가워
		<a href="/views/selfGuide/write.jsp">글쓰기</a>
		<a href="/selfGuideList">셀프가이드</a>
		<a href="/logout">로그아웃</a>
		<a href="/views/member/updateMember.jsp">정보수정</a>
	
<% } else { %>
	<a href="/views/member/login.jsp"> 로그인  </a>
	<a href="/views/member/join.jsp"> 회원가입 </a>
	
<% } %>
</body>
</html>