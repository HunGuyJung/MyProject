

<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <title>CSS Tutorial | Layout - Responsive</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 부트스트랩 불러오기1-->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<!-- 부트스트랩 불러오기2: jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- 부트스트랩 불러오기3: 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/jq.js"></script>
    <script type="text/javascript">

    </script>
    <style type="text/css">
    html,body{height:100%;}
    body{margin:0;
    	background:silver;}
    #dog-wrapper{
    	height:100%;
    	width:100%;
    	position:absolute;
    	top:0px;
    	left:0px;
    }
    #blank{
    	height:50px;
    }
    #dog-header{
    margin:0px;
/*     	height:50px; */
    	position: fixed;
    	top:0px;
    	left:0px;
    	width:100%;
    	overflow:auto;
    	z-index: 1;
    }
    #dog-body{
		min-height:80%;
/* 		height:auto; */
/* 		overflow: auto; */
    }
    #dog-footer{
		height:100px;
		position: absolute;
		left:0px;
		width:100%;
    }
    #dog-footer #dog-footer-detail{
    	height:50px;
    	width:100%;
    	padding-top:50px;
    }
    </style>
  </head>
<body>
	<div id="dog-wrapper">
		<div id="blank"></div>
		<div id="dog-header">
			<jsp:include page="/WEB-INF/views/manager/manager_header.jsp"></jsp:include>
		</div>
		<div id="dog-body">
			<jsp:include page="/WEB-INF/views/manager/${page }"></jsp:include>
		</div>
<!-- 		<div id="dog-footer"> -->
<%-- 			<center><jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include></center> --%>
<!-- 		</div> -->
	</div>
</body>
</html>