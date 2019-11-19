<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../../js/jquery-2.0.0.js"></script>
<title>selfGuideMain</title>

    <style>
        * {
            margin: 0;
            padding: 0;
        }

        .wrap {
            border: 1px solid;
            width: 1220px;
            /* height: 1600px; */
            margin: auto;
        }

        .header {
            border: 1px solid black;
            /* height: 10%; */
            height: 150px;
            margin-top: 5%
        }

        .nav1 {
            border: 1px solid red;
            height: 60%;
            width: 100%;
            box-sizing: border-box;
        }

        .nav2 {
            border: 1px solid blue;
            height: 40%;
            width: 100%;
            box-sizing: border-box;

        }

        .nav1-1 {
            border: 1px solid #a700ff;
            display: inline-block;
            height: 100%;
            width: 60%;
            box-sizing: border-box;
        }

        .nav1-2 {
            border: 1px solid green;
            display: inline-block;
            height: 100%;
            width: 40%;
            box-sizing: border-box;
            float: right;
        }

        .content {
            width: 100%;
            /* height: 75%; */
            
            border: 3px dashed hotpink;
            box-sizing: border-box;
        }

        .footer {
            width: 100%;
            /* height: 11%; */
            height:150px;
            border: 3px dashed #0a00ff;
            box-sizing: border-box;
        }

        .photo-wrap {
            border: 2px solid;
            margin: 5%;
            height: 90%;
            width: 90%;
            box-sizing: border-box;

        }

        #photo-post {
            border: 1px solid;
            height: 30%;
            width: 20%;
            margin: 5%;
        }
        #image-wrap{
            border: 2px dashed blue;
            height: 60%;
            width: 100%;
            border-radius: 10%;
            box-sizing: border-box;
        }
        
        #photo-title{
            height: 40%;
            border: 2px dashed;
            width: 100%;
            box-sizing: border-box;
        }
        h2{
            border: 1px solid;
            height: 30%;
            width: 100%;
            box-sizing: border-box;
            margin: 0;
        }
        p{
            border: 1px solid;
            height: 24%;
            width: 100%;
            box-sizing: border-box;
            margin: 0;
        }
    </style>

</head>
<body>

    <div class="wrap">

        <!--  navigation  -->
        <div class="header">

            <div class="nav1">
                <div class="nav1-1"></div>
                <div class="nav1-2"> <a href="/views/selfGuide/write.jsp">글쓰기</a> </div>
            </div>
            <div class="nav2"></div>
        </div>

        <!-- content -->

        <div class="content" id="guide-content">

            <div class="photo-wrap">
                <h1>테마별 노하우</h1>
                <div id="photo-post">
                   
                </div>

            </div>
        </div>
        
        <!-- footer  -->
        <div class="footer">

        </div>
    </div>
	
	<script>
		var start = 1; // 시작은 1
		$(function() {
			
			fn_more(start);
			start++;
		});
			// 스크롤이 움직이면 실행되는 함수
			// parseInt로 값을 주어 소수점으로 떨어지는 것을 방지하고
			// 5px의 오차범위를 두어 실행되도록 함
		$(window).scroll(function() {
			
			var scrolltop = $(window).scrollTop(); // 스크롤의 위치에 따라 변하는 값(세로좌표) 맨위 0 맨아래도달시 max
			var height_win = $(window).height(); // 화면에 보여지는 창의 길이
			var height = $("#guide-content").height(); // 문서의 길이
			
			if(height-height_win+10 >= Math.round(scrolltop) && Math.round(scrolltop) >= height-height_win) {
				console.log(scrolltop);
				console.log(height_win);
				console.log(height);
				console.log(height-height_win);
				
				fn_more(start);
				start++;
				
			}
		});
		
			
		// dd
		function fn_more(start) {
			var param = {start: start}
				
			$.ajax({
				url: "/moreGuide",
				data: param,
				type: "post",
				dataType: "json",
				success: function(data) {
					var html = "";
					if(start==1) {
						$("#photo-post").html("");
					}	
					for(var i in data) {
						var guideOne = data[i];
						html += "<a href='/selfGuideDateil?selfNo="+guideOne.selfNo+"'>";
						html += "<div id='image-wrap'>";
						html += "<img src='/upload/photo/"+guideOne.photoRenameFilename+"' width='300px'>";
						html += "</div>";
						html += "<div id='photo-title'>";
						html += "<h2 class='p-title'>"+guideOne.selfTitle +"</h2>";
						html += "<p class='p-memberId'>"+guideOne.writerId +"</p>";
						html += "<p class='p-views'>"+guideOne.selfViews+"</p>";
						html += "<p class='p-likes'>"+guideOne.selfLike+"</p></div></a>";
					}
					$("#photo-post").append(html);
						
					// 마지막 게시물이라면 fn_more을 비활성화하기위해사용
					var total= "${total}";
					if(start>=total) {
						
					}
						
				}
			});	
		}
			
	
	</script>
</body>
</html>