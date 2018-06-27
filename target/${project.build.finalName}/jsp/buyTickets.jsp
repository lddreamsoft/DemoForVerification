<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<title>圣诞购票</title>

<meta charset="utf-8">
<!-- 引入响应式布局 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<link href="../css/SantaClaus.css" rel="stylesheet">
<meta name="renderer" content="webkit|ie-stand"/>

</head>
<body>

	<div class="" style="z-index: 0;">

		<button id="btn_QRCode" class="btn btn-danger col-sm-12  " type="button">生成二维码</button>

		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>
		<button class="btn btn-danger col-sm-12  " type="button"
			onclick="showTicketsLayer();">弹出购票层</button>


	</div>


	<div id="ticketsLayer" class="container text-center "
		style="z-index: 1; position: absolute; width: 700px; height: 800px; top: 50%; left: 50%; margin: -400px 0px 0px -350px;">

		<div class="row" style="margin-bottom: -1px;">

			<div class="col-xs-12">
				<img src="../images/Christmas/SantaClaus_01.png" class="img-responsive"
					alt="Responsive image" style="display: inline-block;" /> <img
					src="../images/Christmas/SantaClaus_02.png" class="img-responsive"
					alt="Responsive image"
					style="display: inline-block; margin-left: -5px;" /> <img
					src="../images/Christmas/SantaClaus_03.png" class="img-responsive"
					alt="Responsive image"
					style="display: inline-block; margin-left: -5px;" />
				<!-- 关闭按钮 -->
				<img src="../images/Christmas/close.png" class="img-responsive"
					alt="Responsive image"
					style="position: absolute; margin-left: 520px; margin-top: -78px; z-index: 2; cursor: pointer;"
					onclick="closeTicketsLayer();"/>
			</div>

		</div>

		<div class="row">

			<div class="col-xs-12">
				<img src="../images/Christmas/SantaClaus_04.png" class="img-responsive"
					alt="Responsive image" style="display: inline-block;" />
				<div
					style="color: #D9544F; width: 282px; display: inline-block; font-size: 15px; font-weight: bold; background: #FFFCE9; padding-top: 15px; margin-left: -5px; margin-right: -4px; height: 54px; padding-left: 50px;">
					荷兰花海圣诞嘉年华套票</div>

				<img src="../images/Christmas/SantaClaus_06.png" class="img-responsive"
					alt="Responsive image" style="display: inline-block;" />

			</div>

		</div>

		<div class="row">

			<div class="col-xs-12">

				<img src="../images/Christmas/SantaClaus_07.png" class="img-responsive"
					alt="Responsive image" style="display: inline-block;" />

				<div
					style="width: 382.5px; display: inline-block; height: 137px; background: #FFFCE9; margin-left: -4px; margin-right: -4.5px;">

					<form class="form-horizontal" role="form"
						style="background-color: #FFFCE9; padding-bottom: 10px; padding-left: 30px; color: #1E9FFF">

						<div class="form-group" style="margin-bottom: 5px;">
							<label id="lab_adult1" for="" class="col-sm-6 control-label">12月24日成人套餐￥198</label>
							<div class="input-group" style="width: 110px; cursor: pointer;">
								<span class="input-group-addon"
									onclick="changeTicketsCount('adult1','-');">-</span> <input
									type="text" id="adult1" value=0 class="form-control"
									placeholder="0" style="text-align: center;"> <span
									class="input-group-addon"
									onclick="changeTicketsCount('adult1','+');">+</span>
							</div>
						</div>

						<div class="form-group" style="margin-bottom: 5px;">
							<label id="lab_family1" for="" class="col-sm-6 control-label">12月25日家庭套餐￥298</label>
							<div class="input-group" style="width: 110px; cursor: pointer;">
								<span class="input-group-addon"
									onclick="changeTicketsCount('family1','-');">-</span> <input
									type="text" id="family1" value=0 class="form-control"
									placeholder="0" style="text-align: center;"> <span
									class="input-group-addon"
									onclick="changeTicketsCount('family1','+');">+</span>
							</div>
						</div>


						<div class="form-group" style="margin-bottom: 5px;">
							<label id="lab_adult2" for="" class="col-sm-6 control-label">12月24日成人套餐￥198</label>
							<div class="input-group" style="width: 110px; cursor: pointer;">
								<span class="input-group-addon"
									onclick="changeTicketsCount('adult2','-');">-</span> <input
									type="text" id="adult2" value=0 class="form-control"
									placeholder="0" style="text-align: center;"> <span
									class="input-group-addon"
									onclick="changeTicketsCount('adult2','+');">+</span>
							</div>
						</div>

						<div class="form-group" style="margin-bottom: 5px;">
							<label id="lab_family2" for="" class="col-sm-6 control-label">12月25日家庭套餐￥298</label>
							<div class="input-group" style="width: 110px; cursor: pointer;">
								<span class="input-group-addon"
									onclick="changeTicketsCount('family2','-');">-</span> <input
									type="text" id="family2" value=0 class="form-control"
									placeholder="0" style="text-align: center;"> <span
									class="input-group-addon"
									onclick="changeTicketsCount('family2','+');">+</span>
							</div>
						</div>

						<div class="form-group" style="margin-bottom: 5px;">
							<label for="" class="col-sm-3 control-label">手机号码</label>
							<div class="col-sm-7">
								<input type="email" class="form-control" id="inputEmail3"
									placeholder="">
							</div>
						</div>

						<div class="form-group" style="margin-bottom: 10px;">
							<label for="" class="col-sm-3 control-label">短信验证</label>
							<div class="col-sm-4">
								<input type="email" class="form-control" id="inputEmail3"
									placeholder="">
							</div>
							<button class="btn btn-success col-sm-3  " type="button">获取验证码</button>



						</div>

						<div class="form-group" style="">
							<button class="btn btn-danger col-sm-6  col-sm-offset-2"
								type="button"
								onclick="buyTickets('<%=request.getContextPath()%>');"
								style="padding-top: 3px; margin-top: -3px;">立即购票</button>
						</div>
					</form>
				</div>
				<img src="../images/Christmas/SantaClaus_09.png" class="img-responsive"
					alt="Responsive image" style="display: inline-block;" />
				<div style="width: 65px; display: inline-block;"></div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<img src="../images/Christmas/SantaClaus_11.png" class="img-responsive"
					alt="Responsive image"
					style="display: inline-block; padding-bottom: 8px;" />
			</div>
		</div>
		
	</div>




	<script src="../js/jquery-3.1.1.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/snowfall.min.js"></script>

	<script>
	
	
		//页面加载完毕事件
		$(function() {
			$("#ticketsLayer").hide();
			
			//生成二维码
			$("#btn_QRCode").click(function(){
				  $.get("QRCode.jsp",function(data,status){
				    //alert("Data: " + data + "\nStatus: " + status);
				  });
				});
			
		});

		//购票请求
		function buyTickets(baseUrl) {

			var orders;

			//构造json格式的订单信息
			if ($("#adult1").val() != '') {
				orders = "[{'adult1':'" + $("#adult1").val() + "'}";
			}

			if ($("#adult2").val() != '') {
				orders = orders + ",{'adult2':'" + $("#adult2").val() + "'}";
			}

			if ($("#family1").val() != '') {
				orders = orders + ",{'family1':'" + $("#family1").val() + "'}";
			}

			if ($("#family2").val() != '') {
				orders = orders + ",{'family2':'" + $("#family2").val() + "'}]";
			}

			//构造订单
			$.post(baseUrl + "\\Christmas\\alipayapi.jsp", {
				ordersInfo : orders,
				WIDout_trade_no : "TestOrder:" + new Date().getMilliseconds(),
				WIDsubject : encodeURI("2016" + $("#lab_adult1").text()),
				WIDtotal_fee : "0.01",
				WIDbody : "2016年圣诞节门票"
			}, function(data, status) {
				var w = window.open('about:blank');
				w.document.write(data);
				w.document.close();
			});

		}

		function closeTicketsLayer() {

			snowFall.snow($("#ticketsLayer"), "clear");
			$("#ticketsLayer").fadeToggle("5000");

			//显示鼠标点击图片位置
			/*
			e = e || window.event;
			var imgId = '#' + $(e.target).attr('id');
			var currentWidth = $(imgId).width();
			var currentHeight = $(imgId).height();
			var offsetX = e.pageX - $(imgId).offset().left;
			var offsetY = e.pageY - $(imgId).offset().top;
			var x = offsetX / currentWidth;
			var y = offsetY / currentHeight;
			alert(x + ':' + y)
			 */

		}

		function showTicketsLayer() {

			/*
			if ($("#ticketsLayer").attr("class") == "container text-center show") {
				$("#ticketsLayer").attr("class", "container text-center hidden");
			} else {
				$("#ticketsLayer").attr("class", "container text-center show");
			}
			 */

			/*
			layer.open({
				 type: 1,
				  title:false,
				  skin: 'layui-layer-rim', //加上边框
				  area: ['614px', '710px'], //宽高
				  content: $('#ticketsLayer')
				});
			 */
			// $("#ticketsLayer").hide(); //先让div隐藏
			$("#ticketsLayer").fadeToggle("5000");
			snowFall.snow($("#ticketsLayer"), {
				image : "../images/Christmas/flake.png",
				minSize : 10,
				maxSize : 32
			});
		}

		function changeTicketsCount(textbox, operator) {

			/*
			if ($("#" + textbox).val() == '') {
				$("#" + textbox).val(0);
			}
			 */

			if (eval(parseInt($("#" + textbox).val()) + operator + 1) >= 0) {
				$("#" + textbox).val(
						eval(parseInt($("#" + textbox).val()) + operator + 1));
			}
		}
	</script>


</body>
</html>