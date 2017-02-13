<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
#detail-review-body{
	margin:30px;
	
	padding-top:30px;
	padding-left:120px;
	padding-right:120px;
}
#detail-review-table{
	width:100%;
}
#detail-review-title{
	position:relative;
	padding: 0px 50px 0px 25px;
	margin-left:20px;
	margin-bottom: 50px;
	min-height:60px;
}
#detail-title-box{
	float:left;

}
#detail-intro{
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
#detail-review-connect-product{
	padding:30px 0;
	border-bottom:1px solid #ddd;
	min-height:160px;
	margin-left:20px;
	margin-right:20px;
}
#detail-review-img{
	float:left;
	display: inline-block;
	vertical-align: middle;
	margin-right:10px;
	width:80px;
	height:auto;
}
#detail-review-info{
	float:left;
	line-height:18px;
	padding-top:9px;
	padding-left:20px;
}
#detail-review-profile{
	height:30px;
	border-bottom: 1px solid #ddd;
	margin-left:20px;
	margin-right:20px;
}
#detail-review-content{
	margin-left:20px;
	margin-right:20px;
}
#detail-review-content-title{
	padding:10px 10px 20px 20px;

}
#detail-review-content-summary{
	padding:10px 10px 20px 20px;
}
#detail-review-button{
	width:100%;
	margin-bottom:20px;
}
</style>
</head>

<body>
<div id="detail-review-body" class="row">
	<div id="detail-review-title" align="center">
		<div id="detail-title-box">
			<h2><strong>리뷰 상세</strong></h2>
		</div>
	</div>
	<div id="detail-review-table">
		<table class="table" id="review-table">

			<tr style="margin:40px;">
				<td>
				<div id="detail-review-profile">
					<p>
						<span><strong>${vo.id }</strong><font color="#b2b2b2">님의 후기</font></span>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;${vo.create_time }</span>		
					</p>
				</div>
				<div id="detail-review-connect-product">
					<div id="detail-review-img"><a href="detailGoodsBoard?b_no=${g_sender }"><img style="float:left;"src="resources/upload/${vo.g_img }" width="100" height="100"></a></div>
					<div id="detail-review-info">
						<p><strong>[${vo.brand }]</strong></p>
						<p><a href="detailGoodsBoard?b_no=${g_sender }" style="color:black;">${vo.g_name }</a></p>
						<p><strong>${vo.price }원</strong></p>
					</div>
				</div>
				<div id="detail-review-content">
					<div id="detail-review-content-title">
					<strong>[구매후기] ${vo.title }</strong> 
					</div>
					<div id="detail-review-content-summary">
					<font color="gray">${vo.content }</font>
					</div>
				</div>
				</td>
				
			</tr>
			
		</table>
	
		<div id="detail-review-delete">
		
		<a href="deleteReview.do?b_no=${vo.b_no}" class="btn btn-default">삭제</a>
		<a href="listReview.do" class="btn btn-default">뒤로</a>		

		</div>
	</div>
</div>
</body>
</html>