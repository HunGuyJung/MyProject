<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 부트스트랩 불러오기1-->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<!-- 부트스트랩 불러오기2: jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- 부트스트랩 불러오기3: 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<title>Insert title here</title>
<style type="text/css">
#event-body{
	width:100%;
	margin-top: 50px;
}
#event-table{
	margin-top:20px;
	width:80%;
}
#event-search{
	width:100%;
}
#event-write-button{
	float:right;
	margin-bottom:20px;
}
</style>
<script src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#event-write-button").hide();
		
		var m_level = $("#m_level").val();
		//alert("m_level:" + m_level);
		
	 	if( m_level == null || m_level == "" || m_level != 0 ){
			$("#event-write-button").hide();
		}else{
			$("#event-write-button").show();
		}
	});
</script>
</head>
<body>
<input id="m_no" type="hidden" value="${m_no }">
<input id="m_level" type="hidden" value="${m_level }">
<div align="center" id="event-body">
	<div id="event-table">
		<div id="event-search">
		<form action="listEventBoard.do" method="post" class="form-inline">
			<select name="eventSearchField">
				<option value = "title" class="form-control">글제목</option>
			</select>
			<input type="text" name="eventKeyword">&nbsp;<input type="submit" value="검색" class="btn btn-default">
		</form>
			
		</div>
		<div id="event-write-button">
				<a href="insertEventBoard.do" class="btn btn-default">글쓰기</a>
		</div>
	<br>
		<table width="100%" class="table table-hover">
			<tr>
				<td><a href="listEventBoard.do?eventOrderField=b_no">글번호</a></td>
				<td><a href="listEventBoard.do?eventOrderField=title">글제목</a></td>
				<td><a href="listEventBoard.do?eventOrderField=create_time">작성일</a></td>
				<td><a href="listEventBoard.do?eventOrderField=hit">조회수</a></td>
			</tr>
		<c:forEach var="ebVo" items="${eBList }">
			<tr>
				<td detail="${ebVo.b_no }">${ebVo.b_no }</td>
				<td><a href="detailEventBoard.do?b_no=${ebVo.b_no }">${ebVo.title }</a></td>
				<td>${ebVo.create_time }</td>
				<td>${ebVo.hit }</td>
			</tr>	
		</c:forEach>
		</table>
		<hr>
		<center>${eventPageStr }</center>
	</div>
</div>
</body>
</html>