<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#chart_div1,#chart_div2{
		float: left;
		height:450px;
		width: 50%;
	}
	#low_stock{
		width : 100%;
		height : 300px;
		margin-bottom : 50px;
		overflow: auto;
	}
	#mainManager_chart{
		width : 100%;
		height:450px;
		text-align: center;
	}
	#mm_tb_h3{ 
 		width : 100%;
 		text-align: center; 
 		font-size: x-large;
	}
	.low_stock_tb_td{
		width: 25%;
	}
	.low_stock_tb_col{
		width: 17%;
		background-color: orange;
	}  
</style>
<script type="text/javascript" src="resources/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script>
google.load('visualization','1',{'packages':['corechart']});
google.setOnLoadCallback(manager_Main_drawChart1);
google.setOnLoadCallback(manager_Main_drawChart2);
	function manager_Main_drawChart1() {
		var jsonData1; 
		$.getJSON({
			url:"manager_Main_chart1.json",
			dataType:"json",
			async:false,
			success : function(data) {
				jsonData1 = data;
		}});
		console.log("제이슨데이터1 : "+jsonData1);
		var data1 = new google.visualization.DataTable(jsonData1);
		var chart = new google.visualization.PieChart(document.getElementById('chart_div1'));
		var option = {
				title : '카테고리별 판매비율',
				chartArea: {width: '500',height : '400'},
		};
		chart.draw(data1,option);
	}
	function manager_Main_drawChart2() {
		var jsonData2; 
		$.getJSON({
			url:"manager_Main_chart2.json",
			dataType:"json",
			async:false,
			success : function(data) {
				jsonData2 = data;
		}});
		console.log("제이슨데이터2 : "+jsonData2);
		var data2 = new google.visualization.DataTable(jsonData2);
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div2'));
		var option = {
				title : '일자별 매출',
				chartArea: {width: '500',height : '300'},
		};
		chart.draw(data2,option);
	}
</script>
</head>
<body><br><br>
<!--그래프 그려지는 div -->
	<div id="mainManager_chart"  >
		<div id="chart_div1"></div>
  		<div id="chart_div2"></div>
  	</div>
    
<!-- 재고부족 테이블 div -->
	<label id="mm_tb_h3">부족 재고 현황<br><br>
		<table width="100%">
			<tr>
				<td width="15%"></td>
				<td class="low_stock_tb_col">상품번호</td>
   	  			<td class="low_stock_tb_col">상품이름</td>
   	  			<td class="low_stock_tb_col">상품재고</td>
   	  			<td class="low_stock_tb_col">거래처</td>
   	  			<td width="16%"></td>
   	  		</tr>
		</table>
	</label>
    <div id="low_stock"><center>
     		<table border="1" width="70%"> 
				<!--<tr align="center" style="background: orange;"> -->
					<!--<td>상품번호</td> -->
					<!--<td>상품이름</td> -->
					<!--<td>상품재고</td> -->
					<!--<td>거래처</td> -->
				<!--</tr> -->
   	  			<c:forEach var="g" items="${lowStockGoodsList }">
   	  				<tr>
   	  					<td class="low_stock_tb_td">${g.g_no }</td>
   	  					<td class="low_stock_tb_td">${g.g_name }</td>
   	  					<td class="low_stock_tb_td">${g.amount }</td>
     	  				<td class="low_stock_tb_td">${g.brand }</td>
     	  			</tr>
     			</c:forEach>
    		</table>
	</center></div>
</body>
</html>