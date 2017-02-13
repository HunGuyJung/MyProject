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
			
			$("#updateMyInfoForm").find("input").attr("disabled","disabled");  //  모든 input을 disabled로 바꿔놓기!(이따가 비밀번호 입력확인시 제대로 돌아오기)
			$("#addr1").removeAttr("disabled");  //  주소 API반환값 넣기 위해서 disabled지워주기!
			
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
			
			//  수정버튼을 눌르면
			$("#updateMyInfoBtn").click(function(){
				var inputArray = $("#updateMyInfoForm").find("input");
				var count=0;				
				
				$(inputArray).each(function(){
					var a = $(this).val();
					if(a == null || a=="" ){
						count +=1;
					}
				});
				
				if(count > 1){
					alert(count);
					alert("수정정보가 부족합니다.");
				}else{
					$("#updateMyInfoForm").attr("action","updateMyInfo.do");
					$("#updateMyInfoForm").submit();
				}
			});
			
			
			//  비밀번호 확인을 누르면 모든 창들이 disabled에서 풀리기
			$("#pwdConfirmBtn").click(function(){
				
				var pwd = $("#pwdConfirm").val();
				
				
				$.ajax(
						{
							url:"checkPwd.do",
							data:{pwd: pwd},
							success:function(data){
								//alert(data);
								if(data =="ok"){
									alert("비밀번호확인OK!");
									$("#updateMyInfoForm").find("input").removeAttr("disabled");
									//  alert("삭제함!");
								}else{
									alert("비밀번호오류!");
								}
							}
						}	
				);
				$("input").attr("disabled","");
				
			});
				
			
				
		});

	</script>
 	<!-- 주소 API부분 시작-->
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<!-- <script src="//apis.daum.net/maps/maps3.js?apikey=발급받은 API KEY를 사용하세요&libraries=services"></script> -->
	<script>
	    /* var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	        mapOption = {
	            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
	            level: 5 // 지도의 확대 레벨
	        };
	
	    //지도를 미리 생성
	    var map = new daum.maps.Map(mapContainer, mapOption);
	    //주소-좌표 변환 객체를 생성
	    var geocoder = new daum.maps.services.Geocoder();
	    //마커를 미리 생성
	    var marker = new daum.maps.Marker({
	        position: new daum.maps.LatLng(37.537187, 127.005476),
	        map: map
	    }); */
	
	    function sample6_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var fullAddr = data.address; // 최종 주소 변수
	                var extraAddr = ''; // 조합형 주소 변수
	
	                // 기본 주소가 도로명 타입일때 조합한다.
	                if(data.addressType === 'R'){
	                    //법정동명이 있을 경우 추가한다.
	                    if(data.bname !== ''){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있을 경우 추가한다.
	                    if(data.buildingName !== ''){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	                }
	
	                // 주소 정보를 해당 필드에 넣는다.
	                document.getElementById("addr1").value = fullAddr;                
	            }
	        }).open();
	    }
	</script>
	<!-- 주소 API부분 끝-->   
    
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
      	<div id = "myInfoShow">
        	<h1>개인정보</h1><hr>
        	<table>
        	<tr><td><input class="form-control" type="password" name ="pwd" id="pwdConfirm" placeholder="개인정보 수정시 pw입력!"></td><td>&nbsp;&nbsp;<button id="pwdConfirmBtn" class="btn btn-default">비밀번호 확인</button></td></tr>
        	</table>
        	<!-- <button id="pwRevise" class="btn btn-primary">비밀번호 변경</button> -->
        	<form method="post" id="updateMyInfoForm">
        	<input class="form-control" id="m_no" type="hidden" name="m_no" value="${m_no }">
      		<b>
      		  <table>
	      		  <c:if test="${not empty mVo }">
	      		  <tr><td>ID:</td><td><input class="form-control" type="text" name ="id" id="id" value= "${mVo.id }"></td></tr>
	      		  <tr><td>기존PW:</td><td><input class="form-control" type="password" name ="pwd" id="pwdConfirm" value= "${mVo.pwd }"><br></td></tr>
	      		  <tr></tr>
			      <tr><td>이름:</td><td><input class="form-control" type="text" name ="m_name" value= "${mVo.m_name }"></td></tr>
			      <tr><td>연락처(핸드폰):</td><td><input class="form-control" type="text" name ="phone" value= "${mVo.phone }"></td></tr>
			      <tr><td>이메일주소:</td><td><input class="form-control" type="text" name ="e_mail" value= "${mVo.e_mail }"></td><br></tr>
			      <tr></tr>
			      <tr><td>주소:</td><td><button class="btn btn-default" id="search_add" onclick="sample6_execDaumPostcode()">주소검색</button></td></tr>
			      <tr><td></td><td><input class="form-control" type="text" name ="addr" id="addr1" value="${mVo.addr }"></td><br></tr>
			      <tr></tr>
			      <%-- <tr><br><hr><td>SNS연동:</td><td>&nbsp;&nbsp;<c:if test="${not empty mVo.sns_type }"><input class="checkbox" type="checkbox" id ="sns_type_check" name ="sns_type" checked="checked"></c:if><c:if test="${empty mVo.sns_type }"><input type="checkbox" name ="sns_type"></c:if></td></tr>
				  <tr><td>SNS종류:</td><td>&nbsp;&nbsp;
				  	<select class="form-control" name="sns_type" id="sns_type" disabled="disabled" style="background:lightslategray">
				  		<c:if test="${mVo.sns_type ==0}"><option value="" selected="selected">--선택--</option><option value="1">페이스북</option><option value="2">인스타그램</option><option value="3">카카오스토리</option></c:if>
				  		<c:if test="${mVo.sns_type ==1}"><option value="">--선택--</option><option value="1" selected="selected">페이스북</option><option value="2">인스타그램</option><option value="3">카카오스토리</option></c:if>
				  		<c:if test="${mVo.sns_type ==2}"><option value="">--선택--</option><option value="1">페이스북</option><option value="2" selected="selected">인스타그램</option><option value="3">카카오스토리</option></c:if>
				  		<c:if test="${mVo.sns_type ==3}"><option value="">--선택--</option><option value="1">페이스북</option><option value="2">인스타그램</option><option value="3" selected="selected">카카오스토리</option></c:if>
				  	</select></td></tr>
				  <tr><td>SNS아이디:</td><td>&nbsp;&nbsp;<input class="form-control" type="text" name ="sns_id"  id = "sns_id" disabled="disabled" style="background:lightslategray"></td></tr> --%>
			      <tr><td></td><td><br><input type="submit" id="updateMyInfoBtn" class="btn btn-primary" value = "수정">&nbsp;&nbsp;<input type="reset"  class="btn btn-default" value = "취소"></td></tr>
			      </c:if>
		      </table>
	    	</b>
	    	</form>
    	</div>
    </div>
  </div>
  </body>
</html>