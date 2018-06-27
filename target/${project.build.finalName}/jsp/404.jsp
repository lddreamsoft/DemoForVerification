<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>404</title>

<!--不管知不知道自己的宽高，需要元素在屏幕中水平垂直居中，全版本兼容方法：
     body,html{
            height: 100%;
        }
        body{
            text-align: center;
        }
        span{
            display: inline-block;
            height: 100%;
            vertical-align: middle;
        }
        div{
           
            border: solid 1px red;
            vertical-align: middle;
            display: inline-block;
            *display: inline;
            *zoom:1;
            text-align: left;
        }   
		
<span></span><div class="box"><img src="./images/logo.png"/>测试文字<br>asdfasdf<br>asdafasdfsdafadsf</div>
			
		
		不管知不道自己的宽高，知道父容器的宽高，在父容器中水平垂直居中，全版本兼容方法：             
	        .box{ 
				    width:200px; 
				    height:200px; 
				    border:1px solid #06C; 
				    display:table-cell; 
				    text-align:center; 
				    vertical-align:middle; 
				    *font-family:simsun;*font-size:200px;
				}
				.box img{
				    vertical-align:middle;
				}  		
				
			<div class="box">
			  <img src="./images/logo.png"/>
			</div>
			
		   不管知不知道自己的宽高，不知道父容器的宽高，需要在父容器中水平垂直居中，全版本兼容方法：    
       
            
            
    -->

<style>
body, html {
	height: 100%;
	background-color: #FFFDE8;
}

body {
	text-align: center;
}

div {
	/*border: solid 1px red;*/
	vertical-align: middle;
	display: inline-block;
	*display: inline;
	*zoom: 1;
	text-align: center;
}

table {
	font-size: 0px;
}
</style>
</head>
<body>



	<div style="margin: 0 auto;">
		<table id="__01" width="700" height="500" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td colspan="3"><img src="<%=request.getContextPath()%>/images/404/1.png" width="700"
					height="215" alt=""></td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/404/2.png" width="140" height="53" alt=""></td>
				<td id="count" width="63" height="53"
					style="color: orange; font-size: 30px; font-weight: bold; text-align: right; position: relative; bottom: -2px;">
					300</td>
				<td><img src="<%=request.getContextPath()%>/images/404/4.png" width="497" height="53" alt=""></td>
			</tr>
			<tr>
				<td colspan="3"><img src="<%=request.getContextPath()%>/images/404/5.png" width="700"
					height="232" alt=""></td>
			</tr>
		</table>

	</div>

	<script>
		function countDown() {
			var count = parseInt(document.getElementById("count").innerHTML);
			count--;
			document.getElementById("count").innerHTML = count;

			if (count == 0) {
				window.location.href = "http://localhost:8080/hlhh/";
			}
		}

		setInterval(countDown, 1000);
	</script>


</body>
</html>