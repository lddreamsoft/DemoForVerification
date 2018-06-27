<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="renderer" content="webkit|ie-stand">
    
<%@include file="/jsp/common/css.jsp"%>
<!-- 只有include标签中的/代表项目根路径 -->
<link href="../../css/jquery.datetimepicker.css" rel="stylesheet">
<link href="../../js/ueditor/themes/default/css/ueditor.min.css" rel="stylesheet">
<link href="../../css/magnific-popup.css" rel="stylesheet">
<link href="../../css/magnific-popup-general.css" rel="stylesheet">
<link href="../../css/font-awesome.min.css" rel="stylesheet">
<link href="../../js/easyUI/themes/bootstrap/easyui.css" rel="stylesheet">

<!-- link href="../../js/easyUI/easyui-rtl.css" rel="stylesheet">  -->

<style type="text/css">

/*响应式布局代码>800px=50em*/
	/*如果不同分辨率有功能的不同，这里断点设置为绝对像素，否则设置为em，
	媒体查询中em在设置时需要注意的是，
	因为媒体查询的标签优先级高于html的代码，1em不会取16px*0.625=10px,
	而会取浏览器默认的像素值1em=16px;
	那么如果这里断点采用em的形式，800px=800/16=50em，而不是取80em
	*/
@media only screen and (min-width:800px) {
	.popupHeight {		
		height: 750px;
	}
}

/*响应式布局代码>481px<800px*/
@media only screen and (min-width:481px) and (max-width:800px) {
	.popupHeight {
		height: 900px;
	}
}

/*响应式布局代码 当屏幕尺寸小于480px时，第一段样式<=800的样式同样会被继承，但是第二段样式400<=x<=800不会被继承*/
@media only screen and (max-width:480px) {
	.leftRegion {
		display: none;
	}
	.popupHeight {
		height: 900px;
	}
}

.shake {
	animation: swinging 1s infinite;
	-webkit-animation: swinging 1s infinite;
	transform: rotate(-20deg);
	transform-origin: center 80%;
}

.btn1 {
	width: 10rem;
	border: 0.2rem solid;
	border-radius: 2.5rem;
	-moz-border-radius: 2.5rem; /* Old Firefox */
}

/*这里@分行后动画效果失效*/
@keyframes swinging { 0%{
	transform: rotate(-20deg);
}
50%{
transform
:rotate(20deg)
;
}
}
</style>

</head>

<body>


	<div class="container text-left" style="padding: 0.5rem;">
		<div class="row">
			<div class="leftRegion col-xs-0 col-sm-4 col-md-4 col-lg-4"
				style="background-color: #ffdc65; position: relative; background-image: url(../../images/luckyShake/bg_radiation.png); background-size: 100% auto; background-repeat: no-repeat;">

				<img src="../../images/luckyShake/1.png" class="img-responsive"
					alt="活动图片" />

				<div style="position: relative;">
					<img src="../../images/luckyShake/4.png" class="img-responsive"
						alt="活动图片" style="" /> <img src="../../images/luckyShake/3.png"
						class="shake img-responsive" alt="活动图片"
						style="position: absolute; width: 50%; left: 22%; top: 40%;" /> <img
						src="../../images/luckyShake/2.png" class="img-responsive"
						alt="活动图片"
						style="position: absolute; top: 90%; left: 23%; width: 50%;" />

				</div>


				<div
					style="text-align: center; color: #ae6113; font-size: 1.2rem; margin-top: 1rem;">
					您还有&nbsp;<em style="font-style: normal; font-weight: 400;">1</em>&nbsp;次抽奖机会
				</div>

				<div
					style="text-align: center; margin-top: 10%; position: relative;">
					<img src="../../images/luckyShake/5.png" class="img-responsive"
						alt="活动图片" style="width: 20%; display: inline;" />
					<div
						style="position: absolute; left: 40%; top: 2%; font-size: 1rem; color: white;">活动规则</div>

					<div
						style="border: 0.2rem solid #ffbf4c; background-color: #ffefb7; margin-top: -3%; height: 9rem;">


					</div>

				</div>

				<div></div>
			</div>
			<div id="div_Container" class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
				<ul class="nav nav-pills" role="tablist">
					<li role="presentation" class="active" id="tab1"
						onclick="toogleTab(this);"><a href="#">内容设置</a></li>
					<li role="presentation" id="tab2" onclick="toogleTab(this);"><a
						href="#">奖品设置</a></li>
					<li role="presentation" id="tab3" onclick="toogleTab(this);"><a
						href="#">自定义设置</a></li>
				</ul>


				<div id="tabContent1"
					style="border: 1px solid #ccc; padding: 0.8rem 0.8rem 0 0; border-radius: 1.5rem; margin-top: 0.5rem; background: white;">
					<form class="form-horizontal " role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">活动名称<em
								style="color: red">*</em></label>
							<div class="col-sm-10">
								<input id="activityName" type="text" class="form-control"
									placeholder="">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">活动时间<em
								style="color: red">*</em></label>
							<div class="col-sm-10">

								<input id="startTime" type="text">&nbsp;&nbsp;至&nbsp; <input
									id="endTime" type="text">

							</div>
						</div>

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">活动次数<em
								style="color: red">*</em></label>
							<div class="col-sm-10">


								<div class="input-group">
									<span class="input-group-addon">每人可抽奖</span> <input id="times"
										type="text" value=1 class="form-control"
										style="text-align: center;">
									<div class="input-group-btn">

										<button id="btn_activity" type="button"
											class="btn btn-default dropdown-toggle"
											data-toggle="dropdown">
											次/活动全程 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu dropdown-menu-right" role="menu">
											<li><a href="#" id="type1"
												onclick="changeActivity(this);">次/活动全程</a></li>
											<li><a href="#" id="type2"
												onclick="changeActivity(this);">次/每日</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">活动标签&nbsp;</label>
							<div class="col-sm-10">
								<input id="tag" type="text" class="form-control" placeholder="">
							</div>
						</div>

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">活动说明&nbsp;</label>
							<div class="col-sm-10">
								<script id="editor" type="text/plain"
									style="width: 100%; height: 30rem;"></script>
							</div>
						</div>

						<div style="text-align: center;">
							<button class="btn btn-danger" type="button"
								onclick="saveActivity();" style="width: 15rem;">保存</button>
						</div>

					</form>


				</div>

				<div id="tabContent2" style="height:52rem;border: 1px solid #ccc; padding: 1.5rem; border-radius: 1.5rem; margin-top: 0.5rem; background: white;">
			
			       <table id="table_prizes" class="easyui-datagrid" style="max-height:100%;"> 
			       
			       </table>
				
				</div>
			      
	</div>


	<!-- 弹出层内容 -->
	<div id="div_Prizes" style="height:660px;" class="white-popup mfp-hide">

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">序号<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0;">
				<input id="orderNumber" type="text" style="margin-bottom: 0.5rem;"
					class="form-control" id="" placeholder="功能未定，随便填写">
			</div>
		</div>


		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">奖项<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0;">
				<input id="name" type="text" style="margin-bottom: 0.5rem;"
					class="form-control" id="" placeholder="例：一等奖、二等奖、三等奖">
			</div>
		</div>

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">图片<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="border: 0.1rem solid #cbcbcb; padding: 0;">

				<!--  ./index.html ../common/imageCutter.jsp scrolling="no"-->
				<iframe id="frame_Pictures" frameborder="no" scrolling="auto"
					src="../common/uploadCropImage.jsp"
					style="width: 100%; height: 20rem;"> </iframe>


			</div>
		</div>

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label" style="margin-top:1rem;">名称<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0; margin-top:1rem;">
				<input id="description" type="text" style="margin-bottom: 0.5rem;"
					class="form-control" id="" placeholder="奖品名称">
			</div>
		</div>

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">赠送积分<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0;">
				<input value=0 id="limits" type="text" style="margin-bottom: 0.5rem;" 
					class="form-control" id="" placeholder="赠送会员积分">
			</div>
		</div>

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">库存限制<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0;">
				<input id="stock" type="text" style="margin-bottom: 0.5rem;"
					class="form-control" id="" placeholder="总共有多少个奖品可抽奖">
			</div>
		</div>

		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-2 col-md-2 col-lg-2 control-label">中奖概率<em style="color:red">*</em></label>
			<div class="col-xs-12 col-sm-10 col-md-10 col-lg-10"
				style="padding: 0;">
				
					
					<div class="input-group" style="margin-bottom: 0.5rem;">
  <input id="chance" type="text" class="form-control" placeholder="例：输入5表示中奖概率为5%">
  <span class="input-group-addon">%</span>
</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-5 col-md-5 col-lg-5 control-label">未关注微信公众号用户特殊概率</label>
			<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7" style="padding: 0;">
				
					
					<div class="input-group" style="margin-bottom: 0.5rem;">
  <input id="chance1" type="text" class="form-control" placeholder="例：输入5表示在原有中奖概率的基础上再加5%">
  <span class="input-group-addon">%</span>
</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for=""
				class="col-xs-12 col-sm-5 col-md-5 col-lg-5 control-label">已关注微信公众号用户特殊概率</label>
			<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7" style="padding: 0;">
				
					
					<div class="input-group" style="margin-bottom: 0.5rem;">
  <input id="chance2" type="text" class="form-control" placeholder="例：输入5表示在原有中奖概率的基础上再加5%">
  <span class="input-group-addon">%</span>
</div>
			</div>
		</div>


		<div class="form-group">
			<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
				<button id="save" onclick="savePrize(this);" type="submit"
					class="btn btn-danger" style="width: 15rem; margin-top: 0.5rem;">保存</button>
			</div>
		</div>

	</div>
	
	</div>
	</div>

</body>

<%@include file="/jsp/common/js.jsp"%>

<script src="../../js/dateTimePicker/jquery.datetimepicker.full.min.js"></script>
<script src="../../js/ueditor/ueditor.config.js"></script>
<script src="../../js/ueditor/ueditor.all.min.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script src="../../js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="../../js/layer/layer.js"></script>
<script src="../../js/jquery.magnific-popup.min.js"></script>
<script src="../../js/tools.js"></script>

<script src="../../js/easyUI/jquery.easyui.min.js"></script>
<script src="../../js/easyUI/locale/easyui-lang-zh_CN.js"></script>
<!--script src="../../js/easyUI/easyui-rtl.js"></script> -->

<script>
	

//该组参数被iframe访问
var showSelectFilesRegion='Y';
var picturesUrl='';
var prizeID='';
var activityID='';
var ue;

//保存活动
function saveActivity()
{
  //debugger;
  if(activityID=='')
  {
	//新增
    insertActivity();
  }
  else
  {
	//保存
	updateActivity();
	
  }
}

//
function updateActivity()
{
  var data;
	  
  //debugger;
  var actualType=0; 
  if($("#btn_activity").html().indexOf("次/活动全程")>=0)
  {
	actualType=1;
  }
  else if($("#btn_activity").html().indexOf("次/每日")>=0)
  {
    actualType=2;
  }
	  
	//直接构造JSON对象
	  data={id:1,name:$("#activityName").val().trim(),startTime:$("#startTime").val().trim(),endTime:$("#endTime").val().trim(),type:actualType,times:$("#times").val().trim(),tag:$("#tag").val().trim(),description:ue.getContent()};
	  
	  debugger;
	  
      //摇一摇活动ID固定为1
	  $.post("/hlhh/activities/updateActivity",data,function(result){	
		
	   if(result==1)
	   {
	     layer.msg('保存成功^-^',
	 	 {
	 	  icon:6,
	 	  time:1000
	     });	  
	 	}
	 	else
	 	{
	 	  //debugger;
	 	  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
	 	  {
	 	    icon:5,
	 		time:2000
	 	  });	    		
	 	}
	  });	  
	
}

function insertActivity()
{
  var data;
  
  var actualType=0;
  if($("#btn_activity").html().indexOf("次/活动全程")>=0)
  {
    actualType=1;
  }
  else if($("#btn_activity").html().indexOf("次/每日")>=0)
  {
    actualType=2;
  }
  
  //debugger;
  
  /*
  data='{"name":"'+$("#activityName").val().trim()+'","startTime":"'+$("#startTime").val().trim()+'","endTime":"'+$("#endTime").val().trim()
  +'","type":"'+type+'",times":"'+$("#times").val().trim()+'","tag":"'+$("#tag").val().trim()+'","description":"'+'<p>111<br/></p>'+'"}';
  */
  
  //直接构造JSON对象
  data={name:$("#activityName").val().trim(),startTime:$("#startTime").val().trim(),endTime:$("#endTime").val().trim(),type:actualType,times:$("#times").val().trim(),tag:$("#tag").val().trim(),description:ue.getContent()};
  
  $.post("/hlhh/activities/insertActivity",data,function(result){	
	
   if(result==1)
   {
     layer.msg('保存成功^-^',
 	 {
 	  icon:6,
 	  time:1000
     });	  
 	}
 	else
 	{
 	  //debugger;
 	  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
 	  {
 	    icon:5,
 		time:2000
 	  });	    		
 	}
  });

}

//查询摇一摇活动
function queryActivityById()
{
	//debugger;
	 //debugger;摇一摇活动id固定为：1
	  $.get("/hlhh/activities/queryById/activity/1",function(result){	
		
		if(result=="")
		{
		  activityID='';
		  $("#activityName").val('');
		  $("#startTime").val('');
		  $("#endTime").val('');
		  $("#times").val('');
		  $("#tag").val('');		 
		  
		  ue.ready(function() {
			  ue.setContent('');
		  });

		  $("#type1").click();
		}
		else
		{
		  activityID=1;  
		  
		  //debugger;
		  
		  $("#activityName").val(result.name);
	      $("#startTime").val(new Date(result.startTime).Format("yyyy-MM-dd hh:mm"));
		  $("#endTime").val(new Date(result.endTime).Format("yyyy-MM-dd hh:mm"));
		  $("#times").val(result.times);
		  $("#tag").val(result.tag);
			
		  if(result.type==1)
		  {
		    $("#type1").click();
		  }
		  else if(result.type==2)
		  {
			$("#type2").click();
		  }
		  
 		  ue.ready(function() {
  		    ue.setContent(result.description);
		  });
 		  
 		  
 
		}
	  });	  
}

function updatePrize(onlyUpdatePictures)
{
	//debugger;
	
	//数据校验在后台进行
	var data,url;
	
    //获取iframe中的pictures
    var tempPictures='luckyShake/'; //这里IE9有兼容性问题
    
    //debugger;
    
    //跨浏览器兼容性问题 不允许使用frames[0]这种形式
    $(document.getElementById('frame_Pictures').contentWindow.document.body).find("img[id^='img_thumbnail']").each(function(i,object){
    	tempPictures+=object.src.split('/')[7]+"/"+object.src.split('/')[8].replace("thumbnail.","")+","
    });
    
    if(tempPictures=='luckyShake/')
    {
    	//未找到任何图片
    	 //debugger;
  	  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
  	  {
  	    icon:5,
  		time:2000
  	  });	
  	  
  	  return;
    }	
    
    //debugger; //如果用户上传了文件，但是并没有保存而是直接裁剪图片，返回后prizeID为空，导致报错
    
    if(prizeID=='') return; //当prizeID为空时，直接返回。
    
    if(onlyUpdatePictures=='N')
    {
      url="/hlhh/activities/updatePrize";
      data={
            id:prizeID,
        	pictures:tempPictures,
        	orderNumber:$("#orderNumber").val().trim(),
        	name:$("#name").val().trim(),
        	description:$("#description").val().trim(),
        	limits:$("#limits").val().trim(),
        	stock:$("#stock").val().trim(),
        	chance:$("#chance").val().trim(),
        	chance1:$("#chance1").val().trim(),
        	chance2:$("#chance2").val().trim()
          }
    }
    else if(onlyUpdatePictures=='Y')
    {
      url="/hlhh/activities/updateOnlyPicturesOfPrize";
      data={id:prizeID,pictures:tempPictures};
    }
    
   
    //data=JSON.parse(data);
    //debugger;
    //$.magnificPopup.close(); 
    
    $.post(url,data,function(result){
	     
    	if(result==1)
    	{   		
    		
    	  //刷新图片url
      	  queryPrizeByActivityID(1);
    		 
    	  if(onlyUpdatePictures=="N")
    	  {
    		  layer.msg('保存成功^-^',
    		    	  {
    		    	    icon:6,
    		    		time:1000
    		    	  });	  
    	    $.magnificPopup.close(); 
    	  }
    	}
    	else
    	{
    	  //debugger;
    	  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
    	  {
    	    icon:5,
    		time:2000
    	  });	    		
    	}
    	
	});	 
    
    //debugger;
}

function addPrize()
{
  showSelectFilesRegion="Y";
  $("#orderNumber").val("");
  $("#name").val("");
  $("#description").val("");
  $("#limits").val("0");
  $("#stock").val("");
  $("#chance").val("");
  $("#chance1").val("");
  $("#chance2").val("");
	
  $.magnificPopup.open({
		mainClass : 'mfp-fade',
		removalDelay : 300,
		tClose : '关闭 (Esc)',
		  items: {
		    src: '#div_Prizes'
		  },
		  type: 'inline',callbacks: {
			  close: function() {
				  //queryAll();    
		    }		  
		  }
		});
};



function savePrize(object)
{ 
	debugger;
	
	if(showSelectFilesRegion=='N')
	{
		updatePrize('N');
	}
	else
	{
		insertPrize(object);
	}
}


function editPrize(lprizeID)
{
	prizeID=lprizeID;
	//debugger;

	//界面中的值不方便获取，从数据库获取相关字段的值	
	$.get("/hlhh/activities/queryPrizeByPrizeID/"+prizeID,function(result){
	  
		//debugger;
	  if(result!="")
      {   		
    	 //result.id;
    	 $("#orderNumber").val(result.orderNumber);
    	 $("#name").val(result.name);
    	 picturesUrl=result.pictures.split('/')[1]+"/"+result.pictures.split('/')[2];
    	 $("#description").val(result.description);
    	 $("#limits").val(result.limits);
    	 $("#stock").val(result.stock);
    	 $("#chance").val(result.chance);
    	 $("#chance1").val(result.chance1);
    	 $("#chance2").val(result.chance2);
    	 
    	//debugger;
    		if(picturesUrl=="")
    		{
    		  showSelectFilesRegion="Y";	
    		}
    		else
    		{
    		  showSelectFilesRegion="N";
    		}
    		
    		$.magnificPopup.open({
    		  mainClass : 'mfp-fade',
    		  removalDelay : 300,
    		  tClose : '关闭 (Esc)',
    		  items: {
    				    src: '#div_Prizes'
    		         },
    		  type: 'inline',callbacks: {
    		  close: function() {
    			  //queryAll();    
    			     }		  
    		  	   }
    		});
      }
	});
	
	/*
	//弹出层赋值
	$("#orderNumber").val($(object).parent().parent().parent().children().get(0).innerText);
	$("#name").val($(object).parent().parent().parent().children().get(1).innerText);
	
	var temp=$(object).parent().parent().parent().children().children().children().get(0).src.split('/');
	picturesUrl=temp[7]+"/"+temp[8];
	
	//debugger;
	$("#description").val($(object).parent().parent().parent().children().get(3).innerText);
	$("#limits").val($(object).parent().parent().parent().children().get(4).innerText);
	$("#stock").val($(object).parent().parent().parent().children().get(5).innerText);
	$("#chance").val($(object).parent().parent().parent().children().get(6).innerText);
	$("#chance1").val($(object).parent().parent().parent().children().get(7).innerText);
	$("#chance2").val($(object).parent().parent().parent().children().get(8).innerText);
	*/
	
	
}

function deletePrize(object)
{
	layer.confirm('数据删除后无法恢复，确认是否删除？', {
		  btn: ['是','否'], shade: false,title:false,icon:0
		}, function(){
			
			//仅仅删除活动和奖品之间的关系，并不删除奖品
			$.get("/hlhh/activities/deleteActivityPrize/1/"+object.id,function(result){	
				
			  if(result==1)
			  {
			    layer.msg('删除成功^-^',
			    {
				  icon:6,
				  time:1000
			    });	  
			    queryPrizeByActivityID(1);
			  }
			  else
			  {
				layer.msg('删除失败-_-，可能的原因：该记录已被其它人删除。',
				{
				  icon:5,
				  time:1000
			    });
			  }
				
			});
		 
		}, function(){
	});
}
 
 
 function genExcelFile(obj)
 {
	 var originalSrc=obj.src; //excel文件可能会等待较长时间下载
	 obj.src="../../images/common/downloadExcelFile.gif";
	 
	  //$.easyui.loading({ msg: "正在加载..." });
	 
	 //获取datagrid列标题
	 var opts = $('#table_prizes').datagrid('getColumnFields'); 
	 var columnTitles='';
	 for(i=0;i<opts.length;i++)
	 {
	   var col = $('#table_prizes').datagrid("getColumnOption",opts[i]);
	   columnTitles+=col.title+",";
	 }
	 columnTitles=columnTitles.substring(0,columnTitles.length-1);
	 
	 window.location.href="/hlhh/activities/generate/excelFile?fileName=摇一摇活动奖品&sheetName=摇一摇活动奖品&title="+columnTitles;
	
	 obj.src=originalSrc;
	 
	 //这里不能发送ajax请求，否则无法弹出下载Excel对话框
	 //传入参数格式：{"fileName":xxx,"sheetName":xxx,"title":"字段名称1,字段名称2,...", }
	 /*
	 $.get("/hlhh/activities/generate/excelFile",{"fileName":"摇一摇活动奖品","sheetName":"摇一摇活动奖品","title":columnTitles},
			 function(result){	
		 
	 });
	 */
	 
 }
 
  //查询活动下的奖品
  function queryPrizeByActivityID(activityID)
  {
	   $('#table_prizes').datagrid({ 
           url: '/hlhh/activities/queryById/activityPrize/'+activityID+'/1',
           method:'get',
           //fit:false,
           singleSelect:true,
           striped: true, 
           nowrap:false,//文字超过列宽时换行
           //rownumbers:true,
           idField:'id',
           pagination:true,
           
           onDblClickRow: function (rowIndex, rowData) {  
        	   editPrize(rowData.id);
           },
           
           frozenColumns:[[ /*必须设置宽度，否则浏览器第一次加载界面后无法看到edit按钮*/
       		{field:'id',width:80,title:'<img style="height:2rem;cursor:pointer;" onclick="genExcelFile(this);" src="../../images/common/excel.png"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="height:2rem;cursor:pointer;" onclick="addPrize();" src="../../images/common/addItem.png"/>',align:'center',
       			formatter:function(value,row)
	              {
		           	var str = "";
		           	if(value!=""||value!=null)
		           	{
		           	  str = '<img onclick="deletePrize(this);" id="'+value +'" style="height:1.5rem;cursor:pointer;" src="../../images/common/delete.png"/>'//+
						  //'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img onclick="editPrize(this);" id="'+value +'" style="height:2rem;cursor:pointer;" src="../../images/common/edit.png"/>';
		              return str;
		          	}
		          }	 
       		}
       		]],
           columns:[[ 
		             {field:'orderNumber',title:'序号'},   
		             {field:'name',title:'奖项',width:100},   
		             {field:'pictures',title:'图片',width:50,
  	            	  formatter:function(value,row)
  	            	  {
		             	var str = "";
		            	if(value!=""||value!=null)
		            	{
		            	 /*因左侧存在操作栏的问题，图片不能设置：max-height:100%;*/	
		            	  str = "<img style='max-width:100%;' src='../../upload/images/"+value.split(",")[0].split('/')[0]+"/"+value.split(",")[0].split('/')[1]+"/thumbnail."+value.split(",")[0].split('/')[2]+"'/>";
		                  return str;
		            	}
		              }
		             },
		             {field:'description',title:'名称',width:100},
		             {field:'limits',title:'赠送会员积分'},
		             {field:'stock',title:'库存'},
		             {field:'chance',title:'中奖概率'},
		             {field:'chance1',title:'未关注中奖概率'},
		             {field:'chance2',title:'已关注中奖概率'}
		       	   ]]
       });
	
	   
	   $('#table_prizes').datagrid('getPager').pagination({
		   pageSize:10,
		   pageList:[10, 20, 30],
           layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'] 
       }); //links改为manual即可切换为手工翻页方式
	  
	   //$('#table_prizes').datagrid('reload'); 重新加载数据
  }
 
  //保存奖品
  function insertPrize()
  {
    //数据校验在后台进行
	var data;
	
    //获取iframe中的pictures
    var tempPictures=''; //这里IE9有兼容性问题
    
    //debugger; //window.frame兼容性的重灾区
    
    //跨浏览器兼容性问题 不允许使用frames[0]这种形式
    $(document.getElementById('frame_Pictures').contentWindow.document.body).find("img[id^='img_thumbnail']").each(function(i,object){
    	tempPictures+=object.src.split('/')[6]+"/"+object.src.split('/')[7]+"/"+object.src.split('/')[8].replace("thumbnail.","")+","
    });
    
    data={
           pictures:tempPictures,
    	   orderNumber:$("#orderNumber").val().trim(),
    	   name:$("#name").val().trim(),
    	   description:$("#description").val().trim(),
    	   limits:$("#limits").val().trim(),
    	   stock:$("#stock").val().trim(),
    	   chance:$("#chance").val().trim(),
    	   chance1:$("#chance1").val().trim(),
    	   chance2:$("#chance2").val().trim()
    	 };
    
    //debugger;
    //保存活动奖品关系
    $.post("/hlhh/activities/insertPrize",data,function(result){
	     
   
   	if(result!=0) //result为数据插入后形成的自增长ID
    {   		
      $.get("/hlhh/activities/insertActivityPrize/1/"+result,function(result){
    	  
    	  if(result==1)
    	  {
    		  layer.msg('保存成功^-^',
    			    	{
    			    	 icon:6,
    			    	 time:1000
    			    	});	  

    			        $.magnificPopup.close(); 
    			    	queryPrizeByActivityID(1);
    			    	//return;
    	  }
    	  else
    		  {
    		  //debugger;
    		  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
    		      	  {
    		      	    icon:5,
    		      		time:2000
    		      	  });	    	
    		  }
        
    			 
      });  		 
    	
    	}
    	else
    	{
    		debugger;
    		  layer.msg('暂未提供数据校验功能，请按照正确格式并录入完整数据-_-',
    		      	  {
    		      	    icon:5,
    		      		time:2000
    		      	  });	    	
    	}
    	
	});	 
    
  
  }

	$.datetimepicker.setLocale('ch');
	$("#startTime").datetimepicker({
		lang : "ch",
		format : "Y-m-d H:i",
		timepicker : true
	})

	$("#endTime").datetimepicker({
		lang : "ch",
		format : "Y-m-d H:i",
		timepicker : true
	})


	/*layer不具备响应式特性，移除
	 $('#addPrizes').on('click', function(){	
	

	 //1.弹出奖品编辑层
	 layer.open({
	 type: 1,
	 title:"奖品设置",
	 skin: 'layui-layer-lan', //样式类名
	 area: ['80rem', '70rem'],
	 shadeClose: true, //点击遮罩关闭
	 content: '\<\div style="padding:2rem;">'+$("#form_Prizes").html()+'<\/div>'
	 });   
	
	 });	
	 */

	function toogleTab(object) {
		for (var i = 1; i <= 3; i++) {
			if (("tab" + i) == object.id) {
				$("#" + object.id).attr("class", "active");
				//更换tab内容
				$("#tabContent" + i).show();
				
				//如果是奖品设置，从数据库抓取奖品数据
				if(i==1)
				{
				  
					queryActivityById();
				}else if(i==2)
				{
				  queryPrizeByActivityID(1);	
				  
				}	
				
			

			} else {
				$("#tab" + i).attr("class", "inactive");
				$("#tabContent" + i).hide();
				
				
			}
		}

	}

	function changeActivity(object) {
		$("#btn_activity").html(object.innerHTML + "<span class='caret'></span>");
	}




	//页面加载完毕事件
	$(function() {
		
		//全局禁用缓存，防止IE9缓存ajax数据
		$.ajaxSetup({cache:false});

		//隐藏奖品编辑器 IE8下会有问题
		$("#tabContent2").hide();
		$("#form_Prizes").hide();
		//$("#form_Prizes").css("display","none");

		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		ue = UE.getEditor('editor', {
			toolbars : [ [ 'fullscreen', 'source', '|', 'undo', 'redo', '|',
					'bold', 'italic', 'underline', 'fontborder',
					'strikethrough', 'superscript', 'subscript',
					'removeformat', 'formatmatch', 'autotypeset', 'blockquote',
					'pasteplain', '|', 'forecolor', 'backcolor',
					'insertorderedlist', 'insertunorderedlist', 'selectall',
					'cleardoc', '|', 'rowspacingtop', 'rowspacingbottom',
					'lineheight', '|', 'customstyle', 'paragraph',
					'fontfamily', 'fontsize', '|', 'directionalityltr',
					'directionalityrtl', 'indent', '|', 'justifyleft',
					'justifycenter', 'justifyright', 'justifyjustify', '|',
					'touppercase', 'tolowercase', '|', 'link', 'unlink',
					'anchor', '|', 'inserttable', 'deletetable',
					'insertparagraphbeforetable', 'insertrow', 'deleterow',
					'insertcol', 'deletecol', 'mergecells', 'mergeright',
					'mergedown', 'splittocells', 'splittorows', 'splittocols'

			] ],elementPathEnabled:false
		});
		
		queryActivityById();
	});
</script>

</html>







