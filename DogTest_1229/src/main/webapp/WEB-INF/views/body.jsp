<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
#front-body{
	width:100%;
	height:100%;
}
#front-banner{
	margin-top:15px;
}
body{
	background: black;
}

</style>
<script type="text/javascript" src="resources/jq.js">
</script>
<script type="text/javascript">
$(function(){
	
});
</script>
<style type="text/css">
.carousel-inner{
	z-index: 0;
}
.carousel-control{
	z-index: 0;
}
.carousel{
	z-index: 0;
}
</style>
</head>
<body>
<br>
<div id="front-body">
 <div id="front-banner">
        
			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
		
		      <!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox" >
					<c:forEach var="e" items="${front }" varStatus="status">
					<c:choose>
						<c:when test="${status.index eq 0}">
							<div class="item active">
						      <center><a href="detailEventBoard.do?b_no=${e.b_no }"><img src="resources/event/${e.b_img }" width="auto" height="650px"></a></center> 
						      <div class="carousel-caption">
						      </div>
						      
						    </div>   
						</c:when>
						<c:otherwise>
							<div class="item ">
						      <center><a href="detailEventBoard.do?b_no=${e.b_no }"><img src="resources/upload/${e.b_img }" width="auto" height="650px"></a></center> 
						      <div class="carousel-caption">
						      </div>
						      
						    </div>   
						</c:otherwise>
					</c:choose>
					   
					</c:forEach>    			    
					    
					   			    
				  </div>
				  
				<!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
			</div>
		
      </div>
</div>
</body>
</html>