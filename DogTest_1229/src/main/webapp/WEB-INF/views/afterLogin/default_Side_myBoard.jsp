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
	<style>
      #jb-container {
        width: 100%;
        min-height:80%;
        margin: 0px auto;
        //border: 1px solid #bcbcbc;
      }
      #jb-login {
        height: 40px;
        //border: 1px solid #bcbcbc;
        opacity : 0.90;
        background-color: white;
      }
      #subjb-topImage{
      
      	float: left;
      	margin-left:750px;
      	margin-top: 9px;
      }
      #subjb-Login{
      	 float: right;
      	 padding: 13px;
      }
      #jb-header {
      	position:relative;
        height: 280px;
        margin-bottom: 5px;
        //border: 1px solid #bcbcbc;
		text-align: center;
      }
      .subjb-header{
      	position:absolute; 
      	bottom: 20px;
		display: inline-block;
      }
      #menu{
      	width: 200px;
      	height: 40px
      }
       #jb-banner {
        height: 300px;
        margin-bottom: 20px;
       // border: 1px solid #bcbcbc;
      }
      #jb-content {
        width: 76%;
        height: auto;
        padding: 20px;
        margin-bottom: 20px;
        float: right;
        //border: 1px solid #bcbcbc;
      }
     #jb-sidebar {
        width: 22%;
        min-height: 100%;
        height:auto;
        padding-left:25px;
        padding-top: 100px;
        background:#141212;
        color:silver;
        float: left;
        position: fixed;
       // border: 1px solid #bcbcbc;
      }
      .myPage-sidebar{
      	padding-top:20px;
      	color:white;
      }
      #jb-footer {
        clear: both;
        padding: 20px;
      //  border: 1px solid #bcbcbc;
      }
      @media screen and (max-width:480px) {
        #jb-container {
          width: auto;
        }
        #jb-content {
          float: none;
          width: auto;
        }
        #jb-sidebar {
          float: none;
          width: auto;
        }
      }
      a{
      	text-decoration: none;
      	color: black;
      	
      }
/*       //===================12/20에 새로 추가===================== */
      .myPage-sidebar{
      	font-size: 15px;
      }
    </style>
	<script type="text/javascript" src="resources/jq.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#sns_type_check").click(function(){
				var checked = $(this).attr("checked");
				if( checked == "checked"){
					$("#sns_type").attr("disabled","");
					$("#sns_id").attr("disabled","");					
				}else{
					$("#sns_type").attr("disabled","disabled");
					$("#sns_id").attr("disabled","disabled");
				}
			});
		});

	</script>
    
    
  </head>
  <body>
<input id="m_no" type="hidden" value="${m_no }">
<input id="m_level" type="hidden" value="${m_level }">
  <div id="jb-container">
	<div id="jb-sidebar">
        <h2>Sidebar</h2>
        <ul class="nav nav-pills nav-stacked">
		  <li role="presentation"><a href="myInfoPage.do" type="button" id="myInfo" class="myPage-sidebar">개인정보</a></li>
		  <li role="presentation"><a href="listMyBoard.do?index=1" type="button" id="myBoard" class="myPage-sidebar">게시글내역</a></li>
		  <li role="presentation"><a href="myBasket.do" type="button" id="myBasket" class="myPage-sidebar">장바구니</a></li>
		  <li role="presentation"><a href="myPayList.do?index=1" type="button" id="myPay" class="myPage-sidebar">결재/배송 내역</a></li>
		</ul>
    </div>
    <div id="jb-content">
    <h2>나중에 지울것!: 디폴트값 코디글만 먼저</h2>    	
      	<div id="my-table">
		<div id="myBoard-search">
		<form action="listMyBoard.do" method="post" class="form-inline">
			<select name="b_type">
				<option value = "1" class="form-control" selected="selected">코디</option>
				<option value = "2" class="form-control">후기</option>
			</select>
			<select name="myBoardSearchField">
				<option value = "title" class="form-control">제목</option>
				<option value = "content" class="form-control">내용</option>
			</select>
			<input type="text" name="myBoardKeyword">&nbsp;<input type="submit" value="검색" class="btn btn-default">
		</form>
		</div>
	<br>
		<table width="100%" class="table table-hover">
			<tr>
				<td><a href="listMyBoard.do?myBoardOrderField=b_no">글번호</a></td>
				<td><a href="listMyBoard.do?myBoardOrderField=title">글제목</a></td>
				<td><a href="listMyBoard.do?myBoardOrderField=create_time">작성일</a></td>
				<td><a href="listMyBoard.do?myBoardOrderField=hit">조회수</a></td>
			</tr>
		<c:forEach var="mbVo" items="${mbList }">
			<tr>
				<td detail="${mbVo.b_no }">${mbVo.b_no }</td>
				<td>
					<c:if test="${mbVo.b_type == 1 }"><a href="detailCodi.do?b_no=${mbVo.b_no }">${mbVo.title }</a></c:if>
					<c:if test="${mbVo.b_type == 2 }"><a href="detailReview.do?b_no=${mbVo.b_no }">${mbVo.title }</a></c:if>
				</td>
				<td>${mbVo.create_time }</td>
				<td>${mbVo.hit }</td>
			</tr>	
		</c:forEach>
		</table>
		<hr>
		<center>${myBoardPageStr }</center>
	</div>
    </div>
  </div>
  </body>
</html>