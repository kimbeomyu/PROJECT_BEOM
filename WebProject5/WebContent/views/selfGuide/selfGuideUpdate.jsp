<%@page import="member.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="../../js/jquery-2.0.0.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>selfGuide.jsp</title>
</head>
<body>
	
	<script>
	$(function() {
		
		
	});
		// 내용이 비어있는지 확인해주는 함수
		function validate() {
			var content = $("[name=content]").val();
			if(content.trim().length == 0) {
				alert("내용을 입력하세요");
				return false;
			}
			return true;
		}
	
		// 이미지 업로드시 업로드된 이미지를 미리보기할수있게해줌
		function fn_loadImg(f) {
		 	// console.log(f); // <input~ > 태그자체가 들어있음
			// console.log(f.files); // FileList 파일name, size, 경로 등등 들어있고
			// console.log(f.files[0]); // FileList의 [0]의 자리에는 filename이 들어있음
			if(f.files && f.files[0]) {
				var reader = new FileReader(); // 파일 읽기 메소드
				reader.readAsDataURL(f.files[0]); // 읽기 완료시 onload 이벤트 발생
				reader.onload = function(e) {
					$("#img-viewer").attr("src", e.target.result);
					console.log(e);
					// FileReader 객체의 result에는
					// 파일의 컨텐츠가 담겨있음
				}
			}
		}
		
		
	</script>
	
	<form action="/guideUpdate" method="post" enctype="multipart/form-data">
		<input type="hidden" name="selfNo" value="${requestScope.guideOne.selfNo }">
		작성자ID : <input type="text" name="writerId" value="${sessionScope.member.member_ID}" readonly required> <br>
		제목 : <input type="text" name="title" value="${requestScope.guideOne.selfTitle }"> <br>
		<input type="file" name="up_file" onchange="fn_loadImg(this)"  required > <br>
		<div> <img id="img-viewer" width="350" src="/upload/photo/${requestScope.guideOne.photoRenameFilename }"></div>
		<input type="hidden" name="fileName" value="${requestScope.guideOne.photoRenameFilename }">
		내용 : <textarea rows = "5" cols = "50" name ="content">${requestScope.guideOne.selfContent }</textarea> <br>
		<input type="submit" value="등록" onclick="return validate();"> <br>
	</form>




</body>
</html>