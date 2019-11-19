<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<metacharset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>summernote</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script> <script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container" style="padding-top: 50px;">
		<form action="/">
			<textarea name="" id="myEditor" class="form-control" cols="30"
				rows="10">
        
        </textarea>
        
		</form>
		<div id ="z"></div>
	</div>
	

	<!-- 스타일관련 -->
	<link
		href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css"
		rel="stylesheet">
	<!-- 자바스크립트관련 -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.js"></script>
	<!-- 한글관련 -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/lang/summernote-ko-KR.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#myEditor').summernote({
				lang : 'ko-KR',
				height : 300,
				callbacks : {
					onImageUpload : function(files) {
						sendFile(files[0]);
					},

					onMediaDelete : function(target) {
 					var target2 = "upload"+target[0].src.split('upload')[1];
 					deleteFile(target2);
					
					}
				}
			});

			function sendFile(file) {
				var data = new FormData();
				data.append("file", file);
				$.ajax({
					data : data,
					type : "POST",
					url : "/imageUpload",
					cache : false,
					contentType : false,
					processData : false,
					success : function(data) {
						$('#myEditor').summernote('editor.insertImage', data);
					}
				});
			}
			
			function deleteFile(src) {
				
				console.log(src);
				$.ajax({
					data : {src : src},
					type : "POST",
					url: "/imageRemove",
					cache : false,
					success: function(data) {
						console.log("삭제되었습니다");
						console.log(data);
					}
				});
			}
		});
	</script>
</body>
</html>