<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/jq.js">
</script>
<script type="text/javascript">
$(function(){
	
});
</script>
<style type="text/css">
#list-review-body{
	margin:30px;
	
	padding-top:30px;
	padding-left:120px;
	padding-right:120px;
}
#list-review-table{
	width:100%;
}
#list-review-title{
	position:relative;
	padding: 0px 50px 0px 25px;
	margin-left:20px;
	margin-bottom: 50px;
	min-height:60px;
}
#review-title-box{
	float:left;

}
#review-intro{
	float:left;
	width:80%;
	padding-left:7%;
	color:#b2b2b2;
	white-space: nowrap;
	padding-top:10px;
}
#review-table{
	border: 1px solid;
	border-color:#ddd;
	width:100%;
	height:auto;
}
#review-connect-product{
	padding:30px 0;
	border-bottom:1px solid #ddd;
	min-height:160px;
	margin-left:20px;
	margin-right:20px;
}
#review-img{
	float:left;
	display: inline-block;
	vertical-align: middle;
	margin-right:10px;
	width:80px;
	height:auto;
}
#review-info{
	float:left;
	line-height:18px;
	padding-top:9px;
	padding-left:20px;
}
#review-profile{
	height:30px;
	border-bottom: 1px solid #ddd;
	margin-left:20px;
	margin-right:20px;
}
#review-content{
	margin-left:20px;
	margin-right:20px;
}
#review-content-title{
	padding:10px 10px 20px 20px;

}
#review-content-summary{
	padding:10px 10px 20px 20px;
}
#review-insert-button{
	width:100%;
	margin-bottom:20px;
}
</style>
</head>
<body>
<div id="list-review-body" class="row">
	<div id="list-review-title" align="center">
		<div id="review-title-box">
			<h2 style="font-size:40px;display: inline-block;line-height:17px;font-weight: normal;height:17px;"><b>REVIEW</b></h2>
		</div>
		<div id="review-intro" align="left">
		"        DOGFOOTSHOP의 구매 후기입니다."<br>
		"        DOGFOOTSHOP의 구매 후기는 오직 구매자만이 작성 가능한 100% 믿을 수 있는 후기입니다. 구매 시 많은 참고 부탁 드립니다."
		</div>
	</div>
	<c:if test="${not empty m_no }">
	<div id="review-insert-button">
		<center><a href="insertReview.do" class="btn btn-default">리뷰 작성하기</a></center>
	</div>
	</c:if>
	<div id="list-review-table">
		<table class="table" id="review-table">
			<c:forEach var="r" items="${list }">
			<tr style="margin:40px;">
				<td>
				<div id="review-profile">
					<p>
						<span><strong>${r.id }</strong><font color="#b2b2b2">님의 후기</font></span>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;${r.create_time }</span>		
					</p>
				</div>
				<div id="review-connect-product">
					<div id="review-img"><img style="float:left;"src="resources/upload/${r.g_img }" width="100" height="100"></div>
					<div id="review-info">
						<p><strong>[${r.brand }]</strong></p>
						<p>${r.g_name }</p>
						<p><strong>${r.price }원</strong></p>
					</div>
				</div>
				<div id="review-content">
					<div id="review-content-title">
					<a href="detailReview.do?b_no=${r.b_no }" style="color:black;"><strong>[구매후기] ${r.title }</strong></a> 
					</div>
					<div id="review-content-summary">
					<font color="gray">${r.content }</font>
					</div>
				</div>
				</td>
				
			</tr>
			</c:forEach>
		</table>
		
	</div>
</div>
</body>
</html>