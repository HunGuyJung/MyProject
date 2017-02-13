<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/jq.js">
</script>
<script type="text/javascript">
$(function(){
	$("#review-enter-button").click(function(){
		if($("#review-title").val() == ''){
			alert("제목을 입력해주세요!");
		}else{
			if($("#review-insert-combo option:selected").val() != 'default'){
				$("#review-insert-form").submit();
			}else{
				alert("상품을 선택해주세요!");
			}
		}
		
	});
	$("#review-insert-combo").click(function(){
		if($("#review-insert-combo option:selected").val() != 'default'){
			var g_img = $("#review-insert-combo option:selected").attr("g_img");
			$("#review-insert-img").attr("src","resources/upload/"+g_img);
		}else{
			$("#review-insert-img").removeAttr("src");
		}
		
	});
});
</script>
<style type="text/css">
#review-insert-body{
	width:70%;
	margin:auto;
	padding-right:20px;
}
#review-insert-box{
	width:100%;
	height:580px;
	display:inline-block;
	margin:auto;

	
}
#review-insert-table{
	border: 1px solid #ddd;
	padding-right: 50px;
}
#review-insert-title{
	padding: 30px;
}
#review-insert-button{
	padding:30px;
	margin-top:30px;
	width:100%;
}
</style>
</head>
<body>
<div id="review-insert-body">
	<form method="post" id="review-insert-form" action="insertReview.do">
	<input type="hidden" name="m_no" value="${m_no }">
	<div id="review-insert-title">
		<h2 align="center">리뷰 등록</h2>
	</div>
	<div id="review-insert-box">
		<table id="review-insert-table" class="table">
			<tr>
				<td width="120px"style="border-right: 1px solid #ddd; vertical-align: middle;" align="center"><strong>제목</strong></td>
				<td><input type="text" style="width:100%;border: none;" size="20px" name="title" id="review-title"></td>
			</tr>
			<tr height="130px">
				<td width="120px"style="border-right: 1px solid #ddd; vertical-align: middle;" align="center"><strong>등록할 상품</strong></td>
				<td style="vertical-align: middle; padding:20px;">
				 <select id="review-insert-combo" name="g_code">
				 	<option selected="selected" value="default" id="default-combo">상품을 선택해주세요.</option>
					<c:forEach var="b" items="${list }">
						<option value="${b.g_code }" g_img="${b.g_img }">[${b.brand}] ${b.g_name }</option>
					</c:forEach>
					
				</select>
				&nbsp;&nbsp;<img id="review-insert-img" width="auto" height="100">
				</td>
				
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="gray">후기를 입력해주세요.</font></td>
			</tr>
			<tr>
				<td height="350px" colspan="2">
					<textarea maxlength="300" rows="15" cols="160" name="content" style="border:none;" id="review-content"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="review-insert-button">
	<center>
		<button type="button" class="btn btn-default btn-lg" id="review-enter-button">등록</button>
		&nbsp;&nbsp;<button type="button" class="btn btn-default btn-lg" id="review-back-button">뒤로</button>
	</center>
	</div>
	</form>

</div>

</body>
</html>