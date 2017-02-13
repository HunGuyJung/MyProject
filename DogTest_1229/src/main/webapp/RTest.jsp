
<%@page import="com.github.rcaller.rStuff.RCode"%>
<%@page import="com.github.rcaller.rStuff.RCaller"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
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
	code.addRCode("hit=sqlQuery(dog,'SELECT m.id id,count(b.b_no) count FROM board b, member m WHERE m.m_no=b.m_no GROUP BY id')");
 		code.addRCode("ggplot(head(hit,5),aes(x=reorder(ID,-COUNT),y=COUNT))+geom_bar(stat='identity', width=0.4, position='dodge', fill='#FF696F')+labs(x='ID',y='작성한 글')");
		code.endPlot();

		caller.setRCode(code);
		
		//  caller.redirectROutputToConsole();
		caller.runOnly();
		code.showPlot(file);
		
		String path = request.getRealPath("/resources/lib");
		FileOutputStream fos = new FileOutputStream(path+"/"+file.getName());
		FileInputStream fis = new FileInputStream(file);
		
		int readCount;
		while((readCount=fis.read(buf)) != -1){
			fos.write(buf,0,readCount);
		}
		
		%>
		<img src="resources/lib/<%=file.getName()%>" width="200" height="auto">
		<%
		
	}catch(Exception e){
		System.out.println(e);
	}
%>

</body>
</html>