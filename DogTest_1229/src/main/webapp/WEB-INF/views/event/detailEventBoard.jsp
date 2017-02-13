<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>이벤트 게시물 상세</h2><hr>
글번호: ${ebVo.b_no }<br>
제목: ${ebVo.title }<br>
내용: <br>
<textarea rows="10" cols="60" disabled="disabled">${ebVo.content }</textarea><br>
조회수: ${ebVo.hit }<br>
등록일: ${ebVo.create_time }<br>
상품코드: ${ebVo.g_code}<br>
첨부사진: <a href="down.do?b_no=${ebVo.b_no }">${ebVo.b_img }</a><br>
<a class="btn btn-default" href="updateEventBoard.do?b_no=${ebVo.b_no }">수정</a>&nbsp;&nbsp;
<a class="btn btn-default" href="deleteEventBoard.do?b_no=${ebVo.b_no }">삭제</a>&nbsp;&nbsp;
</body>

</body>
</html>