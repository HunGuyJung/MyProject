<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#goods_img{
		position: relative; 
		float: left; 
		width: 65%; 
		height: auto;
	}
	#goods_info{
		position: relative; 
		float: left; 
		width: 30%; 
		height: auto; 
	}
	#link_codi,#link_review{
		position: relative; 
		float: left; 
		width: 30%; 
		height: 190px; 
		text-align: center;
		overflow: auto;
	}
	#comment_list{
		text-align : center;
		position: relative;  
		width: 60%; 
		height: auto; 
	}
	#goods_info_title{
		font-weight: bold; 
		font-size: 40pt; 
		padding-top:15%; 
		padding-left: 3%;
	}
	#goods_info_price{
		padding-top:3%; 
		padding-left: 16%;
	}
	#g_i_p_w{
		float: left; 
		font-size: xx-large; 
		margin-left: 50px;
	}
	#g_i_p_price{
		float: left; 
		font-size: xx-large;
	}
	#goods_info_content{
		font-size: 20pt; 
		padding-top:16%; 
		padding-left: 16%;
	}
	#goods_info_combobox_color{
		font-size: 20pt; 
		width: 100%; 
		padding-top:5%; 
		margin-left: 50px; 
	}
	#goods_info_combobox_size{
		font-size: 20pt; 
		width: 100%; 
		padding-top:5%; 
		margin-left: 50px;
	}
	#goods_info_amount{
		font-size: 20pt; 
		width: 100%; 
		height: 100px; 
		margin-top: 20px; 
		margin-left: 50px; 
	}
	#goods_info_btn{
		font-size: 20pt; 
		float: left; 
		width: 100%; 
		height: 100px; 
		margin-top: 50px; 
	}
	#like_btn,#cart_btn{
		height:65px;
		width:150px; 
		font-size: large;
	}
	.codi_b_img{
		height: 80px;
		width: 50px;
	}
	#comment_input_group{
		width: 60%;
	}
	#comment_table{
		width: 100%;
		text-align: center;
		border: 1;
	}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function() {
		var amount = [];
		
		// 댓글 리스트 ajax function
		$.commentList = function(){
			//alert("댓글리스트 불러오기");
			var tb = $("#comment_table");
			$(tb).empty();
			var b_no = $("#b_no").val();
			$.getJSON({
				url : "commentList.json",
				data : {b_no:b_no},
				success : function(data){
					var data1 = eval("("+data+")");
					//alert(data1);
					var tr1 = $("<tr></tr>");
					var td1 = $("<td></td>").text("작성자");
					var td2 = $("<td></td>").text("내용");
					var td3 = $("<td></td>").text("작성일");
					$(tr1).append(td1,td2,td3);
					$(tb).append(tr1);
					$(data1).each(function(i,v){
						//alert(v.id);
						var tr2 = $("<tr></tr>").attr("class","comment_tr");
						var td4 = $("<td></td>").text(v.id);
						var td5 = $("<td></td>").text(v.c_content);
						var td6 = $("<td></td>").text(v.create_time);
						var input1 = $("<input></input>").attr({"type":"hidden", "id":"c_ref"}).val(v.c_ref);
						var input2 = $("<input></input>").attr({"type":"hidden", "id":"c_level"}).val(v.c_level);
						var input3 = $("<input></input>").attr({"type":"hidden", "id":"c_step"}).val(v.c_step);
						$(tr2).append(td4,td5,td6,input1,input2,input3);
						$(tb).append(tr2);
					});
				}});
		};
		
		$.commentList();
		
		$(".comment_tr").on('click',function(){
			alert("!!!!!!!!!!!!");
		});
		
		$("#like_btn").click(function() {
			//alert("좋아요!");
			if($("#m_no").val()>=1){
				var m_no = $("#m_no").val();
				var b_no = $("#b_no").val();
				alert("좋아요 회원번호 "+m_no);
				alert("좋아요 글번호 "+b_no);
				$.getJSON({
					url: "boardLike.do",
					data: {m_no:m_no,b_no:b_no},
					success:function(data){
						alert("좋아요 생성!");
				}});
			}else {
				alert("로그인이 필요합니다!");
			}
		});
		
		//색상선택시 해당색상의 사이즈목록 불러오기
		$("#goods_info_combobox_color").change(function() {
			amount = [];
			$("#input_amount").val("");
			$("#size_amount").text("()");
			var g_code = $("#g_code").val();
			var color = $("#color option:selected").val();
 			//alert("선택된 색상 : "+color);
 			$.getJSON({
 				url:"size_combo.do",
 				type:"post",
 				data:{color:color,g_code:g_code},
 				success:function(data){
 					$("#size").empty();
					var op1 = $("<option></option>").html("사이즈");
					$("#size").append(op1);
 					$(data).each(function(i,v) {
						var op = $("<option></option>").attr("value",v.g_size).html(v.g_size);
						$("#size").append(op);
						amount.push({
							size:v.g_size,
							sub_amount:v.amount
						});
					});
 				}});
			});
		
		//사이즈 선택시 해당상품의 수량 표시
		$("#size").change(function() {
			var size = $("#size option:selected").val();
			$(amount).each(function(i,v) {
				if(v.size==size)
				{
					$("#size_amount").text("("+v.sub_amount+")");
				}
			});
		});
		
		//수량입력시 합계자동계산
		$("#input_amount").keyup(function() {
			var tot = $(this).val()*$("#g_i_p_price").text();
			//alert(tot);
			$("#total").val(tot);
		});
		
		// 해당상품의 코디글로 이동
		$(".codi_b_img").click(function() {
			var b_no = $(this).attr("link_b_no");
			//alert(b_no);
			$(location).attr('href','detailCodi.do?b_no='+b_no);
		});
		
		// 해당상품의 후기글로 이동
		$(".review_b_title").click(function() {
			var b_no = $(this).attr("link_b_no");
			//alert(b_no);
			$(location).attr('href','detailReview.do?b_no='+b_no);
		});
		
		// 댓글 입력 ajax
		$("#comment_insert_btn").click(function() {
			if($("#m_no").val()>=1){
				var content = $("#comment_input").val();
				var c_m_no = $("#m_no").val();
				var c_b_no = $("#b_no").val();
				//alert("댓글입력 : "+c_m_no);
				//alert("댓글입력 : "+content);
				$.ajax({
					url: "comment.json",
					data: {m_no:c_m_no,b_no:c_b_no,c_content:content},
					success:function(data){
						//alert(("("+data+")"));
						$("#comment_input").val("");
						$.commentList();
				}});
			}else{
				alert("로그인이 필요합니다!");
			}
		});
		
	});
</script>
</head>
<body><center>
<%-- <h2>회원번호 : ${msg }</h2> --%>
	<div id="goods_img">
		<img src="resources/upload/${dgv.b_img }" width="100%" height="1000px">
	</div>
	<div id="goods_info">
		<form action="insertCart.do" method="post">
        	<div id="goods_info_title">
				<input type="hidden" value="${m_no }" name="m_no" id="m_no"> 
				<input type="hidden" value="${dgv.b_no }" name="b_no" id="b_no">
				<input type="hidden" value="${dgv.g_code }" name="g_code" id="g_code">  
				<input type="hidden" value="${dgv.price }" name="price">  
				<input type="hidden" value="${dgv.g_name }" name="g_name">  
				<input type="hidden" value="${dgv.title }" name="title">  
					${dgv.title }
			</div><br>
        	<div id="goods_info_price">
				<p id="g_i_p_w">₩&nbsp;</p>
				<p id="g_i_p_price">${dgv.price }</p>
			</div>
			<div id="goods_info_content">
				${dgv.content }
			</div>
			<div id="goods_info_combobox_color" align="left">
				색상 : <select name="color" id="color">
					<option>색상선택</option>
					<c:forEach var="c" items="${color }">
						<option value="${c }">${c }
					</c:forEach>
				</select>
			</div>
			<div id="goods_info_combobox_size" align="left">
				사이즈 <select id="size" name="g_size"><option>사이즈</option></select>
			</div>
			<div id="goods_info_amount" align="left">
				수량 : <input id="input_amount" name="amount" type="number" style="width: 20%"><span id="size_amount"></span>
				<br><br>소계 : <input id="total" name="total" type="number" readonly="readonly"> 원 
			</div>
			<div id="goods_info_btn" class="btn-group btn-group-lg">
				<center><input id="type" name="type" type="hidden">
				<input id="like_btn" class="btn btn-success " type="button" value="좋아요!"> &nbsp;&nbsp; 
				<input id="cart_btn" class="btn btn-danger" type="submit" value="구매하기"></center>
			</div>
		</form>
	</div>
	<div id="link_codi">
		<h5>--- 해당 상품이 포함된 Codi 게시글 목록 ---</h5>
		<hr>
		<c:forEach items="${link_codi }" var="lc">
			<img class="codi_b_img" src="resources/user_upload/${lc.b_img }" link_b_no="${lc.b_no }">&nbsp;
		</c:forEach>
		<hr>
	</div>
	<div id="link_review">
		<h5>--- 해당 상품의 Review 게시글 목록 ---</h5>
		<hr>
		<c:forEach items="${link_review }" var="lr">
			<input class="review_b_title" type="text" value="${lr.title }" readonly="readonly" link_b_no="${lr.b_no }"><br>
		</c:forEach>
		<hr>
	</div>
	<div id="comment_list_form" class="form-group">
		<form id="comment_insert_form">
			<div class="input-group" id="comment_input_group">
				<input type="text" id="comment_input" name="c_content" class="form-control" placeholder="댓글을 입력하세요" aria-describedby="basic-addon1">
				<span class="input-group-btn">
		    	<button class="btn btn-primary" id="comment_insert_btn" type="button">댓글 등록</button>
			    </span>
			</div>
		</form>
	</div><hr>
	<div id="comment_list">
		<table id="comment_table"></table>
	</div>
</center></body>
</html>