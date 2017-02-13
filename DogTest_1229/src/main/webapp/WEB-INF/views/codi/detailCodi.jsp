<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#detail-codi-body{
		width:90%;
		height:100%;
		margin: 0px auto;
		position:relative;
	//	border: 1px solid #bcbcbc;
	}
	#detail-codi-container{
		margin-top:40px;
		width:98%;
		height:auto;
		padding:20px;
		margin-bottom:20px;
		
	}
	#detail-codi-text{
		margin-top: 70px;
		float:left;
		width:24%;

	}
	#detail-codi-title{
		margin-top:90px;
	}
	#detail-codi-writer{
		margin-top:20px;
		color:gray;
	}
	#detail-codi-content{
		margin-top:20px;
		color:gray;
	}
	#detail-codi-button{
		margin-top:120px;
		width:80%;
	}
	#codi-update-button{
		width:100%;
		height:auto;
	}
	#detail-codi-delete{
		margin-top:20px;
	}
	#codi-delete-button{
		width:100%;
		height:auto;
	}
	#detail-codi-back{
		margin-top:30px;
	}
	#codi-back-button{
		width:100%;
		height:auto;
	}
	.thumbnail{
		height:100%;
	}
	.img-responsive{
		width:100%;
	}
	#detail-codi-table{
		height:auto;
		width:80%;
	}
	#detail-codi-goods{
		float:left;
		width:100%;
		height:auto;
	}
	#detail-codi-box{
		margin-left:90px;
		width:40%;
		margin-right:150px;
		float:left;
		min-height:550px;
		height:auto;
	}
	#detail-codi-table-tr{
		height:200px;
	}
	.detail-codi-table-td{
		height:100px;
	}
	#detail-like{
		padding-top:50px;
	}
	#detail-word{
		height:20px;
	}
</style>
<script type="text/javascript" src="resources/jq.js">
</script>
<script type="text/javascript">
$(function(){
	$("#like-img").mouseover(function(){
		$("#detail-word").html("좋아요!");
	});
	$("#like-img").mouseleave(function(){
		$("#detail-word").html("");
	});
	$("#like-img").click(function() {
		//alert("좋아요!");
		if($("#m_no").val()>=1){
			var m_no = $("#m_no").val();
			var b_no = $("#b_no").val();
			$.getJSON({
				url: "boardLike.do",
				data: {m_no:m_no,b_no:b_no},
				success:function(data){
					var lc = eval(data);
					$("#like_count").text(lc);
					alert("좋아요를 누르셨습니다!");
			}});
		}else{
			alert("로그인이 필요합니다!");
		}
	});
})
</script>
</head>
<body>
<center>
<div id="detail-codi-body">
	<div id="detail-codi-container">
		<div class="panel panel-default" id="detail-codi-box" align="center">
			<center><div class="panel-body col-md-4" align="center" style="position: relative; vertical-align: middle;">
				<center><img src="resources/upload/${vo.b_img }" width="510" height="auto"></center>
			</div></center>
		</div>
		<input type="hidden" value="${vo.b_no }" id="b_no">
		<div id="detail-codi-text">
			<div id="detail-codi-title">
				<h2>${vo.title }</h2>
			</div><br><br>
			<div id="detail-codi-writer">
				<h4><font color="gray">${id }님</font></h4>
			</div><br>
			<div id="detail-codi-content">
				${vo.content }
			</div>
			<div id="detail-like">
			
			<img src="resources/upload/like.png" width="70" height="auto" id="like-img"> <font id="like_count" size="3">${vo.like_count }</font>
			<div id="detail-word"></div>
			</div>
			
			<div id="detail-codi-button">
			<c:if test="${m_no == vo.m_no }">
				<div id="detail-codi-update">
					<a id="codi-update-button" type="button" class="btn btn-default" href="updateCodi.do?b_no=${vo.b_no }"><font size='4'>수정하기</font></a>
				</div>
				<div id="detail-codi-delete">
					<a id="codi-delete-button" type="button" class="btn btn-danger" href="deleteCodi.do?b_no=${vo.b_no }"><font size='4'>삭제</font>&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
				</div>
			</c:if>
				
				<div id="detail-codi-back">
					<button id="codi-back-button" type="button" class="btn btn-default" onclick="location.href='listCodi.do'"><font size='4'>뒤로</font></button>
				</div>
			</div>
			
		</div>
	</div>
	<div id="detail-codi-goods">
		<br>
		<table id="detail-codi-table" width="600" class="table">
			<tr height="10px">
				<td align="center">모자</td>
				<td align="center">아우터</td>
				<td align="center">상의</td>
				<td align="center">하의</td>
				<td align="center">신발</td>
			</tr>
			<tr id="detail-codi-table-tr">
				<td class="detail-codi-table-td" id="td_cap" align="center" width="100" height="100"><c:if test="${not empty vo_cap.g_img }"><a href="detailGoodsBoard.do?b_no=${ggno_1 }" class="thumbnail"><img src="resources/upload/${vo_cap.g_img }" width="auto" height="100"></a></c:if></td>
				<td class="detail-codi-table-td" id="td_outer" align="center" width="100" height="100"><c:if test="${not empty vo_outer.g_img }"><a href="detailGoodsBoard.do?b_no=${ggno_2 }" class="thumbnail"><img src="resources/upload/${vo_outer.g_img }" width="auto" height="100"></a></c:if></td>
				<td class="detail-codi-table-td" id="td_top" align="center" width="100" height="100"><c:if test="${not empty vo_top.g_img }"><a href="detailGoodsBoard.do?b_no=${ggno_3 }" class="thumbnail"><img src="resources/upload/${vo_top.g_img }" width="auto" height="100"></a></c:if></td>
				<td class="detail-codi-table-td" id="td_bottom" align="center" width="100" height="100"><c:if test="${not empty vo_bottom.g_img }"><a href="detailGoodsBoard.do?b_no=${ggno_4 }" class="thumbnail"><img src="resources/upload/${vo_bottom.g_img }" width="auto" height="100"></a></c:if></td>
				<td class="detail-codi-table-td" id="td_shoes" align="center" width="100" height="100"><c:if test="${not empty vo_shoes.g_img }"><a href="detailGoodsBoard.do?b_no=${ggno_5 }" class="thumbnail"><img src="resources/upload/${vo_shoes.g_img }" width="auto" height="100"></a></c:if></td>
			</tr>
		</table>

	</div>
</div>
</center>
</body>
</html>