<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/jq.js">
</script>
<script type="text/javascript">
$(function(){
	var fileTarget = $('.filebox .upload-hidden');
	var cap_no;
	var outer_no;
	var top_no;
	var bottom_no;
	var shoes_no;
    fileTarget.on('change', function(){
        if(window.FileReader){
            // 파일명 추출
            var filename = $(this)[0].files[0].name;
        } 

        else {
            // Old IE 파일명 추출
            var filename = $(this).val().split('/').pop().split('\\').pop();
        };

        $(this).siblings('.upload-name').val(filename);
    });

    //preview image 
    var imgTarget = $('.preview-image .upload-hidden');

    imgTarget.on('change', function(){
        var parent = $(this).parent();
        parent.children('.upload-display').remove();

        if(window.FileReader){
            //image 파일만
            if (!$(this)[0].files[0].type.match(/image\//)) return;
            
            var reader = new FileReader();
            reader.onload = function(e){
                var src = e.target.result;
                parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
                $("#codi-box-image").empty();
                $("#codi-box-image").append($("<img/>",{"src":src,"width":"400","height":"auto","id":"codi-box-uploadfile"}));
               
            }
            reader.readAsDataURL($(this)[0].files[0]);
        }

        else {
            $(this)[0].select();
            $(this)[0].blur();
            var imgSrc = document.selection.createRange().text;
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

            var img = $(this).siblings('.upload-display').find('img');
            img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";        
        }
    });

    $("#codi-enter-button").click(function(){
		
// 		var move1 = "insertCodi.do?m_no=" + $("#m_no").val() + "&m_level=" + $("#m_level").val();
		var move1 = "insertCodi.do";
		if($("#codi-box-uploadfile").length){
			$("#codi-insert-form").attr("action",move1);
			move();
		}else{
			alert("코디 이미지를 넣어주세요!");
			
		}
	});
	function move(){
		$("#codi-insert-form").submit();
	}

	function listTable(src){
		var td = $("<td></td>").append($("<img/>",{"src":src,"width":100,"height":100}));
		$("#insert-codi-table-tr").append(td);
	}
	function getTrNumber(){
		var tds = $("#insert-codi-table-tr").children().length;
	}
	function recoverButton(){
		$("#td-cap").empty();
		$("#td-outer").empty();
		$("#td-top").empty();
		$("#td-bottom").empty();
		$("#td-shoes").empty();
	}
	
	$("#codi-recover-button").bind("click",recoverButton);
	
	var codi_table = $("#insert-codi-table");
	$(".capimg").each(function(){
		$(this).bind('click',function(){
			var src = $(this).attr("src");
			var no = $(this).attr("no");
			cap_no = no;
			$("#td-cap").empty();
			$("#td-cap").append($("<img/>",{"src":src,"width":90,"height":90,"id":"cap-image"}),
					$("<input/>",{"type":"hidden","value":no,"name":"cap","id":"cap-no"}));
			$("#cap-image").mouseover(function(){
				$(this).css({"opacity":"0.5"})
			});
			$("#cap-image").mouseleave(function(){
				$(this).css({"opacity":""})
			});
			$("#cap-image").click(function(){
				$("#td-cap").empty();
			});
		});
	});
	$(".outerimg").each(function(){
		$(this).bind('click',function(){
			var src = $(this).attr("src");
			var no = $(this).attr("no");
			outer_no = no;
			$("#td-outer").empty();
			$("#td-outer").append($("<img/>",{"src":src,"width":90,"height":90,"id":"outer-image"}),
					$("<input/>",{"type":"hidden","value":no,"name":"outer","id":"outer-no"}));
			$("#outer-image").mouseover(function(){
				$(this).css({"opacity":"0.5"})
			});
			$("#outer-image").mouseleave(function(){
				$(this).css({"opacity":""})
			});
			$("#outer-image").click(function(){
				$("#td-outer").empty();
			});
		});
	});
	$(".topimg").each(function(){
		$(this).bind('click',function(){
			var src = $(this).attr("src");
			var no = $(this).attr("no");
			top_no = no;
			$("#td-top").empty();
			$("#td-top").append($("<img/>",{"src":src,"width":90,"height":90,"id":"top-image"}),
					$("<input/>",{"type":"hidden","value":no,"name":"top","id":"top-no"}));
			$("#top-image").mouseover(function(){
				$(this).css({"opacity":"0.5"})
			});
			$("#top-image").mouseleave(function(){
				$(this).css({"opacity":""})
			});
			$("#top-image").click(function(){
				$("#td-top").empty();
			});
		});
	});
	$(".bottomimg").each(function(){
		$(this).bind('click',function(){
			var src = $(this).attr("src");
			var no = $(this).attr("no");
			bottom_no = no;
			$("#td-bottom").empty();
			$("#td-bottom").append($("<img/>",{"src":src,"width":90,"height":90,"id":"bottom-image"}),
					$("<input/>",{"type":"hidden","value":no,"name":"bottom","id":"bottom-no"}));
			$("#bottom-image").mouseover(function(){
				$(this).css({"opacity":"0.5"})
			});
			$("#bottom-image").mouseleave(function(){
				$(this).css({"opacity":""})
			});
			$("#bottom-image").click(function(){
				$("#td-bottom").empty();
			});
		});
	});
	
	$(".shoesimg").each(function(){
		$(this).bind('click',function(){
			var src = $(this).attr("src");
			var no = $(this).attr("no");
			shoes_no = no;
			$("#td-shoes").empty();
			$("#td-shoes").append($("<img/>",{"src":src,"width":90,"height":90,"id":"shoes-image"}),
					$("<input/>",{"type":"hidden","value":no,"name":"shoes","id":"shoes-no"}));
			$("#shoes-image").mouseover(function(){
				$(this).css({"opacity":"0.5"})
			});
			$("#shoes-image").mouseleave(function(){
				$(this).css({"opacity":""})
			});
			$("#shoes-image").click(function(){
				$("#td-shoes").empty();
			});
		});
	});

	
	$('#myTab a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
	
	
	
	$(".del").click(function(){
		
		
	});
});
</script>
<style type="text/css">
	#insert-codi-body{
		width:95%;
		margin:0px auto;
		height:auto;
		min-height:100%;
		//border:1px solid #bcbcbc;
	}
	#insert-codi-content{
		width:98%;
		height: auto;
		padding: 20px;
		margin-bottom:20px;
	}
	#insert-codi-panel{
		width:42%;
		height:auto;
		float:left;
		margin-left:10%;
	}
	#insert-codi-tabpanel{
		width:35%;
		height:auto;
		padding:20px;
		border:thin;
		margin-right:10%;
		margin-bottom:20px;
		float:right;
		//border: 1px solid #bcbcbc;
	}
	#cap{
      	overflow: auto;
      	height:350px;
      }
      #top{
      	overflow: auto;
      	height:350px;
      }
      #outer{
      	overflow: auto;
      	height:350px;
      }
      #bottom{
      	overflow: auto;
      	height:350px;
      }
      #shoes{
      	overflow: auto;
      	height:350px;
      }
      #insert-codi-box{
      	margin-left:50px;
      	width:90%;
      	margin-right:20%;
      	float:left;
      	height:500px;
      	overflow:auto;
      }
      #insert-codi-text{
      	float:left;
      	margin-bottom:5px;
      	color:gray;
      }
      .b_group{
      	width:200px;
      }
      #insert-codi-goods-table{
      	float:left;
      }
      .img-thumbnail{
      	width:140px;
      	height:140px;
      }
      #insert-codi-table{
      	margin-right:100px;
      }
      .where {
	  display: block;
	  margin: 25px 15px;
	  font-size: 11px;
	  color: #000;
	  text-decoration: none;
	  font-family: verdana;
	  font-style: italic;
	} 
	.img-thumbnail{
		width:140px;
		height:140px;
	}
	.filebox input[type="file"] {
	    position: absolute;
	    width: 1px;
	    height: 1px;
	    padding: 0;
	    margin: -1px;
	    overflow: hidden;
	    clip:rect(0,0,0,0);
	    border: 0;
	}
	
	.filebox label {
	    display: inline-block;
	    padding: .5em .75em;
	    color: #999;
	    font-size: inherit;
	    line-height: normal;
	    vertical-align: middle;
	    background-color: #fdfdfd;
	    cursor: pointer;
	    border: 1px solid #ebebeb;
	    border-bottom-color: #e2e2e2;
	    border-radius: .25em;
	}
	
	/* named upload */
	.filebox .upload-name {
	    display: inline-block;
	    padding: .5em .75em;
	    font-size: inherit;
	    font-family: inherit;
	    line-height: normal;
	    vertical-align: middle;
	    background-color: #f5f5f5;
	  border: 1px solid #ebebeb;
	  border-bottom-color: #e2e2e2;
	  border-radius: .25em;
	  -webkit-appearance: none; /* 네이티브 외형 감추기 */
	  -moz-appearance: none;
	  appearance: none;
	}
	
	/* imaged preview */
	.filebox .upload-display {
	    margin-bottom: 5px;
	}
	
	@media(min-width: 768px) {
	    .filebox .upload-display {
	        display: inline-block;
	        margin-right: 5px;
	        margin-bottom: 0;
	    }
	}
	
	.filebox .upload-thumb-wrap {
	    display: inline-block;
	    width: 54px;
	    padding: 2px;
	    vertical-align: middle;
	    border: 1px solid #ddd;
	    border-radius: 5px;
	    background-color: #fff;
	}
	
	.filebox .upload-display img {
	    display: block;
	    max-width: 100%;
	    width: 100% \9;
	    height: auto;
	}
	
	.filebox.bs3-primary label {
	  color: #fff;
	  background-color: #337ab7;
	    border-color: #2e6da4;
	}
	#tab-clothes{
		width:90px;
	}
</style>
</head>
<body>
<div id="insert-codi-body">
	<form method="post" id="codi-insert-form" enctype="multipart/form-data">
	<input type="hidden" name="m_no" value="${m_no }">

	<div id="insert-codi-content">
		<div id="insert-codi-panel">
			<div class="panel panel-default" id="insert-codi-box">
				<div class="panel-body" id="codi-box-image">
				</div>
			</div>
			<div id="insert-codi-goods-table">
				<center><h3><label>상품 목록</label></h3></center>
				<table id="insert-codi-table" width="620" height="auto" class="table">
					<tr height="10px" valign="middle">
						<td align="center">모자</td>
						<td align="center">아우터</td>
						<td align="center">상의</td>
						<td align="center">하의</td>
						<td align="center">신발</td>
					</tr>
					<tr id="insert-codi-table-tr">
						<td id="td-cap" align="center" width="124" height="100"></td>
						<td id="td-outer" align="center" width="124" height="100"></td>
						<td id="td-top" align="center" width="124" height="100"></td>
						<td id="td-bottom" align="center" width="124" height="100"></td>
						<td id="td-shoes" align="center" width="124" height="100"></td>
					</tr>
					
				</table>
			</div>
			<div>
			<center><button type="button" class="btn btn-default btn-lg b_group" id="codi-recover-button">다시하기</button></center>
			</div>
	</div>

	<div role="tabpanel" id="insert-codi-tabpanel">
	
		 <ul class="nav nav-tabs" role="tablist">
		 
			    <li role="presentation" class="active"><a href="#cap" aria-controls="cap" role="tab" data-toggle="pill" id="tab-clothes"><center>모자</center></a></li>
			    <li role="presentation"><a href="#outer" aria-controls="outer" role="tab" data-toggle="pill" id="tab-clothes"><center>아우터</center></a></li>
			    <li role="presentation"><a href="#top" aria-controls="top" role="tab" data-toggle="pill" id="tab-clothes"><center>상의</center></a></li>
			    <li role="presentation"><a href="#bottom" aria-controls="bottom" role="tab" data-toggle="pill" id="tab-clothes"><center>하의</center></a></li>
			    <li role="presentation"><a href="#shoes" aria-controls="shoes" role="tab" data-toggle="pill" id="tab-clothes"><center>신발</center></a></li>
		
		 </ul>
	
			 <div class="tab-content">
			 	<div role="tabpanel" class="tab-pane fade in active" id="cap">
			 		<c:forEach var="cap" items="${cap }">
						<img src="resources/upload/${cap.g_img }" id="cap${cap.g_no }" class="capimg img-thumbnail" no="${cap.g_no }" width="140" height="140">
					</c:forEach>
			 	</div>
			 	<div role="tabpanel" class="tab-pane fade" id="outer">
			    <c:forEach var="outer" items="${outer }">
					<img src="resources/upload/${outer.g_img }" id="outer${outer.g_no }" class="outerimg img-thumbnail" no="${outer.g_no }" width="140" height="140">
				</c:forEach>
			 	</div>
			 	
			    <div role="tabpanel" class="tab-pane fade" id="top">
				<c:forEach var="top" items="${top }">
					<img src="resources/upload/${top.g_img }" id="top${top.g_no }" class="topimg img-thumbnail" no="${top.g_no }" width="140" height="140">
				</c:forEach>
				</div>
			    
			 	 <div role="tabpanel" class="tab-pane fade" id="bottom">
			    <c:forEach var="bottom" items="${bottom }">
	
					<img src="resources/upload/${bottom.g_img }" id="bottom${bottom.g_no }" class="bottomimg img-thumbnail" width="140" height="140" no="${bottom.g_no }">
	
		
				</c:forEach>
			 	</div>
			 	
			 	<div role="tabpanel" class="tab-pane fade" id="shoes">
			    <c:forEach var="shoes" items="${shoes }">
					<img src="resources/upload/${shoes.g_img }" id="shoes${shoes.g_no }" class="shoesimg img-thumbnail" no="${shoes.g_no }" width="140" height="140">
				</c:forEach>
			 	</div>
			 	
			 	<div>
			 	<br>
			 	<span id="insert-codi-text">제목</span>
	  			<input type="text" class="form-control" placeholder="제목을 입력하세요." aria-describedby="basic-addon1" id="codi-title" name="title">
	  			</div>
	  			<div>
	  			<br>
	  			<span id="insert-codi-text">내용</span>
	  			<textarea rows="4" class="form-control" placeholder="내용을 입력하세요." id="codi-content" name="content"></textarea>
				</div>
				
				<br>
				<center>
				<div class="filebox bs3-primary preview-image">
	                            <input class="upload-name" value="파일선택" disabled="disabled" style="width: 200px;">
	
	                            <label for="input_file">업로드</label> 
	                          <input type="file" id="input_file" class="upload-hidden" name="uploadFile"> 
	            </div>
				</center>
				<br>
				
				<center>
				<p id="insert-codi-button">
	  			<button type="button" class="btn btn-default btn-lg" id="codi-enter-button">등록</button>
	  			<button type="button" class="btn btn-default btn-lg" id="codi-back-button">뒤로</button>
				</p>
				
				</center>
				
		 </div>
	</div>
</div>
	
	</form>
</div>

</body>
</html>