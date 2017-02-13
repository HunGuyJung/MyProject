<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.chart_div{
		margin-left: 30px;
		margin-top: 20px;
		float: left;
		width: 750px;
		height: 400px;
	}
	#manager_Sales_chart2{
		text-align: center;
		overflow: auto;
	}
</style><script type="text/javascript" src="resources/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script>
google.load('visualization','1',{'packages':['corechart']});
google.setOnLoadCallback(manager_Sales_drawChart1);
google.setOnLoadCallback(manager_Sales_drawChart2);
google.setOnLoadCallback(manager_Sales_drawChart3);
	function manager_Sales_drawChart1(str,title_str) {
		var url_str = "manager_Sales_chart1.json";
		var title_str = "월별 판매현황";
		if(str=="manager_Main_chart2.json")
		{
			url_str = "manager_Main_chart2.json";
			title_str = "일자별 판매현황";
		}
		//alert(url_str);
		var jsonData1; 
		$.getJSON({
			url:url_str,
			dataType:"json",
			async:false,
			success : function(data) {
				jsonData1 = data;
		}});
		console.log("제이슨데이터1 : "+jsonData1);
		var data1 = new google.visualization.DataTable(jsonData1);
		var chart = new google.visualization.ColumnChart(document.getElementById('manager_Sales_chart1'));
		var option = {
				title : title_str,
				chartArea: {width: '500px',height : '400px'},
		};
		chart.draw(data1,option);
	}
	function manager_Sales_drawChart2() {
		var jsonData2; 
		$.getJSON({
			url:"manager_Sales_chart2.json",
			dataType:"json",
			async:false,
			success : function(data) {
				jsonData2 = data;
		}});
		console.log("제이슨데이터2 : "+jsonData2);
		var data2 = new google.visualization.DataTable(jsonData2);
		var chart = new google.visualization.PieChart(document.getElementById('manager_Sales_chart3'));
		var option = {
				title : '거래처별 판매 현황',
				chartArea: {width: '450',height : '350'},
		};
		chart.draw(data2,option);
	}
	function manager_Sales_drawChart3() {
		var jsonData3; 
		$.getJSON({
			url:"manager_Sales_chart3.json",
			dataType:"json",
			async:false,
			success : function(data) {
				jsonData3 = data;
		}});
		console.log("제이슨데이터3 : "+jsonData3);
		var data3 = new google.visualization.DataTable(jsonData3);
		var chart = new google.visualization.PieChart(document.getElementById('manager_Sales_chart4'));
		var option = {
				title : '회원별 구매현황',
				chartArea: {width: '450',height : '350'},
		};
		chart.draw(data3,option);
	}
	$(function() {
		$(".days_trs").hide();
		
		//월별 버튼 클릭시 실행
		$("#sales_list_month_btn").click(function() {
			$(".days_trs").hide();
			$(".month_trs").show();
			manager_Sales_drawChart1("manager_Sales_chart1.json");
		});
		
		//일자별 버튼 클릭시 실행
		$("#sales_list_days_btn").click(function() {
			$(".days_trs").show();
			$(".month_trs").hide();
			manager_Sales_drawChart1("manager_Main_chart2.json");
		});
	});
</script>
</head>
<body>
	<div id="manager_Sales_chart1" class="chart_div">판매현황(월별/일별)</div>
	<div id="manager_Sales_chart2" class="chart_div">
		<button id="sales_list_month_btn" class="btn btn-default" >월별 판매현황</button>
		<button id="sales_list_days_btn" class="btn btn-default" >일자별 판매현황</button><br><br>
		<table width="50%" class="table table-hover">
			<tr>
				<td>날짜</td>
				<td>매출</td>
			</tr>
			<c:forEach items="${sales_list_month }" var="salesListM">
				<tr class="month_trs">
					<td>${salesListM.create_time }</td>
					<td>${salesListM.total }</td>
				</tr>
			</c:forEach>
			<c:forEach items="${sales_list_days }" var="salesListD">
				<tr class="days_trs">
					<td>${salesListD.create_time }</td>
					<td>${salesListD.total }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="manager_Sales_chart3" class="chart_div">거래처별 판매 현황</div>
	<div id="manager_Sales_chart4" class="chart_div">회원별 구매현황</div>
</body>
</html>