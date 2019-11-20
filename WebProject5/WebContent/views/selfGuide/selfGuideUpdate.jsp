<%@page import="member.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="../../js/HuskyEZCreator.js"></script>
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
	
	<form action="/guideUpdate" method="post" enctype="multipart/form-data" id="frm">
		<input type="hidden" name="selfNo" value="${requestScope.guideOne.selfNo }">
		작성자ID : <input type="text" name="writerId" value="${sessionScope.member.member_ID}" readonly required> <br>
		제목 : <input type="text" name="title" value="${requestScope.guideOne.selfTitle }"> <br>
		<input type="file" name="up_file" onchange="fn_loadImg(this)"  required > <br>
		<div> <img id="img-viewer" width="350" src="/upload/photo/${requestScope.guideOne.photoRenameFilename }"></div>
		<input type="hidden" name="fileName" value="${requestScope.guideOne.photoRenameFilename }">
		내용 : <textarea rows = "5" cols = "50" id="textAreaContent" name ="content">${requestScope.guideOne.selfContent }</textarea> <br>
		<input type="button" id="submitButton" value="등록" onclick="return validate();"> <br>
	</form>

<script>
		var oEditors = [];
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "textAreaContent",
			sSkinURI: "/SmartEditor2Skin.html",
			fCreator: "createSEditor2"
		});
		
		
		// form태그의 submit버튼사용을 하지않고
		// type을 button으로 설정하여 진행할때 사용하는것
		// 이버튼을누르면 submit이 일어나게하고 입력된 글들은 태그들에 묶여있기때문에 그것들을 하나로 묶음
		$("#submitButton").click(function() {
			oEditors.getById["textAreaContent"].exec("UPDATE_CONTENTS_FIELD", []);
			$("#frm").submit(); // 서브밋!
		});
		

		
		// 크기조정
		/* /dist/js/service에 가면 SE2BasicCreator.js가 있는데
		위치는 버전에 따라 조금 상이하며
		아래와 같은 내용에서 
		nMinHeight:500,
		nMinWidth:parseInt(elIRField.style.minWidth, 10)||800
		의 값을 수정해주면 창의 크기가 변함
		
		var htDimension = {
				nMinHeight:500,
				nMinWidth:parseInt(elIRField.style.minWidth, 10)||800,
				nHeight:elIRField.style.height||elIRField.offsetHeight,
				nWidth:elIRField.style.width||elIRField.offsetWidth
		}; */
		
		("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		// 파일 미리보기
		// 미리보기하기전에 업로드가 완료됨
		function pasteHTML(filepath){
			var sHTML = '<img src="<%=request.getContextPath()%>/upload/testphoto/'+filepath+'" width="300px">';

		    oEditors.getById["textAreaContent"].exec("PASTE_HTML", [sHTML]);
		}
	</script>



</body>
</html>