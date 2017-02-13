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
	border-bottom:0.5px solid #ddd;
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
#chatBox{
	width:300px;
	height:300px;
	border: 1px;
	solid: #5e5e5e;
	overflow: auto;
}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function(){
		var m_no = $("#m_no").val();
		//alert("m_no:"+m_no);
		if( m_no != -1 && m_no != null  && m_no !="" ){
			$("#logout_a").show();
			$("#login_a").hide();
		}else{
			$("#logout_a").hide();
			$("#login_a").show();
		}
		
		//  로그인 버튼작동
		$("button[login='login_b']").click(function(){
			//alert("로그인 버튼 작동함!");
			var id = $("input[log=id]").val();
			var pwd = $("input[log=pwd]").val();
						
			if(id != null && pwd != null){
				$("#login_form").submit();
			}
			
		});
		//  로그인 버튼작동 끝
		
		//  로그아웃 a 클릭 시작
			//  <a> 링크로 logout.do 서비스 요청하게 해놨음!
		//  로그아웃 a 클릭 끝
		
		
		//  회원가입 버튼 클릭시(정의 필요!!) 시작
			//  // $.ajax( { url:"", data:{id:idValue}, success:function(){} } )
				//  아이디 중복확인 누를때 시작
					$("#compare_Id").click(function(){
						var a = $("#id").val();
						alert("새로가입할 아이디: " + a);
						if(a == null || a ==""){
							$("input[id='idChekResult']").css("color","red");
							$("input[id='idChekResult']").val("아이디로 공백은 사용불가능합니다.");
							$("#id").val("");
						}else{
							$.ajax(
									{
										url:"compareId.do",
										data: {id:a},
										success:function(data){
											var arr = eval("("+data+")");
											alert(arr);
											//  아이디로 빈칸이나 null인체 아이디확인 눌렀을경우
											if(arr == null){
												$("input[id='idChekResult']").css("color","darkgreen");
												$("input[id='idChekResult']").val("아이디: " + a + "은 사용"+ "가능합니다.");
											}else{  //  아이디로 빈칸이 아닌게 입력되었다고 하였을때, 아래진행 
												$(arr).each(function(index, item){
													var compareIdResult = item["id"];
													alert("compareIdResult:"+compareIdResult);
													
													
													if( compareIdResult == a){  //  db에 이미 입력된 id가 있다면 사용불가
														$("input[id='idChekResult']").css("color","red");
														$("input[id='idChekResult']").val("아이디: " + a + "은 사용"+ "불가능합니다.");
														$("#id").val("");
													}else{  //  db에 이미 입력된 id가 없기때문에 사용가능
														$("input[id='idChekResult']").css("color","darkgreen");
														$("input[id='idChekResult']").val("아이디: " + a + "은 사용"+ "가능합니다.");
													}	
												});
											}
										}
									}
								  );
						}
					});
					
				//  아이디 중복확인 누를때 종료
				//  아이디 입력칸벗어난이후, 다시 입력시 시작
					$("input[id='id']").keyup(function(){
						$("input[id='idChekResult']").val("");
					});
			
			
				//  비밀번호 유효성검사실행 시작
					//  비밀번호 입력칸벗어나면 비밀번호 유효성 체크 함수 시작
					$("input[id='pwd']").blur(function(){
						var pw = $.trim( $("input[id='pwd']").val() );
						var num = pw.search(/[0-9]/g);			
						var eng = pw.search(/[a-z]/ig);
						var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩';:₩/?]/gi);
						var str_space = /\s/;  // 공백체크
						var flag = true;
							
						if(pw.length < 8 || pw.length > 12){
							$("input[id='pwdChek']").css("color","red");
							$("input[id='pwdChek']").val("8~12자리이내 입력바랍니다.");
							$("input[id='pwd']").focus();
							flag = false;
						}
						if(str_space.exec($("input[id='pwd']").val())){
							$("input[id='pwdChek']").css("color","red");
							$("input[id='pwdChek']").val("공백업이 입력바랍니다.");
							$("input[id='pwd']").focus();
							flag = false;
						}
						if(num < 0 || eng < 0 || spe < 0 ){
							$("input[id='pwdChek']").css("color","red");
							$("input[id='pwdChek']").val("영문/숫자/특수문자 혼합입력바랍니다.");
							$("input[id='pwd']").focus();
							flag = false;
						}
						if(flag== true && pw != null ){
							$("input[id='pwdChek']").css("color","darkgreen");
							$("input[id='pwdChek']").val("유효성검사이상없습니다.");
							
						}
					});
					//  비밀번호 입력칸벗어나면 비밀번호 유효성 체크 함수 종료
				
					//  비밀번호 입력칸벗어난이후, 다시 입력시 시작
					$("input[id='pwd']").keyup(function(){
						$("input[id='pwdChek']").val("");
						$("input[id='pwdChek2']").val("");
					});
					$("input[id='pwd2']").keyup(function(){
						$("input[id='pwdChek2']").val("");
					});
					//  비밀번호 입력칸벗어난이후, 다시 입력시 종료
					
					//  비밀번호 재확인버튼 누를시
					$("#check_pwd").click(function(){
						if($("input[id='pwd']").val() == $("input[name='pwd2']").val()){
							$("input[id='pwdChek2']").css("color","darkgreen");
							$("input[id='pwdChek2']").val("비밀번호 동일합니다.");
							
						}else{
							$("input[id='pwdChek2']").css("color","red");
							$("input[id='pwdChek2']").val("비밀번호 불일치합니다.");
							
						}
					});
				//  비밀번호 유효성검사실행 종료
			
				//  회원가입시 sns연동 체크박스 클릭시 시작
				$("#myJoinSnsCheck").change(function(){
					//  체크되면 
					if($("#myJoinSnsCheck").is(":checked")){
						$("#sns_type").css("background","white");
						$("#sns_id").css("background","white");
						$("#sns_type").removeAttr("disabled");
						$("#sns_id").removeAttr("disabled");
					}else{
					//  체크해제시 초기화
						$("#sns_type").css("background","lightslategray ");
						$("#sns_id").css("background","lightslategray ");
						$("#sns_type").attr("disabled","disabled");
						$("#sns_id").attr("disabled","disabled");
						$("#sns_type option:eq(0)").attr("selected", "selected");
						//alert($("#sns_type option:eq(0)").val());
						$("#sns_type option:eq(0)").attr("selected","selected");
						$("#sns_id").val("");
					}
				});
				//  회원가입시 sns연동 체크박스 클릭시 종료
			
				
				//  회원가입 누를때 시작	
				$("button[join='join_b']").click(function(){
					
					//  올바른, 회원아이디, 비밀번호1, 비밀번호2, 이름, 연락처, 이메일, 주소를 가지고 있다면! submit하기
					var flag1 = true; var flag2 = true; var flag3 = true; var flag4 = true;
					var id = $("#id").val();
					var idChekResult = $("input[id='idChekResult']").val();
					var pwd = $("#pwd").val();
					var pwd2 = $("#pwd2").val();
					var m_name = $("#m_name").val();
					var phone = $("#phone").val();
					var e_mail = $("#e_mail").val();
					var addr = $("#addr").val() + $("#addr2").val();
					var sns_type = $("#sns_type option:selected").val();
					var sns_id = $("#m_name").val();
					
					if( idChekResult == null || idChekResult == "" || id == null || id == "" ){
						alert("아이디 중복확인 바랍니다");
					}
					else if( pwd != pwd2 || pwd == null || pwd == "" || pwd2 == null || pwd2 == ""){
						alert("비밀번호 재확인바랍니다.");

					}else if( m_name == "" || m_name == null || phone == "" || phone == null || e_mail == "" || e_mail == null || addr == "" || addr == null ){
						alert("이름/핸드폰/전화번호/주소를 확인바랍니다.");
						//alert("m_name: " + m_name);
						//alert("phone: " + phone);
						//alert("e_mail: " + e_mail);
						//alert("addr: " + addr);
						//alert("myJoinSnsCheck" + $("#myJoinSnsCheck").attr("checked"));
						
						
					}else if( $("#myJoinSnsCheck").is(":checked")  ){
						if( sns_type == "" || sns_id == "" || sns_type == null  || sns_id == null){
							alert("sns정보를 확인바랍니다.");
						}else{
							//  회원가입양식 다 맞으면 회원가입 버튼기능
							$("form[join='joinForm']").attr("action","join.do");
							//alert($("form[join='joinForm']").attr("action"));
							$("form[join='joinForm']").submit();
						}
					}else{
						//  회원가입양식 다 맞으면 회원가입 버튼기능
						$("form[join='joinForm']").attr("action","join.do");
						//alert($("form[join='joinForm']").attr("action"));
						$("form[join='joinForm']").submit();
					}
				});	
				//  회원가입 누를때 끝
				
				
			//  회원가입 버튼 클릭시(정의 필요!!) 끝
				
			//  메일발송 버튼 클릭시 시작
			$("button[mail='mail_b']").click(function(){
				
				var inputArray = $("form[mail='mailForm']").find("input");
				var check = 0;
				inputArray.each(function(){
					if( $(this).val() == null || $(this).val() == "" ){
						alert($(this).attr("name"));
						check += 1;
						alert("check:" + check);
					}
				});
				if(check == 0){
					$("form[mail='mailForm']").attr("action","mail1.do");
					//alert($("form[mail='mailForm']").attr("action"));
					alert("메일이 발송되었습니다.!");
					$("form[mail='mailForm']").submit();
				}else{
					alert("최종check:" + check);
					alert("메일발송시 필요한 정보를 다시입력해주십시오!");
				}
			});
			//  메일발송 버튼 클릭시 끝
			
///////////////////////////////////////// 현규 로고 클릭식 메인 & 관리자 이동 ///////////////////////
				
// 				var count = 0;
// 				$("#main_link").on("mouseenter click",function(e){
// 					if(e.type == "mouseenter")
// 					{ 
// 						count = count+1 ; 
// 						//alert(count);	
// 					}
// 					else if(e.type == "click")
// 					{ 
// 						if(count == 3 && $("#m_level").val()==0)
// 						{
// 							alert("관리자 페이지");
// 							$(location).attr('href',"manager_Main.do");
// 						}else{;
// 							alert("메인 페이지");
// 							$(location).attr('href',"front.do");
// 						}
// 					}
// 				});

		var mous_count = 0;
		$("#main_link").mouseover(function() {
			mous_count++;
			//alert("마우스 오버 : "+mous_count+" 회");
		});
		
		$("#main_link").click(function() {
			if(mous_count==3)
			{
				if( $("#m_level").val()==0 && $("#m_no").val()!=""  )
				{
					alert("관리자 페이지 이동! "+mous_count);
					$(location).attr('href',"manager_Main.do");
				}else{
					alert("메인 페이지로 이동! "+mous_count);
					$(location).attr('href',"front.do");
				}
			}else{
				alert("메인 페이지로 이동! "+mous_count);
				$(location).attr('href',"front.do");
			}
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
	
	    function sample5_execDaumPostcode() {
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
	                document.getElementById("addr").value = fullAddr;                
	            }
	        }).open();
	    }
	</script>
<!-- 주소 API부분 끝-->
	<!-- 채팅 기능 시작 -->
	<script src="http://203.236.209.152:3000/socket.io/socket.io.js"></script>
	<script>
		$(function(){
			var id = $("#chatId").val();
			//alert(totalData);
			if(id != null && id != "" && id !="null"){
				//alert("chat 아이디:" + id);
				chatConnect(id);  //  고객 ID만 넣으면 채팅접속 시작
			}						
		});
		
		//  임의 function chatConnect 정의 시작
		function chatConnect(id){
			
			var socket = io.connect('http://203.236.209.152:3000');    //  첫연결   -> 이부분때문에 소켓이 연결되는것임!
			socket.on('conn', function(data){                             
 				//console.log(data);
 				//alert("jsp상의 socket.on: "+data);
 				$("#chatCount").val(data);
 				
			});

			//  엔터 누를때 작동 시작!
			$("#chatInput").bind('keypress', function(e){
  				if(e.keyCode == 13){
    				var text = $("#chatInput").val();
    				//alert(name+' : '+ text);
    				socket.emit('input', id+' : '+ text, id ); 
    				$("#chatInput").val('');
  				}
			});
			//  엔터 누를때 작동 끝!
			
			//  보내기 버튼 누를때 작동 시작!
			$("#chatSendBtn").click(function(){
				var text = $("#chatInput").val();
   				//alert(name+' : '+ text);
   				socket.emit('input', id+' : '+ text, id );             
   				$("#chatInput").val('');
			});
			//  보내기 버튼 누를때 작동 끝!
			
			//	메시지를 받은 다음의 이벤트임
			socket.on('output', function(message, totalData){
			//  $("#chatBox").append(totalData);  //  로그인이 제대로 되었다는 전제하에 화면에 chat시작 알림!
			//  $("#chatBox").append('<div>'+ totalData +'</div>');
			
			
				//  alert(data);
				//alert(totalData);
				
				$("#chatBox").append('<div>'+ message +'</div>');
				$("#chatBox").scrollTop($("#chatBox")[0].scrollHeight+20);  //  최근 메시지줄로 초점맞쳐주기
			});
		};
		//  임의 function chatConnect 정의 끝
	</script>				
	<!-- 채팅 기능 끝 -->

</head>
<body>
<div id="nav">
	<input type="hidden" id="m_no" value="${m_no }">
	<input type="hidden" id="mVoId" value="${mVo.id }">
	<input type="hidden" id="m_level" value="${m_level }">
	<input type="hidden" id="chatCount">
	<div id="nav-logo">
		<img id="main_link" src="resources/upload/logo.png" width="280" height="auto">
	</div>
	<div id="nav-menu">
	<ul>
		<li><a class="btn btn-default" href="listGoodsBoard.do">SHOPPING</a>|</li> 
		<li><a class="btn btn-default" href="listCodi.do">CODI</a>|</li>
		<li><a class="btn btn-default" href="listEventBoard.do?index=1">EVENT</a>|</li> 
		<li><a class="btn btn-default" href="listReview.do">REVIEW</a>|</li>
		<li><a class="btn btn-default" href="myInfoPage.do">MY PAGE</a></li> 
	</ul>
	</div>
	<div id="nav-login">
	<ul>
		<li id="login_a"><a type="button" data-toggle="modal" data-target="#myLoginModal" log="login_a" class="btn btn-default">LOGIN</a>|</li>
		<li id="logout_a"><a type="button" logout="logout_a" class="btn btn-default" href="logout.do">LOGOUT</a>|</li>
		<li><a type="button" class="btn btn-default" data-toggle="modal" data-target="#myJoinModal" join="join_a">JOIN</a>|</li>
		<li><a type="button" class="btn btn-default" data-toggle="modal" data-target="#myChatModal" chat="chat_a">1:1Chat</a>|</li>
		<li><a type="button" class="btn btn-default" data-toggle="modal" data-target="#myMailModal">EMAIL(Q&A)</a></li>
	</ul>
	</div>
			<!-- 로그인 Modal 시작-->
			<div class="modal fade" id="myLoginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <center><h4 class="modal-title" id="myModalLabel"><b>로그인 화면</b></h4></center>
			      </div>
			      <div class="modal-body">
			      	<form action="login.do" method="post" id="login_form">
			      	<center><b>
				      ID: <input type="text" name ="id" log="id"><br><br>
				      PW: <input type="password" name ="pwd" log="pwd"></b>
			        </center>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal" login="login_b">로그인</button>
			        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
			      </div>
			    </div>
			  </div>
			</div>
			<!-- 로그인 Modal 종료-->
			<!-- 회원가입 Modal 시작-->
			<div class="modal fade" id="myJoinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <center><h4 class="modal-title" id="myModalLabel"><b>회원가입 화면</b></h4></center>
				      </div>
				      <div class="modal-body">			   
				      		<form action = "#" method="post" join="joinForm">
				      		<b>
				      		  <table align = "center">
									<tr><td>ID:</td><td>&nbsp;&nbsp;<input type="text" name ="id" id="id">&nbsp;&nbsp;<input type="button" class="btn btn-info" id="compare_Id" value="아이디 중복확인"></td></tr>
									<tr><td colspan="2" align="left">&nbsp;&nbsp;<input type="text" name ="idChek" id="idChekResult" disabled="disabled" size="30" style="color: red; font-size: xx-small; border: hidden; background: white;"></td></tr>
									<tr><br><td colspan="2" align="left"><br>&nbsp;&nbsp;"영문,숫자,특수문자 혼합 8~12자리 입력바랍니다.(공백입력 불가능)"</td></tr>
									<tr><td>PW:</td><td>&nbsp;&nbsp;<input type="password" name ="pwd" id="pwd">&nbsp;&nbsp;<input type="text" name ="pwdChek" id="pwdChek" disabled="disabled" size="30" style="color: red; font-size: xx-small; border: hidden; background: white;"></td></tr>
									<tr><td>PW재확인:</td><td>&nbsp;&nbsp;<input type="password" name ="pwd2" id="pwd2">&nbsp;&nbsp;<input type= "button" class="btn btn-info" id="check_pwd" value="비밀번호 확인"></td></tr>
									<tr><td>&nbsp;&nbsp;<input type="text" name ="pwdChek2" id="pwdChek2" disabled="disabled" size="30" style="color: red; font-size: xx-small; border: hidden; background: white;"></td></tr>
									<tr><td><br>이름:</td><td><br>&nbsp;&nbsp;<input type="text" name ="m_name" id="m_name"></td></tr>
									<tr><td>연락처(핸드폰):</td><td>&nbsp;&nbsp;<input type="text" name ="phone" id="phone"></td></tr>
									<tr><td>이메일주소:</td><td>&nbsp;&nbsp;<input type="text" name ="e_mail" id="e_mail"></td></tr>
									<tr><td><br>주소:&nbsp;&nbsp;<input type="button" class="btn btn-default" id="search_add" onclick="sample5_execDaumPostcode()" value="주소검색"/></td><td><br></td></tr>
									<tr><td align="left" colspan=2><input type="text" style="width:500px;" id="addr" name="addr"></td></tr>
									<tr><td align="left" colspan=2><input type="text" style="width:500px;" id="addr2" name="addr2"></td></tr>							
									<tr><td><br><br><hr>SNS연동:</td><td><br><br><hr>&nbsp;&nbsp;<input type="checkbox" name ="sns_typeCheck" id="myJoinSnsCheck"></td></tr>
									<tr><td>SNS종류:</td><td>&nbsp;&nbsp;<select name="sns_type" id="sns_type" disabled="disabled" style="background:lightslategray"><option value="">--선택--</option><option value="1">페이스북</option><option value="2">인스타그램</option><option value="3">카카오스토리</option></select></td></tr>
									<tr><td>SNS아이디:</td><td>&nbsp;&nbsp;<input type="text" name ="sns_id"  id = "sns_id" disabled="disabled" style="background:lightslategray"></td></tr>
									<tr><td></td><td></td></tr>
								</table>
					    	</b>
					    	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal" join="join_b">회원가입</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				      </div>
			    </div>
			  </div>
			</div>
			<!-- 회원가입 Modal 종료-->
			<!-- 메일보내기 시작-->
			<div class="modal fade" id="myMailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <center><h4 class="modal-title" id="myModalLabel"><b>메일문의화면</b></h4></center>
				      </div>
				      <div class="modal-body">			   
				      		<form action = "#" method="post" mail="mailForm">
				      		<b>
				      		  <table align = "center">
				      		  		<tr style="height: 30px"><td>아이디 or 이름:</td><td colspan="5"><input type="text" name="mailText1" id="mailText1"></td></tr>
									<tr style="height:30px"><td>연락처 또는 이메일:&nbsp;&nbsp;</td><td colspan="5"><input type="text" name="mailText2" id="mailText2"></td></tr>
									<tr style="height:30px"><td>제목:</td><td colspan="5"><input type="text" name="mailText3" id="mailText3"></td></tr>
									<tr><td><font color="#eee">-----------------------------</font></td><td><font color="#eee">----------------------------------------</font></td></tr>
									<tr style="height: 30px"><td>메시지:</td><td colspan="5"><textarea rows="10" cols="21" name="mailText4" id="mailText4"></textarea></td></tr>
							  </table>
					    	</b>
					    	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal" mail="mail_b">메일발송</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				      </div>
			    </div>
			  </div>
			</div>	
			<!-- 메일보내기 종료-->
			<!-- 채팅창 시작-->
			<div class="modal fade" id="myChatModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <center><h4 class="modal-title" id="myModalLabel"><b>1:1 채팅문의</b></h4></center>
				      </div>
				      <div class="modal-body">			   
				      		<form action = "#" method="post" chat="chat">
				      		<b>
				      		  <table align = "center">
				      		  		<tr style="height: 30px"><td>아이디 or 이름:</td><td colspan="4"><input type="text" name="chatId" id="chatId" value="${mVo.id }" readonly="readonly" class="form-control" placeholder="로그인 후 채팅가능합니다."></td></tr>
									<tr><td><font color="#eee">-----------------------------</font></td><td><font color="#eee">----------------------------------------</font></td></tr>
									<tr style="height: 30px"><td>대화창:</td><td colspan="5"><div id="chatBox"></div></td></tr>
									<tr style="height: 30px"><td>입력창:</td><td colspan="4"><input name="chatInput" id="chatInput" class="form-control"></td><td colspan="1">&nbsp;&nbsp;<input type="button" class="btn btn-default" id = "chatSendBtn" value="보내기"></td></tr>
							  </table>
					    	</b>
					    	</form>
				      </div>
				<!-- <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal" mail="chat_b">채팅발송</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				      </div> -->
			    </div>
			  </div>
			</div>	
			<!-- 채팅창 종료-->			
</div>
</body>
</html>