<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.goods_list{
		position: relative; 
		float: left; 
		width: 20%; 
		height:300px; 
		margin-bottom: 5%;
	}
	#goods_img{
		position: absolute; 
		left: 70px; 
		top: 0px;
		width: 200px; 
		height: 300px;
	}
	.like_img{
 		opacity:0;  
		position: absolute; 
		left: 70px; 
		bottom: 0px;
		width: 200px;
		height: 60px; 
	}
	#over_img{
 		opacity:0; 
		position: absolute; 
		left: 70px; 
		top: 0px; 
		width: 200px; 
		height: 240px;
	}
	#goods_title{
		position:absolute; 
		left:70px; 
		top:295px; 
		font-size: x-large;
		width: 200px;
		overflow: hidden;
	}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function() {
		//상품사진위에 마우스 오버시 발생하는 이벤트
		$(".goods_list").hover(function() {
			$(this).children().eq(1).css("opacity",1);
			var ch = ($(this).children().eq(2));
			ch.children().eq(0).css("opacity",0.3);
		},function(){
			$(this).children().eq(1).css("opacity",0);
			var ch = ($(this).children().eq(2));
				ch.children().eq(0).css("opacity",0);
		});
		
		//검색창 토글 이벤트
		$("#search_filter").hide();
		$("#search").click(function() {
			$("#search_filter").slideToggle();
		});
		
		//좋아요 버튼 클릭
		$(".like_img").click(function() {
			//alert("좋아요!");
			var b_no = $(this).attr("l_b_no");
			if($("#m_no").val()>=1){
				var m_no = $("#m_no").val();
				alert("좋아요 회원번호 "+m_no);
				alert("좋아요 글번호 "+b_no);
				$.getJSON({
					url: "boardLike.do",
					data: {m_no:m_no,b_no:b_no},
					success:function(data){
						alert("좋아요 생성!");
				}});
			}else{
				alert("로그인이 필요합니다!");
			}
		});
	});
</script>
</head>
<body>
<%-- 	<h2>회원번호 : ${msg }</h2> --%>

	<center><br><br><div><h3 id="search">!!!!!!! 검색 !!!!!!!</h3></div>
	<div id="search_filter">
		<form action="listGoodsBoard.do" method="post">
			<table>
				<tr>
					<td colspan="7"><center><input type="search" name="keyword" placeholder=검색어를 입력하세요.">
									<input type="submit" value="검색"></center></td>
				</tr><tr><td><br></td></tr>
				<tr>
					<td>--- 카테고리 ---</td><td></td>
					<td>---  브랜드  ---</td><td></td>
					<td>---   색상   ---</td><td></td>
					<td>---  사이즈  ---</td>
				</tr>
				<tr>
					<td>
						<c:forEach var="c" items="${category }">
	        				<input type="checkbox" name="category" value="${c }">${c }<br>
						</c:forEach>
					</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<c:forEach var="b" items="${brand }">
	        				<input type="checkbox" name="brand" value="${b }">${b }<br>
	        			</c:forEach>
					</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<c:forEach var="c" items="${color }">
	        				<input type="checkbox" name="color" value="${c }">${c }<br>
	        			</c:forEach>
					</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<c:forEach var="s" items="${g_size }">
	        				<input type="checkbox" name="g_size" value="${s }">${s }<br>
	        			</c:forEach>
					</td>
				</tr>
				<tr><td><br></td></tr>
			</table>
		</form>
	</div><br><br></center> 
	<c:forEach var="l" items="${list }">
		<div class="goods_list">
			<img id="goods_img" src="resources/upload/${l.b_img }"/>
    		<img l_b_no=${l.b_no  } class="like_img" src="resources/upload/like.jpg"/>
    		<a href="detailGoodsBoard.do?b_no=${l.b_no }"><img id="over_img" src="resources/upload/black.jpg"/></a>			
    		<p id="goods_title" align="justify">${l.title }</p>
		</div>
	</c:forEach>
</body>
</html>