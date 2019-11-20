
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="../../js/jquery-2.0.0.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>
		제목 :
		${guideOne.selfTitle}</h2>
	<img src="/upload/photo/${guideOne.photoRenameFilename}"
		width="300px" height="300px">

	<h6>
	
		글번호 :
		${guideOne.selfNo}
		/ 작성자 ID :
		${guideOne.writerId}
		/ 작성 날짜 :
		${guideOne.selfDate}
	</h6 >
	<c:if test="${guideOne.writerId ==  member.member_ID}" >
		<form action = "/selfGuideLoad" >
			<input type="hidden" name="selfNo" value="${guideOne.selfNo}">
			<input type="submit" value="수정"/>
		</form>
		<form action = "/guideRemove" >
			<input type="hidden" name="selfNo" value="${guideOne.selfNo }">
			<input type="hidden" name="fileName" value="${guideOne.photoRenameFilename}">
			<input type="submit" value="삭제" onclick="return fn_check()">
		</form>
	</c:if>
		
		/ 내용 :
		${guideOne.selfContent} <br>
		/ 조회수 :
		${guideOne.selfViews}
		/ 좋아요 :

	<span id="like">
		${guideOne.selfLike}
	</span>
	<button onclick="fn_like()">좋아?</button>
	<hr>


	comment : <input type="text" id="comment" placeholder="댓글을 작성하세요" onkeyup="enterkey()">
	<input type="submit" value="등록" onclick="fn_commentAdd()"/>
	
	<a href="/logout">로그아웃</a> 
	<h2>댓글</h2>
	<table id="table-tr">
		<tr>
			<th>댓글</th>
			<th>작성자ID</th>
			<th>작성일자</th>
		</tr>
		<c:forEach items="${commentList.comment }" var="commentOne">
			<tr>
				<input type="hidden" value="${commentOne.scommentNo }" id="${commentOne.scommentNo }"/>
				<td>${commentOne.scommentText }</td>
				<td>${commentOne.memberId }</td>
				<td>${commentOne.scommentDate }</td>
			<c:if test="${commentOne.memberId == member.member_ID }">
				<td><button onclick="fn_commentDelete(${commentOne.scommentNo })">삭제</button></td>
			</c:if>
			</tr>
		</c:forEach>

	</table>

	<script>
	// 삭제할건지 물어봐주는거 재확인
	function fn_check() {
		  if (confirm("정말 삭제하시겠습니까?")){
		    	return true;
			 } else {
				 return false;
		 	}
		}
	
	// 엔터키 누르면 실행될 이벤트를 인식시켜주는 함수
	function enterkey() {
		if (window.event.keyCode == 13) {
			fn_commentAdd()
       }
	}
		//좋아요 누르면 반응하는 함수
		function fn_like() {
			var param = {
				selfNo : "${guideOne.selfNo}",
				memberId :"${member.member_ID}"
			}

			$.ajax({
				url : "/likesCount",
				data : param,
				type : "post",
				dataType : "json",
				success : function(data) {
					var total = data;
					$("#like").html(total);
				}
			});
		}

		//댓글 추가시 ALERT창뜨고 바로 적용
		function fn_commentAdd() {
			
			var param = {
				comment: $("#comment").val(),
				memberId: "${member.member_ID}",
				selfNo: "${guideOne.selfNo}"
			}
			
			
			$.ajax({
				url: "/guideCommentAdd",
				data: param,
				type: "post",
				dataType: "json",
				success : function(data) {
					var commentOne = data;
					var html="";

					html += "<tr><td>" +commentOne.scommentText +"</td>";
					html += "<input type='hidden' value='"+commentOne.scommentNo+"' id='"+commentOne.scommentNo+"'/>";
					html += "<td>"+commentOne.memberId+"</td>";
					html += "<td>"+commentOne.scommentDate.substr(8,4)+"-"+commentOne.scommentDate.substr(0,2)
					              +"-"+commentOne.scommentDate.substr(4,2)+"</td>";
					
					if(commentOne.memberId == "${member.member_ID}") {
						html +="<td><button onclick='fn_commentDelete("+commentOne.scommentNo+")'>삭제</button></td>";
					}
					html += "</tr>";
					$("#table-tr").append(html);
					$("#comment").val("");
				}
			});
		}
		

		// 댓글 삭제
		function fn_commentDelete(scommentNo) {
			
			var param = {
					scommentNo: scommentNo,
					selfNo: "${guideOne.selfNo}"
			}
			
			$.ajax({
				url: "/commentDelete",
				data: param,
				type: "post",
				dataType: "json",
				success: function(data) {
					var guideCommentList = data;
					var html="";
					html +="<tr><th>댓글</th><th>작성자ID</th><th>작성일자</th></tr>";
					for(var i in guideCommentList ) {
						
						var commentOne = guideCommentList[i];
						
						
						html += "<tr><td>" +commentOne.scommentText +"</td>";
						html += "<input type='hidden' value='"+commentOne.scommentNo+"' id='"+commentOne.scommentNo+"'/>";
						html += "<td>"+commentOne.memberId+"</td>";
						html += "<td>"+commentOne.scommentDate.substr(8,4)+"-"+commentOne.scommentDate.substr(0,2)
			              +"-"+commentOne.scommentDate.substr(4,2)+"</td>";
						
						if(commentOne.memberId == "${member.member_ID}") {
							html +="<td><button onclick='fn_commentDelete("+commentOne.scommentNo+")'>삭제</button></td>";
						}
						html += "</tr>";
					}
					$("#table-tr").html(html);
					$("#comment").val("");
				}
			});
		} 

	</script>
</body>
</html>