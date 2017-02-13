<%@page import="java.util.Date"%>
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
<style type="text/css">
#nav{
	width:100%;
	height: 50px;
	background: white;
	
	vertical-align: middle;
}
#nav-logo{
	padding-top:5px;
	padding-left:1px;
	float:left;
	margin:0px;
}
#nav-menu{
	float:left;
	padding-top:8px;
	border-style: none;
   	background: none;
   	margin:0px;
}
#nav-menu ul {
	list-style-type:none;
}
#nav-menu ul li {
	display:inline;
}
#nav-menu ul li .btn{
	border-style: none;
   	background: none;
}
#nav-menu ul li .btn:HOVER{
	color: orange;
}
#nav-login{
	float:right;
	padding-top:8px;
	padding-right: 7px;
}
#nav-login ul{
	list-style-type:none;
}
#nav-login ul li {
	display:inline;
}
#nav-login ul li .btn{
	border-style: none;
   	background: none;
}
#nav-login ul li .btn:HOVER{
   	color: orange;
}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function(){
		
	///////////////////////////////////////// 현규 로고 클릭식 메인 & 관리자 이동 ///////////////////////
	
		var count = 0;
		$("#main_link").on("mouseenter click",function(e){
			if(e.type == "mouseenter")
			{ 
				count = count+1 ; 
				//alert(count);	
			}
			else if(e.type == "click")
			{ 
				if(count == 3 && $("#mVo").val()==0)
				{
					alert("관리자 페이지");
					$(location).attr('href',"manager_Main.do");
				}else{
					alert("메인 페이지");
					$(location).attr('href',"front.do");
				}
			}
		});
	
	});
</script>
</head>
<body>
<div id="nav">
	<input type="hidden" id="mVo" value="${mVo.m_level }">
	<input type="hidden" id="m_no" value="${m_no }">
	<div id="nav-logo">
		<img id="main_link" src="resources/upload/logo.png" width="280" height="auto">
	</div>
	<div id="nav-menu">
	<ul>
		<li><a class="btn btn-default" href="manager_Main.do">MAIN</a>|</li> 
		<li><a class="btn btn-default" href="manager_Sales.do">SALES</a>|</li>
		<li><a class="btn btn-default" href="manager_Members.do">MEMBERS</a>|</li> 
		<li><a class="btn btn-default" href="manager_Goods.do">GOODS</a>|</li>
		<li><a class="btn btn-default" href="manager_Order.do">ORDER</a></li> 
	</ul>
	</div>
</div>
</body>
</html>