<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@page import="com.github.rcaller.rStuff.RCode"%>
<%@page import="com.github.rcaller.rStuff.RCaller"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#manager_Members_warp{
		text-align: center;
		height: 650px;
	}
	#manager_Members_form_table{
		position: relative; 
		float: left; 
		width: 35%; 
		height: 600px;
		margin-left: 70px;
		/*background-color: green; */
	}
	#user_no,#user_id{
		width: 20%; 
	}
	.manager_Members_col{
		background-color: orange;
	}
	#manager_Members_Boardlist_tb{
		position: relative; 
		float: left; 
		width: 50%; 
		height: 600px;
		margin-left: 40px;
		/*background-color: red; */
	}
	#manager_Members_msg{
		font: bold;
		color: red;
	}
	.sel_manager_Members_trs{
		background-color: red;
	}
	#manager-tab-content{
		padding:40px;
	}
	#manager-tabpanel{
		padding:20px;
	}
</style>
<script type="text/javascript" src="resources/jq.js"></script>
<script type="text/javascript">
	$(function() {
		//회원정보테이블 클릭시 해당 회원정보를 input에 뿌려주는 쿼리
		$(".manager_Members_trs").click(function() {
			$(".manager_Members_trs").removeClass('sel_manager_Members_trs');
			$(this).addClass('sel_manager_Members_trs');
			var arr = $("input[type=text]");
			var tds = $(this).find("td");
			$(tds).each(function(index,item) {
				$(arr[index]).val($(tds[index]).text());
			});
			var n = $(tds[$(tds).length-1]).text();
			$("#user_code option:eq("+n+")").prop("selected", true);
			//alert("회원등급 : "+n);
		});
		
		//회원정보 수정여부 확인 메세지 타이머
		setTimeout(function() {
			//alert("Timeout 작동");
			$("#manager_Members_msg").hide();
		},3000);
		
		//모든 글 목록 테이블 클릭시 해당 글 상세페이지로 이동.
		$(".manager_Members_Boardlist_trs").click(function() {
			var b_no = $(this).children().eq(3).html();
			var b_type = $(this).children().eq(5).html();
			
			$(location).attr('href',"manager_Members_Boardlist_detail.do?b_no="+b_no+"&b_type="+b_type);
			 
			//alert("글번호 : "+b_no);
			//alert("글종류 : "+b_type);
		});
	});
</script>
</head>
<body><br><br>
	<div id="manager_Members_warp">
	<h3 id="manager_Members_msg">${manager_Members_msg }</h3><br>
	<!--회원목록 & 회원 등급 조정 부분 시작-->
		<div id="manager_Members_form_table">
	<!--회원 검색 & 등급 조절 폼 시작 -->
			<form action="manager_Members.do" method="post">
				회원번호 : <input id="user_no" name="m_no" type="text" readonly="readonly"> &nbsp;&nbsp;	
				회원ID : <input id="user_id" name="id" type="text" readonly="readonly"> &nbsp;&nbsp; 	
				회원등급 : <select id="user_code" name="m_level">
							<option value="0">0.관리자
							<option value="1">1.일반회원
							<option value="2">2.VIP
							<option value="3">3.블랙리스트
						</select>
				<br><br><input type="submit" value="수정"> 
			</form><br>
	<!--회원 검색 & 등급 조절 폼 끝 -->
	<!--회원목록 테이블 시작-->
			<table id="user_tb" border="1" width="100%">
				<tr class="manager_Members_col">
					<td>회원번호</td>
					<td>회원ID</td>
					<td>회원등급</td>
				</tr>
				<c:forEach var="m" items="${manager_Members_list }">
					<tr class="manager_Members_trs">
						<td>${m.m_no }</td>
						<td>${m.id }</td>
						<td>${m.m_level }</td>
					</tr>
				</c:forEach>
			</table><br>
			<div role="tabpanel" id="manager-tabpanel">
			 <ul class="nav nav-tabs" role="tablist">
		 
			    <li role="presentation" class="active"><a href="#total" aria-controls="total" role="tab" data-toggle="pill" id="tab-charts"><center>TOTAL</center></a></li>
			    <li role="presentation"><a href="#codi" aria-controls="codi" role="tab" data-toggle="pill" id="tab-charts"><center>CODI</center></a></li>
			    <li role="presentation"><a href="#review" aria-controls="review" role="tab" data-toggle="pill" id="tab-charts"><center>REVIEW</center></a></li>
		 </ul>
			<%
	try{
		String path = request.getRealPath("/resources/lib");
		

		File pastFile = new File(path+"/");
		
		File[] pastFileList = pastFile.listFiles();
		
		if(pastFileList.length>0){
			for(int i=0;i<pastFileList.length;i++){
				pastFileList[i].delete();
			}
		}
		
		
		RCaller caller = new RCaller();
		caller.setRscriptExecutable("C:\\Program Files\\R\\R-3.3.2\\bin\\x64\\Rscript.exe");
		RCode code = new RCode();
		code.clear();
		
		File file;
		byte[] buf = new byte[1024];
		file = code.startPlot();
		System.out.println("Plot will be saved to : "+file);
//		code.addRCode("install.packages('labeling')");
// 		code.addRCode("install.packages('ggplot2')");
// 		code.addRCode("cat('.Rprofile: Setting UK repositoryn')");
// 		code.addRCode("r = getOption('repos') # hard code the UK repo for CRAN");
// 		code.addRCode("r['CRAN'] = 'http://cran.uk.r-project.org'");
// 		code.addRCode("options(repos = r)");
// 		code.addRCode("rm(r)");
		
// 		code.addRCode("install.packages('Runiversal')");
//  		code.addRCode("install.packages('plyr')");
//  		code.addRCode("install.packages('munsell')");
//  		code.addRCode("install.packages('reshape2')");
		code.addRCode("library(RODBC)");
 		code.addRCode("library(plyr)");
 		code.addRCode("library(reshape2)");
 		code.addRCode("library(labeling)");
		code.addRCode("library(ggplot2)");
 		code.addRCode("library(munsell)");
 		

//		code.addRCode("hit <- sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type between 1 and 2 GROUP BY id ORDER BY count desc')");
//		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill=''#FF696F')+labs(x='ID',y='작성한 글')");
		
  //		code.addRCode("x=c(1,2,3,4,5,6)");
 	//	code.addRCode("plot(x)");
		code.addRCode("dog=odbcConnect('dogfoot',uid='dogfoot',pwd='dogfoot')");
		code.addRCode("hit=sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type between 1 and 2 GROUP BY id')");
 		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill='#FF696F')+labs(x='ID',y='작성한 글')+ggtitle('글 작성 총 횟수')+theme(plot.title=element_text(face='bold',size=30,vjust=2,color='#FF696F'),axis.title.x=element_text(size=14),axis.text.x=element_text(size=16),axis.text.y=element_text(size=16))");
		code.addRCode("");
 		
 		code.endPlot();

		caller.setRCode(code);
		
		//  caller.redirectROutputToConsole();
		caller.runOnly();
		
		
		FileOutputStream fos = new FileOutputStream(path+"/"+file.getName());
		FileInputStream fis = new FileInputStream(file);
		
		int readCount;
		while((readCount=fis.read(buf)) != -1){
			fos.write(buf,0,readCount);
		}
		
		%>
		
		 <div class="tab-content" id="manager-tab-content">
		<div role="tabpanel" class="tab-pane fade in active" id="total">
			<img src="resources/lib/<%=file.getName()%>" width="420" height="auto">
		</div>
		
		
	<%
		
	}catch(Exception e){
		System.out.println(e);
	}

	try{
		RCaller caller = new RCaller();
		caller.setRscriptExecutable("C:\\Program Files\\R\\R-3.3.2\\bin\\x64\\Rscript.exe");
		RCode code = new RCode();
		code.clear();
		
		File file;
		byte[] buf = new byte[1024];
		file = code.startPlot();
		System.out.println("Plot will be saved to : "+file);
//		code.addRCode("install.packages('labeling')");
// 		code.addRCode("install.packages('ggplot2')");
// 		code.addRCode("cat('.Rprofile: Setting UK repositoryn')");
// 		code.addRCode("r = getOption('repos') # hard code the UK repo for CRAN");
// 		code.addRCode("r['CRAN'] = 'http://cran.uk.r-project.org'");
// 		code.addRCode("options(repos = r)");
// 		code.addRCode("rm(r)");
		
// 		code.addRCode("install.packages('Runiversal')");
//  		code.addRCode("install.packages('plyr')");
//  		code.addRCode("install.packages('munsell')");
//  		code.addRCode("install.packages('reshape2')");
		code.addRCode("library(RODBC)");
 		code.addRCode("library(plyr)");
 		code.addRCode("library(reshape2)");
 		code.addRCode("library(labeling)");
		code.addRCode("library(ggplot2)");
 		code.addRCode("library(munsell)");
 		

//		code.addRCode("hit <- sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type between 1 and 2 GROUP BY id ORDER BY count desc')");
//		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill=''#FF696F')+labs(x='ID',y='작성한 글')");
		
  //		code.addRCode("x=c(1,2,3,4,5,6)");
 	//	code.addRCode("plot(x)");
		code.addRCode("dog=odbcConnect('dogfoot',uid='dogfoot',pwd='dogfoot')");
		code.addRCode("hit=sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type=1 GROUP BY id')");
 		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill='#3D6BF2')+labs(x='ID',y='작성한 글')+ggtitle('코디 게시판 글 수')+theme(plot.title=element_text(face='bold',size=30,vjust=2,color='#3D6BF2'),axis.title.x=element_text(size=14),axis.text.x=element_text(size=16),axis.text.y=element_text(size=16))");
		code.addRCode("");
 		
 		code.endPlot();

		caller.setRCode(code);
		
		//  caller.redirectROutputToConsole();
		caller.runOnly();
		
		String path = request.getRealPath("/resources/lib");
		System.out.println("path경로 : "+path);
		FileOutputStream fos = new FileOutputStream(path+"/"+file.getName());
		FileInputStream fis = new FileInputStream(file);
		
		int readCount;
		while((readCount=fis.read(buf)) != -1){
			fos.write(buf,0,readCount);
		}
		
		%>
		<div role="tabpanel" class="tab-pane fade" id="codi">
			<img src="resources/lib/<%=file.getName()%>" width="420" height="auto">
		</div>
		<%
		
	}catch(Exception e){
		System.out.println(e);
	} 
	try{
		RCaller caller = new RCaller();
		caller.setRscriptExecutable("C:\\Program Files\\R\\R-3.3.2\\bin\\x64\\Rscript.exe");
		RCode code = new RCode();
		code.clear();
		
		File file;
		byte[] buf = new byte[1024];
		file = code.startPlot();
		System.out.println("Plot will be saved to : "+file);
//		code.addRCode("install.packages('labeling')");
// 		code.addRCode("install.packages('ggplot2')");
// 		code.addRCode("cat('.Rprofile: Setting UK repositoryn')");
// 		code.addRCode("r = getOption('repos') # hard code the UK repo for CRAN");
// 		code.addRCode("r['CRAN'] = 'http://cran.uk.r-project.org'");
// 		code.addRCode("options(repos = r)");
// 		code.addRCode("rm(r)");
		
// 		code.addRCode("install.packages('Runiversal')");
//  		code.addRCode("install.packages('plyr')");
//  		code.addRCode("install.packages('munsell')");
//  		code.addRCode("install.packages('reshape2')");
		code.addRCode("library(RODBC)");
 		code.addRCode("library(plyr)");
 		code.addRCode("library(reshape2)");
 		code.addRCode("library(labeling)");
		code.addRCode("library(ggplot2)");
 		code.addRCode("library(munsell)");
 		

//		code.addRCode("hit <- sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type between 1 and 2 GROUP BY id ORDER BY count desc')");
//		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill=''#FF696F')+labs(x='ID',y='작성한 글')");
		
  //		code.addRCode("x=c(1,2,3,4,5,6)");
 	//	code.addRCode("plot(x)");
		code.addRCode("dog=odbcConnect('dogfoot',uid='dogfoot',pwd='dogfoot')");
		code.addRCode("hit=sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no and b_type=2 GROUP BY id')");
 		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill='#55C963')+labs(x='ID',y='작성한 글')+ggtitle('후기 게시판 글 수')+theme(plot.title=element_text(face='bold',size=30,vjust=2,color='#55C963'),axis.title.x=element_text(size=14),axis.text.x=element_text(size=16),axis.text.y=element_text(size=16))");
		code.addRCode("");
 		
 		code.endPlot();

		caller.setRCode(code);
		
		//  caller.redirectROutputToConsole();
		caller.runOnly();
		
		String path = request.getRealPath("/resources/lib");
		FileOutputStream fos = new FileOutputStream(path+"/"+file.getName());
		FileInputStream fis = new FileInputStream(file);
		
		int readCount;
		while((readCount=fis.read(buf)) != -1){
			fos.write(buf,0,readCount);
		}
		
		%>
		<div role="tabpanel" class="tab-pane fade" id="review">
			<img src="resources/lib/<%=file.getName()%>" width="420" height="auto">
		</div>
		</div>
		<%
		
	}catch(Exception e){
		System.out.println(e);
	}%>

		</div>
		</div>
		
	<!-- 회원목록 테이블 끝-->
	<!-- 회원목록 & 회원 등급 조정 부분 끝-->
		 <div id="manager_Members_Boardlist_tb">
		 	<h3>전체 게시글 목록</h3>
		 	<table id="user_tb" border="1" width="100%">
				<tr class="manager_Members_col">
					<td>회원번호</td>
					<td>회원ID</td>
					<td>회원등급</td>
					<td>글번호</td>
					<td>글제목</td>
					<td>글타입</td>
					<td>작성일</td>
					<td>조회수</td>
					<td>댓글수</td>
				</tr>
				<c:forEach var="b" items="${manager_Members_Boardlist }">
					<tr class="manager_Members_Boardlist_trs">
						<td>${b.m_no }</td>
						<td>${b.b_img }</td>
						<td>${b.g_code }</td>
						<td>${b.b_no }</td>
						<td>${b.title }</td>
						<td>${b.b_type }</td>
						<td>${b.create_time }</td>
						<td>${b.hit }</td>
						<td>${b.c_count }</td>
					</tr>
				</c:forEach>
			</table><br>
		 </div>	
     </div></center>
</body>
</html>