<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>



<!DOCTYPE html>
<html>
<head>

<title></title>
<%@include file="/jsp/common/css.jsp"%>
<link href="../../css/font-awesome.min.css" rel="stylesheet">
<style>
a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
</style>



</head>



<body style="font: 12px/150% Arial, Verdana; background-color: #252525;">



	<div
		style="width: 100%; height: 10rem; color: #FFFFFF; line-height: 10rem;">

		<span
			style="font-size: 3rem; margin-left: 10rem; font-family: 'microsoft yahei'"><img
			src="/hlhh/images/logo.png" style="" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎登录</span>
	</div>

	<!-- 垂直居中(需要知道父容器高度)：父容器：position:relative 子容器：position: absolute;top:50%;left:50%;width:100%;transform:translate(-50%,-50%); -->
	<div class=" text-center col-xs-12 col-sm-12 col-md-12 col-lg-12"
		style="background-color: #FFFFFF;">
		<div class="row">

			<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1"></div>

			<div class="col-xs-0 col-sm-0 col-md-5 col-lg-6"
				style="padding-left: 0px; padding-right: 0px;">
				<img src="/hlhh/images/loginMg/1.png" class="img-responsive" />
			</div>

			<div class="col-xs-12 col-sm-12 col-md-5 col-lg-4"
				style="padding-left: 0px; padding-right: 0px; margin-right: -10rem;">
				<ul class="nav nav-tabs"
					style="margin-top: 5rem; text-align: center; font-size: 1.8rem; font-weight: 700; font-family: 'microsoft yahei'">
					<li role="presentation" class="active" id="tab1"
						onclick="toogleTab(this);" style="width: 15rem;"><a href="#"
						id="title1" style="color: #e4393c;"
						onmouseover="this.style.cssText='color:#e4393c;'"
						onmouseout="if($('#tab1').attr('class')!='active') {this.style.cssText='color:#666666'}">扫码登录</a></li>


					<li role="presentation" id="tab2" onclick="toogleTab(this);"
						style="width: 15rem;"><a href="#" id="title2"
						style="color: #666666"
						onmouseover="this.style.cssText='color:#e4393c;'"
						onmouseout="if($('#tab2').attr('class')!='active') {this.style.cssText='color:#666666'}">帐户登录</a></li>
				</ul>
				<div id="QRCodeRegion"
					style=" margin-bottom: 1rem; padding: 3rem; border: 0.1rem solid #dddddd; border-top: 0;height:28rem;">

					<div id="QRCodeMask">
						<img id="QRCode" style="width: 15rem; height: 15rem;"
							src="/hlhh/QRCode/genQRCodeForMg" />
					</div>

					<p>
						打开<span style="color: #e4393c;">微信</span> 扫描二维码登录
					</p>

					<p>

						<i class="fa fa-bolt" aria-hidden="true"
							style="font-size: 2rem; color: #B2E667"></i> <i
							style="font-size: 1rem; font-family: microsoft yahei; color: #666666">&nbsp;更快</i>
						<i class="fa fa-plus-circle" aria-hidden="true"
							style="font-size: 2rem; color: #B2E667; margin-left: 1rem;"></i>
						<i
							style="font-size: 1rem; font-family: microsoft yahei; color: #666666">&nbsp;更安全</i>

					</p>


					<div style="border-top: 0.1rem solid #dddddd; margin-top: 2rem;">

						<a  href="/hlhh/jsp/register/registerMg.jsp"
							style="margin-top: 1rem; float: right; color: #e4393c; font-size: 1.6rem; cursor: pointer;"><i
							class="fa fa-chevron-circle-right" aria-hidden="true"></i>立即注册</a>

					</div>

				</div>
				<div id="AccountRegion"
					style="height:28rem;margin-bottom: 1rem; padding: 5rem; padding-bottom: 2rem; position: relative; border: 0.1rem solid #dddddd; border-top: 0;">


					<div class="input-group">
						<div class="input-group-addon">$</div>
						<input type="text" class="form-control" id="exampleInputAmount"
							placeholder="用户名/已验证手机">
					</div>

					<div class="input-group" style="margin-top: 2rem;">
						<div class="input-group-addon">$</div>
						<input type="text" class="form-control" id="exampleInputAmount"
							placeholder="密码">
					</div>

					<div class="input-group" style="margin-top: 2rem; width: 100%;">

						<input type="checkbox"
							style="vertical-align: middle; margin-top: 3px; margin-bottom: 1px; float: left;">
						<span style="margin-left: 0.5rem; float: left;">自动登录</span> <span
							style="float: right;">忘记密码</span>
					</div>

					<button type="button" class="btn btn-danger"
						style="width: 100%; margin-top: 2rem; font-weight: 500; border-radius: 2rem; font-size: 2rem; font-family: 'microsoft yahei'">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>

					<div
						style="border-top: 0.1rem solid #dddddd; margin-top: 2rem; height: 2rem;">

						<a  href="/hlhh/jsp/register/registerMg.jsp"
							style="margin-top: 1rem; float: right; color: #e4393c; font-size: 1.6rem; cursor: pointer;"><i
							class="fa fa-chevron-circle-right" aria-hidden="true"></i>立即注册</a>

					</div>



				</div>
			</div>

			<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1"></div>
		</div>
	</div>


	<div style="color: #FFFFFF; padding: 3rem;"
		class="text-center col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
		<i style="">Powered by ldsoft</i>
	</div>


</body>

<%@include file="/jsp/common/js.jsp"%>
<script src="/hlhh/js/jquery.cookie.js"></script>



<script>

	//全局UID存储在cookie中，每隔2秒向服务器发送请求判断uid对应的二维码是否已经登录或失效， 失效则在服务器端重新生成uid后更新此值

	//二维码过期，重新获取二维码
	function refreshQRCode() {
		//1.去除遮罩
		$("#QRCodeMask").attr("style", "");

		//2.去除错误区域
		$('#errorRegion').remove();

		//3.重新获取图片 不能用jquery赋值，jquery赋值仅仅改变图片src的字符串值，并不重新发起请求
		document.getElementById('QRCode').src = "/hlhh/login/genQRCodeForMg?"
				+ Math.random();

	}

	var handle = self
			.setInterval(
					function() {

						$
								.get(
										"/hlhh/login/isUIDExpired",
										function(result) {

											//debugger;
											if (result.split(":")[1] == 1) {
												//二维码已过期 清除定时器，给二维码添加遮罩，并提示用户手工刷新图片
												$("#QRCodeMask")
														.attr("style",
																"filter: alpha(opacity=60);-moz-opacity: .6;opacity: .6;position:relative;");
												$("#QRCodeMask")
														.append(
																"<div id='errorRegion' style='position:absolute;z-index:1;top:50%;left:50%;transform:translate(-50%,-50%);font-weight:bold;font-size:1.5rem;'><p style='color:gray;'>二维码已失效</p><button type='button' class='btn btn-danger' style='border-radius: 0rem;' onclick='refreshQRCode();'>刷新</button></div>");

												window.clearInterval(handle);
											} else if (result.split(":")[1] == 0) {
												//二维码未过期 不做任何处理
											}

										});

					}, 2000);

	function toogleTab(object) {
		for (var i = 1; i <= 3; i++) {
			if (("tab" + i) == object.id) {

				$("#" + object.id).attr("class", "active");

				if (i == 1) {
					$("#QRCodeRegion").show();
					$("#title1").css("color", "#e4393c");
					$("#title2").css("color", "#666666");
				} else if (i == 2) {
					$("#AccountRegion").show();
					$("#title2").css("color", "#e4393c");
					$("#title1").css("color", "#666666");
				}

			} else {

				$("#tab" + i).attr("class", "inactive");

				if (i == 1) {
					$("#QRCodeRegion").hide();
				} else if (i == 2) {
					$("#AccountRegion").hide();
				}

			}
		}

	}

	$(function() {

		$.ajaxSetup({
			cache : false
		});

		//默认微信扫码登录
		$("#AccountRegion").hide();

	});
</script>

</html>







