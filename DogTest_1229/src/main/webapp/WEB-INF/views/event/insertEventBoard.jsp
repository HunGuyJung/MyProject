<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- 부트스트랩 불러오기1-->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<!-- 부트스트랩 불러오기2: jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- 부트스트랩 불러오기3: 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="resources/js/bootstrap.min.js"></script>
</head>
<body>
<h2>이벤트 게시물 등록</h2><hr>
<form action="insertEventBoard.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="m_no" value="${m_no }">
	&nbsp;&nbsp;&nbsp;&nbsp;제목:&nbsp;&nbsp;<input type="text" name="title" placeholder="제목" style="column-span: 30px"><br>
  	&nbsp;&nbsp;&nbsp;&nbsp;내용:<br>
	<textarea name ="content" class="form-control" col="4" rows="10" placeholder="내용"></textarea><br>
	&nbsp;&nbsp;&nbsp;&nbsp;관련상품: &nbsp;&nbsp;<input type="text" name="g_code"><br>
	&nbsp;&nbsp;&nbsp;&nbsp;이미지 파일: &nbsp;&nbsp;&nbsp;&nbsp;<input type="file" name="uploadFile" ><br>
	&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="등록" class="btn btn-default"> &nbsp;<input type="reset" value="취소" class="btn btn-default"> &nbsp;
</form>
</body>
</html>