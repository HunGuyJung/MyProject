<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#manager_Order_lowStok_div{
		margin-top: 40px;
		margin-left: 70px;
		float: left;
		width: 40%; 
	}
	#manager_Order_orderlist_div{
		margin-top: 40px;
		float: left; 
		width: 50%; 
		padding-left: 10%
	}
	#manager_Order_orderlist_tb{
		overflow: auto;
	}
	.sel_tr{
		background-color: red;
	}
	#manager_Order_msg{
		font: bold;
		color: red;
		text-align: center;
	}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function() {
		//테이블 클릭시 해당 행의 값을 텍스트 필드에 입력(발주입력)
		$(".manager_Order_lowStok_trs").click(function() {
			$(".manager_Order_lowStok_trs").removeClass("sel_tr");
			$(this).addClass("sel_tr");
			var arr = $("#manager_Order_lowStok_div").find("input[type=text]");
			var tds = $(this).find("td");
			$(tds).each(function(index,item) {
				$(arr[index]).val($(tds[index]).text());
			});

		});
		
		//테이블 클릭시 해당 행의 값을 텍스트 필드에 입력(발주확인)
		$(".manager_Order_orderlist_trs").click(function() {
			$(".manager_Order_orderlist_trs").removeClass("sel_tr");
			$(this).addClass("sel_tr");
			var arr = $("#manager_Order_orderlist_div").find("input[type=text]");
			var tds = $(this).find("td");
			$(tds).each(function(index,item) {
				$(arr[index]).val($(tds[index]).text());
			});
		});
		
		//발주확인 버튼 클릭시 작동
		$("#dealOk_btn").click(function() {
			$("#manager_Order_orderlist_form").attr("action","manager_Order_orderUpdate.do");
			$("#manager_Order_orderlist_form").submit();
		});
		
		//발주취소 버튼 클릭시 작동
		$("#dealCancle_btn").click(function() {
			$("#manager_Order_orderlist_form").attr("action","manager_Order_orderDelete.do");
			$("#manager_Order_orderlist_form").submit();
		});
		
		//상품상세 글 등록 확인 메세지 타이머
		setTimeout(function() {
			//alert("Timeout 작동");
			$("#manager_Order_msg").hide();
		},3000);
		
	});
</script>
</head>
<body>
	<!--상품목록 리스트 재고 낮은순으로 출력 시작 -->
	<h3 id="manager_Order_msg">${manager_Order_msg }</h3><br>
	<div id="manager_Order_lowStok_div">
		<form action="insertDeal.do" method="post">
			<table width="100%">
				<tr>
					<td>상품번호 : <input name="g_no" type="text" readonly="readonly"></td>
					<td>상품이름 : <input name="g_name" type="text" readonly="readonly"></td>
				</tr><tr><td><br></td></tr>
				<tr>
					<td>주문수량 : <input name="d_qty" type="text"></td>
					<td>주문단가 : <input name="d_total" type="text" readonly="readonly"></td>
				</tr><tr><td><br></td></tr>
				<tr>
					<td>거래처 &nbsp;&nbsp;&nbsp;: <input name="source_no" type="text"></td>
					<td style="padding-left: 100px"><input type="submit" value="발주">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="취소"></td>
				</tr>
			</table><br>
		 </form><br>
		 <table border="1" width="100%">
			<tr align="center" style="background: orange;">
			 <td>상품번호</td>
			 <td>상품이름</td>
			 <td>상품재고</td>
			 <td>상품단가</td>
			 <td>거래처</td>
			</tr>
			<c:forEach var="g" items="${lowStockGoodsList }">
				<tr class="manager_Order_lowStok_trs">
					<td>${g.g_no }</td>
					<td>${g.g_name }</td>
					<td>${g.amount }</td>
					<td>${g.price }</td>
					<td>${g.source_no }</td>
				</tr>
			</c:forEach>
		 </table><br>
	</div>
	<!--상품목록 리스트 재고 낮은순으로 출력 끝-->
	<!--발주목록 확인시 상품재고에 추가 시작-->
	<div id="manager_Order_orderlist_div">
		<div id="manager_Order_orderlist_tb">
			<table border="1" width="100%">
		 		<tr align="center" style="background: orange;">
		 			<td>발주번호</td>
		 			<td>상품번호</td>
					<td>발주수량</td>
		 			<td>발주금액</td>
		 			<td>거래처</td>
		 			<td>주문일</td>
		 		</tr>
		 		<c:forEach var="d" items="${nonOk_dealList }">
		 			<tr class="manager_Order_orderlist_trs">
		 				<td>${d.d_no }</td>
		 				<td>${d.g_no }</td>
		 				<td>${d.d_qty }</td>
		 				<td>${d.d_total }</td>
		 				<td>${d.source_no }</td>
		 				<td>${d.d_date }</td>
		 			</tr>
		 		</c:forEach>	
			</table><br><br>
		</div>
		<form id="manager_Order_orderlist_form" method="post">
	 	 	<input name="orderOk" type="hidden" value="ok">
 	 		발주번호 : <input name="d_no" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	 	상품번호 : <input name="g_no" type="text"><br><br>
	 	 	주문수량 : <input name="d_qty" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	 	주문금액 : <input name="d_total" type="text"><br><br>
	 	 	<center><input id="dealOk_btn" type="button" value="상품도착">
	 	 	<input id="dealCancle_btn" type="button" value="발주취소"></center>
		</form><br>
		<div id="manager_Order_orderlist_tb">
			<table border="1" width="100%">
		 		<tr align="center" style="background: orange;">
		 			<td>발주번호</td>
		 			<td>상품번호</td>
					<td>발주수량</td>
		 			<td>발주금액</td>
		 			<td>거래처</td>
		 			<td>주문확정일</td>
		 		</tr>
		 		<c:forEach var="od" items="${Ok_dealList }">
		 			<tr class="manager_Order_orderlist_trs">
		 				<td>${od.d_no }</td>
		 				<td>${od.g_no }</td>
		 				<td>${od.d_qty }</td>
		 				<td>${od.d_total }</td>
		 				<td>${od.source_no }</td>
		 				<td>${od.f_date }</td>
		 			</tr>
		 		</c:forEach>	
			</table><br><br>
		</div>
	</div>
	<!--발주목록 확인시 상품재고에 추가 끝-->
</body>
</html>