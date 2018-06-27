<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/jsp/common/css.jsp"%>
<link href="../../css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
@media only screen and (min-width:800px) {
	.class_Seperator {
		width: 80px;
		display: inline-block;
	}
	.class_Verification {
		width: 80px;
		display: inline-block;
		text-align: right;
	}
	.class_PhoneNumber {
		width: 80px;
		display: inline-block;
		text-align: right;
	}
	.class_userNameRegion {
		padding-top: 30px;
		font-size: 16px;
		text-align: center;
	}
}

@media only screen and (min-width:481px) and (max-width:800px) {
	.class_Seperator {
		width: 80px;
		display: inline-block;
	}
	.class_Verification {
		width: 80px;
		display: inline-block;
		text-align: right;
	}
	.class_userNameRegion {
		padding-top: 30px;
		font-size: 16px;
		text-align: center;
	}
	.class_PhoneNumber {
		width: 80px;
		display: inline-block;
		text-align: right;
	}
}

@media only screen and (max-width:480px) {
	.class_Seperator {
		display: none;
	}
	.class_Verification {
		width: 80px;
		display: block;
		text-align: left;
	}
	.class_userNameRegion {
		padding-top: 30px;
		font-size: 16px;
		text-align: center;
	}
	.class_PhoneNumber {
		width: 80px;
		display: block;
		text-align: left;
	}
}
</style>
</head>

<body style="font-family: 'microsoft yahei'">


	<div class="container">

		<div style="padding: 30px; padding-bottom: 50px;">
			<img src="/hlhh/images/logo.png" /> <span
				style="font-size: 20px;; font-weight: 550; margin-left: 10px;">用户注册</span>
		</div>

		<div style="padding-bottom: 40px; border-bottom: 3px solid #ddd;">
			<ul class="nav nav-pills col-sm-12"
				style="padding: 0; font-size: 20px;">
				<li class="col-xs-12 col-sm-4" style="margin: 0; margin-top:10px;text-align: center;"><i
					class="fa fa-circle" aria-hidden="true"
					style="color: #a5de51; position: relative;"><span
						style="color: #ffffff; margin-left: -1.20px;; margin-right: 0.50px;; font-weight: bold;">1</span></i>
					
					<span style="display:inline-block;width:120px;text-align:left;">设置用户名</span>
					
					
					</li>

				<li class="col-xs-12 col-sm-4"
					style="color: #999999; margin: 0; margin-top:10px; text-align: center;"><i
					class="fa fa-circle" aria-hidden="true" style="position: relative;"><span
						style="color: #ffffff; margin-left: -1.20px; margin-right: 0.50px; font-weight: bold;">2</span></i>
					
					
								
					<span style="display:inline-block;width:120px;text-align:left;">填写帐号信息</span>
					</li>

				<li class="col-xs-12 col-sm-4"
					style="color: #999999; margin: 0; margin-top:10px; text-align: center;"><i
					class="fa fa-check-circle" aria-hidden="true"
					style="position: relative;"><span
						style="color: #ffffff; margin-left: -1.20px; margin-right: 0.50px; font-weight: bold;">3</span></i>
					
					<span style="display:inline-block;width:120px;text-align:left;">注册成功</span>
					
					</li>
			</ul>

		</div>

		<div id="userNameRegion" class="class_userNameRegion">

			<div style="display: inline-block;">
				<span class="class_PhoneNumber">手机号码</span> <input type="text"
					style="display: inline; width: 250px; cursor: pointer;"
					class="form-control" placeholder="请输入您的手机号码"> <span
					style="width: 20px; display: inline-block;"> </span>

			</div>

			<p>
			<p />

			<div
				style="margin-top: 10px; display: inline-block; position: relative;">
				<span class="class_Verification">验证</span>

				<!-- 验证码区域定宽、定高 -->
				<div id="verificationCodeRegion"
					style="cursor: pointer; display: none; text-align: center; bottom: 34px; border-radius: 10px;; position: absolute;">

					<img id="img_verificationCode" class=""
						style="width: 250px; height: 150px; border-radius: 10px;" />

				</div>


				<input type="text" id="verificationCode" readonly
					style="display: inline; width: 250px; height: 34px; cursor: pointer;"
					class="form-control " placeholder=" "> <i
					class="fa fa-refresh" aria-hidden="true"
					onclick="refreshVerificationCode();"
					style="font-size: 20px; color: #a5de51; cursor: pointer; width: 20px;"></i>

			</div>


			<div style="margin-top: 10px;">


				<span class="class_Seperator"> </span>

				<button type="button" class="btn btn-danger form-control"
					style="width: 250px;">下一步</button>

				<span style="width: 20px; display: inline-block;"> </span>


			</div>

		</div>


	</div>

	<div style="margin-top: 100px;; color: #000000; padding: 30px;"
		class="text-center col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
		<i style="">Powered by ldsoft</i>
	</div>


</body>

<%@include file="/jsp/common/js.jsp"%>
<script src="/hlhh/js/jquery.cookie.js"></script>

<script>
	var clickTimes = 0;
	var clickWordsInfo = ''; //点击的文字

	$("#img_verificationCode")
			.click(
					function(e) {
						//pageX表示全局坐标，offsetX表示左上角坐标
						//在鼠标点击处添加点击成功图标
						//判断鼠标点击位置是否在图片边缘，防止图片绘制超出图片边框
						//假定图片的宽度为250px,高度为150px

						var paintX, paintY; //绘制图片的X坐标和Y坐标

						paintX = e.offsetX - 10;
						paintY = e.offsetY - 15;

						//超出右边界
						if (e.offsetX + 15 > 250) {
							paintX = 235 - 10;
						}

						//超出左边界
						if (e.offsetX - 15 < 0) {
							paintX = 0;
						}

						//超出上边界
						if (e.offsetY - 15 < 0) {
							paintY = 0;
						}

						//超出下边界
						if (e.offsetY + 15 > 150) {
							paintY = 135 - 10;
						}

						$("#verificationCodeRegion")
								.append(
										"<span class='clickSpan' style='position:absolute;z-index:1;top:"+paintY+"px;left:"+paintX+"px;font-size:30px;color:red;'>"
												+ "  <i class='fa fa-check-circle-o'></i>"
												+ "</span>");

						clickWordsInfo += e.offsetX + "," + e.offsetY + ","
						clickTimes++;

						//console.log("e.offsetX:"+e.offsetX);
						//console.log("e.offsetY:"+e.offsetY);

						//点击2次后向服务器发送验证请求
						//1、服务器验证成功placeholder改为验证成功
						//2、服务器验证成功placeholder改为验证失败，同时刷新点击验证图片

						if (clickTimes == 2) {

							//debugger;
							$.get("/hlhh/verificationCode/check", {
								wordsInfo : clickWordsInfo
							}, function(result) {

								if (result == "passed") {
									$("#verificationCode").attr("placeholder",
											"验证成功");
								} else if (result == "failed") {
									$("#verificationCode").attr("placeholder",
											"验证失败");
									//刷新验证码图片
									refreshVerificationCode();
								}

							});

							clickTimes = 0;
							clickWordsInfo = '';
						}

					});

	function refreshVerificationCode() {

		//图片验证码的刷新可能会比较耗时，加载完成之前显示loading.gif，因图片无法显示，加上loading遮罩
		//document.getElementById('img_verificationCode').src='/hlhh/images/common/loading.gif';

		//清除图片中的√
		$(".clickSpan").remove();
		//清除图片中的图片加载中
		$("#loadingRegion").remove();

		$("#verificationCodeRegion")
				.append(
						"<img id='loadingRegion' src='/hlhh/images/svg/loading.svg' style='width:40px;height:40px;position:absolute;z-index:1;top:50%;left:50%;transform:translate(-50%,-50%);'/>");

		//清除cookie
		if ($.cookie("VerificationCodeUID") != undefined) {
			$.cookie("VerificationCodeUID", "", {
				path : '/hlhh'
			});
		}

		document.getElementById('img_verificationCode').src = "/hlhh/verificationCode/gen?"
				+ Math.random();

		//重新弹出图片
		$('#verificationCodeRegion').css("left",
				$("#verificationCode").position().left);
		//$('#verificationCodeRegion').css("height", "180px");
		$("#verificationCodeRegion").slideDown("slow");

		/*
		document.getElementById('img_verificationCode').onload = function(e) {
			e.stopPropagation();
			//去除loading区域
			$('#loadingRegion').remove();
			
			//从cookie中读取点击提示文字 
			$("#verificationCode").attr("placeholder","请依次点击图片中的："+$.cookie("VerificationCode").split("_")[0]+" "+$.cookie("VerificationCode").split("_")[1]);
		}
		 */

	}

	var mouseMoveIn = 0;

	$(document)
			.mousemove(
					function(e) {

						if (mouseMoveIn == 0) {
							//如果鼠标移入验证码区域，向上弹出验证码图片
							if (e.pageX > $("#verificationCode").offset().left
									&& e.pageX < ($("#verificationCode")
											.offset().left + document
											.getElementById("verificationCode").offsetWidth)) {
								if (e.pageY >= $("#verificationCode").offset().top
										&& e.pageY <= ($("#verificationCode")
												.offset().top + document
												.getElementById("verificationCode").offsetHeight)) {
									//限制只能执行一次
									//console.log("执行了1次");
									mouseMoveIn = 1;

									//判断cookie是否失效，cookie失效则重新请求验证码，否则加载现有验证码
									if ($.cookie("VerificationCodeUID") == undefined
											|| $.cookie("VerificationCodeUID") == "") {
										//向服务器重新发送请求，请求验证码图片
										document
												.getElementById('img_verificationCode').src = "/hlhh/verificationCode/gen?"
												+ Math.random();
									}

									$('#verificationCodeRegion')
											.css(
													"left",
													$("#verificationCode")
															.position().left);
									//$('#verificationCodeRegion').css("height", "180px");
									$("#verificationCodeRegion").slideDown(
											"slow");
								}
							}
						}

						//如果鼠标移出验证码区域和弹出验证码图片区域，向下收起验证码图片
						//这里右侧x+20是因为右侧还有一个刷新图标
						//这里下侧y+20是为下侧冗余一定的空间，避免反复刷新验证码

						if (e.pageX < $("#verificationCode").offset().left
								|| e.pageX > ($("#verificationCode").offset().left + 250 + 50)) {
							mouseMoveIn = 0;
							$("#verificationCodeRegion").slideUp("slow");
						}

						if (e.pageY < ($("#verificationCode").offset().top - 150)
								|| e.pageY > ($("#verificationCode").offset().top + 34 + 20)) {
							mouseMoveIn = 0;
							$("#verificationCodeRegion").slideUp("slow");
						}

					});

	$(function() {

		//页面刷新后不等cookie失效时间到来，强制清除cookie
		//如果不清除会出现cookie还在，但cookie对应的图片因为刷新后不存在了，导致图片显示为空白 

		if ($.cookie("VerificationCodeUID") != undefined) {
			$.cookie("VerificationCodeUID", "", {
				path : '/hlhh'
			});
		}

		document.getElementById('img_verificationCode').onload = function(e) {
			e.stopPropagation();
			//去除loading区域
			$('#loadingRegion').remove();

			//从cookie中读取点击提示文字 
			$("#verificationCode").attr(
					"placeholder",
					"请依次点击图片中的：" + $.cookie("VerificationCode").split("_")[0]
							+ " " + $.cookie("VerificationCode").split("_")[1]);
		}

		$.ajaxSetup({
			cache : false
		});

	});
</script>

</html>







