<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/jsp/common/jstl.jsp"%>


<!DOCTYPE html>
<html>
<head>

<title></title>
<%@include file="/jsp/common/css.jsp"%>
<link href="<%=request.getContextPath()%>/css/owl.carousel.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/owl.theme.default.min.css"
	rel="stylesheet">

</head>


<body>

	<!--[if lte IE 8]>	
	
	<script>
	   window.location.href="<%=request.getContextPath()%>/upgradeBrowser.html";
	</script>
		
	<![endif]-->

	<!--轮播图  -->
	<div id="carousel" class="owl-carousel owl-theme">
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/1.jpg" />
		</div>
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/2.jpg" />
		</div>
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/3.jpg" />
		</div>
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/1.jpg" />
		</div>
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/2.jpg" />
		</div>
		<div>
			<img src="<%=request.getContextPath()%>/images/carousel/3.jpg" />
		</div>

	</div>

	<%@include file="/jsp/common/js.jsp"%>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script>
		$(document).ready(function() {
			$("#carousel").owlCarousel({
				loop:true,				
				lazyLoad:true,
				autoplay:true,
				autoplayTimeout:5000,
				autoplayHoverPause:true,
				responsiveClass:true,
			    responsive:{
			        0:{
			            items:1			            
			        },
			        600:{
			            items:3		 
			        },
			        1000:{
			            items:5			          
			        }
			    }
				
			});
		});
	</script>
</body>
</html>