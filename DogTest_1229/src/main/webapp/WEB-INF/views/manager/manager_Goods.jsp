<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#manager_Goods_insertForm_div{
		width: 100%;
		height: 230px;
		padding-left: 180px;
	}
	#manager_Goods_insertForm_table{
		float : left;
		width: 1000px;
		height: 200px;
	}
	#manager_Goods_insertForm_img{
		float : left;
		width: 150px;
		height: 200px;
		overflow: hidden;
	}
	#manager_Goods_Goodstb_col{
		width: 100%;
		padding-left: 70px;
		padding-right: 70px;
		overflow: scroll;
		overflow-x: hidden; 
	}
	#manager_Goods_Goodstb{
		width: 100%; 
		height: 400px;
		padding-left: 70px;
		padding-right: 70px;
		overflow: auto;
	}
	.manager_Goods_insertForm_col{
		width: 10%;
		padding-left: 20px;
		padding-right: 10px;
		padding-bottom: 8px;
	}
	#goodsBoard_insert_madal_body{
		height: 450px;
		width: 750px;
	}
	#goodsBoard_insert_g_img{
		float: left; 
		margin-right: 20px; 
		margin-bottom: 20px;
	}
	#goodsBoard_insert_form_div{
		 float: left; 
		 align: left;
		 width: 250px;
		 padding-top: 15px;
	}
	#goodsBoard_insert_msg{
		font: bold;
		color: red;
		text-align: center;
	}
	.sel_tr{
		background-color: red;
	}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function() {
		//테이블 클릭시 해당 행의 값을 텍스트 필드에 입력
		$(".manager_Goods_Goodstb_trs").click(function() {
			$("#manager_Goods_insertForm_g_no").attr("readonly","readonly");
			$(".manager_Goods_Goodstb_trs").removeClass('sel_tr');
			$(this).addClass('sel_tr');
			var arr = $(".mgf");
			var tds = $(this).find("td");
			var n = 0;
			$(tds).each(function(index,item) {
				$(arr[index]).val($(tds[index]).text());
				n++;
			});
			//alert("선택된 tr의 파일명 : "+$(arr[n-1]).val());
			$("#manager_Goods_insertForm_img").children().eq(0).attr("src","resources/upload/"+$(arr[n-1]).val());
		});
		
		// 등록버튼 클릭시 상품 등록
		$("#manager_Goods_insertForm_insert").click(function() {
			$("#manager_Goods_insertForm_g_no").removeAttr("readonly");
			$("#manager_Goods_insertForm").attr("action","manager_Goods_insert.do");
			$("#manager_Goods_insertForm").submit();
		});
		
		// 수정버튼 클릭시 상품 수정
		$("#manager_Goods_insertForm_update").click(function() {
			$("#manager_Goods_insertForm_g_no").removeAttr("readonly");
			$("#manager_Goods_insertForm").attr("action","manager_Goods_update.do");
			$("#manager_Goods_insertForm").submit();
		});
		
		// 삭제버튼 클릭시 상품 삭제
		$("#manager_Goods_insertForm_delete").click(function() {
			$("#manager_Goods_insertForm_g_no").removeAttr("readonly");
			$("#manager_Goods_insertForm").attr("action","manager_Goods_delete.do");
			$("#manager_Goods_insertForm").submit();
		});
		
		// 취소버튼 클릭시 동작
		$("#manager_Goods_insertForm_cancle").click(function() {
			$("#manager_Goods_insertForm_g_no").removeAttr("readonly");
			$("#manager_Goods_insertForm_img").children().eq(0).attr("src","resources/upload/like.jpg");
		});
		
		//모달에서 취소버튼 클릭시 작동
		$("#goodsBoard_insert_cancle_btn").click(function() {
			$(this).attr("data-dismiss","modal");
		});
		
		//상품 상세 글 등록 from에서 상품코드 클릭시 이미지 변경
		$("#goodsBoard_insert_form_g_code").click(function() {
			var g_img = $("#goodsBoard_insert_form_g_code option:selected").attr("g_img");
			$("#goodsBoard_insert_form_g_img").attr("src","resources/upload/"+g_img);
			$("#goodsBoard_insert_form_b_img").val(g_img);
		});
		
		$("#goodsBoard_insert_btn").click(function() {
			$("#goodsBoard_insert_form").submit();
		});
		
		// 상품번호 input 더블클릭시 readonly 해제
		$("#manager_Goods_insertForm_g_no").dblclick(function() {
			$("#manager_Goods_insertForm_g_no").removeAttr("readonly");
		});
		
		//상품상세 글 등록 확인 메세지 타이머
		setTimeout(function() {
			//alert("Timeout 작동");
			$("#goodsBoard_insert_msg").hide();
		},3000);
	});
</script>
</head>
<body><br><br>
	<h3 id="goodsBoard_insert_msg">${manager_Goods_msg }</h3><br>
	<div class="modal fade" id="goodsBoard_insert_madal"> 
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">상품 상세 글 등록</h4>
	      </div>
	      <div class="modal-body" id="goodsBoard_insert_madal_body">
	        <div id="goodsBoard_insert_g_img">
     			<img id="goodsBoard_insert_form_g_img" width="300px" height="410px">
			</div>
    		<div id="goodsBoard_insert_form_div">
				<form id="goodsBoard_insert_form" action="goodsBoard_insert.do" method="post">
					<input type="text" readonly="readonly" value="${m_no }" name="m_no"><br><br>
					<input id="goodsBoard_insert_form_b_img" type="hidden" name="b_img">
					글 제목 : <input type="text" name="title"> <br><br>
					상품코드 : <select id="goodsBoard_insert_form_g_code" name="g_code">
						<c:forEach var="c" items="${g_code_comboList }">
							<option value="${c.g_code }" g_img="${c.g_img }">${c.g_code }
						</c:forEach>
					</select><br><br>
					상품설명 : <br><br>
					<textarea rows="10" cols="50" name="content"></textarea> <br><br>
				</form><br>
			</div>
	      </div>
	      <div class="modal-footer">
	      	<button id="goodsBoard_insert_btn">등록</button>
			<button data-dismiss="modal">취소</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<div id="manager_Goods_insertForm_div">
		<div id="manager_Goods_insertForm_table">
	    	<form id="manager_Goods_insertForm" method="post" enctype="multipart/form-data" > 
	     		<table>
	     	 		<tr>
	     	 			<td class="manager_Goods_insertForm_col">상품번호</td>
	     	 			<td><input class="mgf" id="manager_Goods_insertForm_g_no" type="text" name="g_no"></td>
	     	 			<td class="manager_Goods_insertForm_col">상품코드</td>
	     	 			<td><input class="mgf" type="text" name="g_code"></td>
	     	 			<td class="manager_Goods_insertForm_col">상품이름</td>
			     	 	<td><input class="mgf" type="text" name="g_name"></td>
			     	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	     	 			<td><input type="button" value="등록" id="manager_Goods_insertForm_insert"></td>
	     	 		</tr>
	     	 		<tr>
			     	 	<td class="manager_Goods_insertForm_col">상품단가</td>
			     	 	<td><input class="mgf" type="text" name="price"></td>
			     	 	<td class="manager_Goods_insertForm_col">브랜드</td>
			     	 	<td><input class="mgf" type="text" name="brand"></td>
			     	 	<td class="manager_Goods_insertForm_col">카테고리</td>
			     	 	<td><input class="mgf" type="text" name="category"></td>
			     	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			     	 	<td><input type="button" value="수정" id="manager_Goods_insertForm_update"></td>
	     	 		</tr>
	     	 		<tr>
			     	 	<td class="manager_Goods_insertForm_col">상품소재</td>
			     	 	<td><input class="mgf" type="text" name="material"></td>
			     	 	<td class="manager_Goods_insertForm_col">추천도</td>
			     	 	<td><input class="mgf" type="text" name="recommend"></td>
			     	 	<td class="manager_Goods_insertForm_col">색상</td>
			     	 	<td><input class="mgf" type="text" name="color"></td>
			     	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			     	 	<td><input type="button" value="삭제" id="manager_Goods_insertForm_delete"></td>
	     	 		</tr>
	     	 		<tr>
			     	 	<td class="manager_Goods_insertForm_col">사이즈</td>
			     	 	<td><input class="mgf" type="text" name="g_size" ></td>
			     	 	<td class="manager_Goods_insertForm_col">재고수량</td>
			     	 	<td><input class="mgf" type="text" name="amount"></td>
			     	 	<td class="manager_Goods_insertForm_col">거래처번호</td>
			     	 	<td><input class="mgf" type="text" name="source_no"></td>
			     	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			     	 	<td><input type="reset" value="취소" id="manager_Goods_insertForm_cancle"></td>
	     	 		</tr>
	     	 		<tr>
	     	 			<td colspan="5" class="manager_Goods_insertForm_col">
		     	 			이미지 <input type="file" name="uploadFile">
		     	 			<input class="mgf" id="type" name="g_img" type="text">
						</td>
						<td colspan="3" align="right">
							<a type="button" data-toggle="modal" data-target="#goodsBoard_insert_madal">
								<button>상품상세글 등록</button>
							</a>&nbsp;&nbsp;
						</td>
	     	 		</tr>
	     	</table><br>
	      </form>
      </div>
      <div id="manager_Goods_insertForm_img">
			<img src="resources/upload/like.jpg" width="150px" height="200px">
	  </div>
    </div>
    <div id="manager_Goods_Goodstb_col"><center>
    	<table border="1" width="100%">
    		<tr style="background: orange;" align="center">
	     		<td width="10%">상품번호</td>
	     		<td width="5%">상품코드</td>
	     		<td width="10%">상품이름</td>
	     		<td width="10%">상품단가</td>
	     		<td width="10%">브랜드</td>
	     		<td width="10%">카테고리</td>
	     		<td width="6%">소재</td>
	     		<td width="5%">추천도</td>
	     		<td width="6%">색상</td>
	     		<td width="6%">사이즈</td>
	     		<td width="6%">재고</td>
	     		<td width="6%">거래처</td>
	     		<td width="10%">이미지</td> 
     		</tr>
    	</table> 	
    </div>
	<div id="manager_Goods_Goodstb">
		<table border="1" width="100%">
	    	<c:forEach var="g" items="${manager_Goods_list}">
	     		<tr class="manager_Goods_Goodstb_trs">
	     			<td width="10%">${g.g_no }</td>
     				<td width="5%">${g.g_code }</td>
     				<td width="10%">${g.g_name }</td>
     				<td width="10%">${g.price }</td>
     				<td width="10%">${g.brand }</td>
     				<td width="10%">${g.category }</td>
     				<td width="6%">${g.material }</td>
     				<td width="5%">${g.recommend }</td>
     				<td width="6%">${g.color }</td>
     				<td width="6%">${g.g_size }</td>
     				<td width="6%">${g.amount }</td>
     				<td width="6%">${g.source_no }</td>
     				<td width="10%">${g.g_img }</td>
     			</tr>
     		</c:forEach>
	   	</table><br><br>
	</center></div>
</body>
</html>