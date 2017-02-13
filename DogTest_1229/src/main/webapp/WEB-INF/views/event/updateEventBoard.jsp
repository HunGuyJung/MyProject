<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>게시물 수정</h2>
<hr>
<form action="updateEventBoard.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="b_no" value="${ebVo.b_no }">
제목: <input type="text" name="title" value="${ebVo.title }"><br>
내용: <br>
<textarea rows="10" cols="60" name="content">${ebVo.content }</textarea><br>
<input type="hidden" name="b_img" value="${ebVo.b_img }">
파일: <input type="file" name="uploadFile"><br>
<input type="submit" class="btn btn-default" value="수정">
<input type="reset" class="btn btn-default" value="취소">
</form>
</body>
</html>