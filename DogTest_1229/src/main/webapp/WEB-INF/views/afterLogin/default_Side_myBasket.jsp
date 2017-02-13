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
    
    <!-- 결제모듈: 아임포트 라이브러리1 -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
	<!-- 결제모듈: 아임포트 라이브러리2 -->
	<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>
	
	<script type="text/javascript" src="resources/jq.js"></script>
	<script type="text/javascript">
	var total;
	var checkBox;
	var orderVo;
		$(function(){
			total = 0;
			//  초기에 숨겨야할 것들 설정 시작
			$("#finalDeliveryBtn").hide()  //  배송지버튼 숨기기
			$("#myDeliveryShow").hide();  //  배송지입력창 숨기기
			//  초기에 숨겨야할 것들 설정 종료
			
			$( "input[type=checkbox]" ).on("click", function(){
				if($(this).is(":checked")){
					var a = $(this).attr("subTotal");
					total = eval(total) + eval(a);
					$("#finalTotalPrice").val(total);
					$("#TotalPrice").val(total);
					
					checkBox = $(this);
					
					//  결제버튼 눌러지면  시작
					$("#finalPayBtn").click(function(){
						
						
						//   주문장 정보가 입력되어 있지않은지 체크
						var count = 0;
						var orderFormInputArray = $("#orderForm").find("input");
						$(orderFormInputArray).each(function(){
							var a = $(this).val();
							//alert(a);
							if(a == null || a == ""){
								count += 1;
							}
						});
						
						if(count != 0 ){
							alert("결제&배송정보를 다시입력해주세요!");
							
						//  주문장 정보 제되로 있으면 실제 결제 API, 주문장, 주문상세 ajax 진행  시작
						}else{
							//  alert("순서2");
							//  주문상세  2  시작
							var g_no = $(checkBox).next().val();
							var count = $(checkBox).next().next().val();
							var subtotal = $(checkBox).next().next().next().val();
							
							var data = {
											od_no: "0",
											g_no: g_no,
											count: count,
											subtotal: subtotal
										}
							
							$.ajax(
									{
										url:"orderDetail.do",
										data:data,
										success:function(){
											//  alert("주문상세입력됨!");
										}
									}
							);
							//  주문상세 2  끝

						}			
						//  주문장 정보 제되로 있으면 실제 결제 API, 주문장, 주문상세 ajax 진행  끝
						
						
						//  모든 결제, 주문장, 주문상세가 끝났다는 논리로 결제내역화면으로 보내준다!
						if(msg =="결제가 완료되었습니다."){
							var url = "/dogCodi/myPayList.do?index=1";    
							$(location).attr('href',url);	
						}
						
					});
					//  결제버튼 눌러지면  끝
					
				}else{
					var a = $(this).attr("subTotal");
					total = eval(total) - eval(a);
					$("#finalTotalPrice").val(total);
					$("#TotalPrice").val(total);
		        }
		

			} );

			//  최종금액확인버튼 누르면 배송지버튼창 나타남
			$("#finalPriceCheckBtn").click(function(){
								
				//  배송버튼 보이는 화면
				$("#finalDeliveryBtn").show();
		
				$( "input[type=checkbox]" ).attr("disabled","disabled");
				//  최종버튼이 클릭되어지면 check 불가능하게 만들기!
			});
			
			// 그다음 배송버튼 누르면 배송입력창 나타남
			$("#finalDeliveryBtn").click(function(){
				$("#myDeliveryShow").show();
			});
			
	 		//  결제 버튼누르면 결제기능 호출하기
			$("#finalPayBtn").click(function(){
				var count = 0;
				var orderFormInputArray = $("#orderForm").find("input");
				$(orderFormInputArray).each(function(){
					var a = $(this).val();
					//alert(a);
					if(a == null || a == ""){
						count += 1;
					}
				});
				
				if(count != 0 ){
					alert("결제&배송정보를 다시입력해주세요!");
				}else{
					//  alert("순서1");
					pay();
					
					// 주문장  1 시작
					orderVo = $("#orderForm").serialize();
					alert(orderVo);
					$.ajax(
							{
								url:"order.do",
								data:orderVo,
								type:"POST",
								success:function(){
									//  alert("주문장 입력됨!");
								}
							}
					);
					//  주문장 1 끝

				}
			}); 
					
		});

		//  결제모듈 코딩 시작
		var msg = "123";  //  결재완료이후 order.do 진행시키기위한 지표
		var IMP = window.IMP; // 생략가능
		IMP.init('imp77108478'); // 'iamport'매개변수자리에 부여받은 "가맹점 식별코드"를 사용
	
		//  결제모듈의 핵심 function()  시작
		//  결제 창을 띄워야 하는 페이지에서 아래의 IMP.request_pay({파라메터}) 함수를 호출
		var pay = function(){
		IMP.request_pay({
		    pg : 'inicis', // version 1.1.0부터 지원.
		    pay_method : 'card',
		    merchant_uid : 'merchant_' + new Date().getTime(),
		    name : '주문명:결제테스트',
		    amount : $("#finalTotalPrice").val(),
		    buyer_email : 'iamport@siot.do',
		    buyer_name : '구매자이름',
		    buyer_tel : '010-1234-5678',
		    buyer_addr : '서울특별시 강남구 삼성동',
		    buyer_postcode : '123-456',
		    m_redirect_url : 'https://www.yourdomain.com/payments/complete'
		}, function(rsp) {
		    if ( rsp.success ) {
		        msg = '결제가 완료되었습니다.';
		        msg += '고유ID : ' + rsp.imp_uid;
		        msg += '상점 거래ID : ' + rsp.merchant_uid;
		        msg += '결제 금액 : ' + rsp.paid_amount;
		        msg += '카드 승인번호 : ' + rsp.apply_num;
		    } else {
		        msg = '결제에 실패하였습니다.';
		        msg += '에러내용 : ' + rsp.error_msg;
		    }
		    alert(msg);
		});
		//  결제모듈의 핵심 function()  끝
		}		
		//  결제모듈 코딩 끝	
		
			
		//  주소창 불러오는 api
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
	                document.getElementById("delAddr").value = fullAddr;                
	            }
	        }).open();
	    }		
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
	    <!-- 장바구니 시작: list를 order_detail을 불러와야함!-->
	   	<div id="myBasketShow">
	   		<h2>장바구니 목록</h2>
			<hr>
			<%-- <form action="listGoods.do" method="post">
				<select  name="keyfield" id="keyfield">
					<option value="name"  <c:if test="${keyfield=='name' }"> selected="selected"</c:if> >상품이름</option>
					<option value="price" <c:if test="${keyfield=='price' }"> selected="selected"</c:if>>가격</option>
					<option value="no" <c:if test="${keyfield=='no' }"> selected="selected"</c:if>>상품번호</option>
				</select>
				<input type="text" name="keyword"  value="${keyword }">
				<input type="submit" class="btn btn-default" value="검색">
			</form> --%>
			<table class="table table-hover">
				<tr>
					<td>체크옵션</td>
					<td>장바구니번호</td>
					<td>상품제목(title)</td>
					<td>상품내용(content)</td>
					<td>상품코드</td>
					<td>상품이미지</td>
					<td>상품명</td>
					<td>가격</td>
					<td>수량</td>
					<td>색상</td>
					<td>사이즈</td>
					<td>총계</td>		
				</tr>
				<c:if test="${not empty cartList}">
					<c:forEach items="${cartList }" var="dgVo" begin="0" >
						<tr>
							
								<td><form od_form="od_form" class="od_form" value="${dgVo.title }">
										<input type="checkbox" subTotal="${dgVo.total}" value="${dgVo}">
										<input type="hidden" name="g_no" value="${dgVo.g_code}${dgVo.color }${dgVo.g_size}">
										<input type="hidden" name="count" value="${dgVo.amount}">
										<input type="hidden" name="subtotal" value="${dgVo.total}">
									</form>
								</td>
								<td>${dgVo.b_no }</td>
								<td>${dgVo.title }</td>
								<td>${dgVo.content }</td>
								<td> ${dgVo.g_code }</td>
								<td><img src="resources/upload/${dgVo.b_img}" width="30px" height="30px"></td>
								<td>${dgVo.g_name}</td>
								<td>${dgVo.price}</td>
								<td>${dgVo.amount}</td>
								<td>${dgVo.color}</td>
								<td>${dgVo.g_size}</td>
								<td total="total" >${dgVo.total}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<br><br>
			<h2>총가격: <input id= "finalTotalPrice" type="text" readonly="readonly"></h2><hr>
			<!-- 금액 최종확인 후 배송지/결제로 가기 시작-->
				<button type="submit" class="btn btn-default" id ="finalPriceCheckBtn">금액 최종확인</button>&nbsp;&nbsp;<button id="finalDeliveryBtn" class="btn btn-default">배송지</button>&nbsp;&nbsp;
			<!-- 금액 최종확인 후 배송지/결제로 가기 종료-->
			<hr>
			<br><br><br><br>
			<!-- 장바구니 안의 배송지설정 시작-->
			<div id="myDeliveryShow">
		    	<form action="order.do" method="post" id="orderForm" class="form-inline">
		    		<input id="m_no" type="hidden" name ="m_no" value="${m_no }">
		    		<input id= "TotalPrice" type="hidden" name="total" readonly="readonly">
			    	<h2>결제</h2>
			    	<table class="table table-hover">
			    		<tr><td>결재방식</td><td><select name="pay_type"><option value="1">핸드폰결제</option></select></td></tr>
			    		<tr><td>송금인</td><td><input type="text" name = "depositor"></td></tr>
			    	</table>
			    	<hr><br><br><br><br>
			    	<h2>배송지</h2>
			    	<table class="table table-hover">
			    		<tr><td>받을사람</td><td><input type="text" name = "receiver"></td></tr>
			    		<tr><td>연락처</td><td><input type="text" name = "receiver_phone"></td></tr>
			    		<tr><td>주소지</td><td><input type="text" style="width:500px;" id="delAddr" name="receiver_addr">&nbsp;&nbsp;<input type="button" class="btn btn-default" id="search_add" onclick="sample6_execDaumPostcode()" value="주소검색"/>
			    		</td></tr>
			    		<tr><td></td><td><input type="button" value="최종확인" id="finalPayBtn" class="btn btn-default">&nbsp;&nbsp;<input type="reset" class="btn btn-default" value="취소">
			    	</table>
		    	</form>
		    	<br>
	    	</div>
			<!-- 장바구니 안의 배송지설정 종료 -->
		</div>
	   	<!-- 장바구니 종료-->
    </div>
  </div>
  </body>
</html>