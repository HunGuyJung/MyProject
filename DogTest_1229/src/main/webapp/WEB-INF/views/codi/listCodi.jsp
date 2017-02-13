<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#codi-img-file{
    	width:auto;
    	height:auto;
    	min-height:490px;
    	max-height:490px;
 
    }
	#list-codi-body{
		padding:20px;
		margin:20px;
	}
	#list-codi-thumbnail{
		padding:20px;

	}
	#list-codi-title{
		width:100%;
		height:70px;
		margin:15px;	
	}
	#list-codi-insert-button{
		margin:20px;
		height:35px;
		width:100%;
	}
	#list-codi-insert{
		width:140px;
		height:40px;
	}
</style>
</head>
<body>
<div id="list-codi-body" class="row">
			
			<div id="list-codi-title">
			<center><h4><b>DOGFOOTSHOP 코디</b></h4></center>
			<center><h5><font color="gray">나만의 코디를 다른 사람들과 공유해보세요!</font></h5></center>

	     	</div>
	    	<c:if test="${not empty m_no }"><div id="list-codi-insert-button">
		     		<center><a href="insertCodi.do"><button type="button" class="btn btn-default" id="list-codi-insert"><font size="3px">코디 등록</font></button></a></center>
		     	</div></c:if>
			<c:forEach var="c" items="${list }">
     			<div class="codi_img col-sm-6 col-md-4">
     			
     				<a href="detailCodi.do?b_no=${c.b_no }" class="thumbnail" id="list-codi-thumbnail">
     					<img src="resources/upload/${c.b_img }" id="codi-img-file">
     				</a>
     				<div class="caption">
     				<center>${c.title }</center>
     				<center><font color="gray">${c.id }님</font></center>
     				<center><img src="resources/upload/like.png" width="40" height="auto">${c.like_count }</center>
     				<br><br>
     				</div>
     			
     			</div>
     		</c:forEach>
</div>
</body>
</html>