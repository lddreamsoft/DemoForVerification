<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!-- 引入JSTL -->
<%@include file="/jsp/common/jstl.jsp"%>

<!DOCTYPE html>
<html>
<head>


<%@include file="/jsp/common/css.jsp"%>
<!-- 只有include标签中的/代表项目根路径 -->

<style type="text/css">

@-webkit-keyframes spin {

from { -webkit-transform:rotate(0deg);}
to {-webkit-transform: rotate(360deg);}

}

@-moz-keyframes spin {

from {-moz-transform:rotate(0deg);}
to {-moz-transform: rotate(360deg);}

}

@-ms-keyframes spin {

from { -ms-transform:rotate(0deg);}
to {-ms-transform: rotate(360deg);}

}

#raysHolder {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-top: -25REM; /* 高度的一半 */
  margin-left: -20REM; /* 宽度的一半 */
  z-index: 2;
}

#rays {
  background: url(/hlhhwx/images/common/rays.png) 0 0 no-repeat;
  position: absolute;
  width: 40REM;
  height: 40REM;	
  -webkit-animation: spin 10s linear infinite;
  -moz-animation: spin 10s linear infinite;
  -ms-animation: spin 10s linear infinite;
  -o-transition: rotate(3600deg); 
}

.shake {
	animation: swinging 1s infinite;
	-webkit-animation: swinging 1s infinite;
	-moz-animation: swinging 1s infinite;
	-o-animation: swinging 1s infinite;
	transform: rotate(-20deg);
	-webkit-transform: rotate(-20deg);
	transform-origin: center 80%;
	-webkit-transform-origin: center 80%;
}

/*这里@分行后动画效果失效*/
@keyframes swinging { 
	0% 
	{
	  transform: rotate(-20deg);
	}

	50%
	{
	  transform:rotate(20deg);
	}
}

/*安卓手机*/
@-webkit-keyframes swinging {
	0% 
	{
	  transform:rotate(-20deg);
	}
	
	50%
	{
	  transform:rotate(20deg);
	}
}


/*滚动的范围需要根据获取内容动态调整++*/
@-webkit-keyframes rollEffect {
	from 
	{ 
	  top:10rem;
	}
	
	to 
	{
	  top: -25rem;
	}
}

.roll {
  animation: rollEffect 10s linear infinite;
  -o-animation: rollEffect 10s linear infinite;
  -moz-animation: rollEffect 10s linear infinite;
  -webkit-animation: rollEffect 10s linear infinite;
}

.wrap {
  position: absolute;
  overflow: hidden;
  left: 2.5rem;
  top: 15rem;
  background-color: transparent;
  right: 0;
  bottom: 0;
  display: inline-block;
  width: 30rem;
  height: 10rem;
  z-index: 1;
  border: 0 solid;
  border-radius: 2rem;
}

.wrapRules {
  position: absolute;
  background-color: transparent;
  z-index: 1;
  width:30rem;
  margin-left:-16rem;
  margin-top:-20rem;
  padding:1rem;
}

</style>

</head>

<body>


	<!-- tomcat发布mp3后，会破掉mp3文件使之不能播放，不清楚为什么 -->
	<audio id="audio_Shake" preload="auto" src="/hlhhwx/audio/shake.mp3">
	</audio>

	<audio id="audio_Winning" preload="auto" src="/hlhhwx/audio/winning.mp3">
	</audio>

	<div class="container text-center">
		<div class="row">

			<div class="" style="background-color: #ffdc65; position: relative; background-image: url(/hlhhwx/images/luckyShake/bg_radiation.png); background-size: 100% auto; background-repeat: no-repeat;">
				<img src="/hlhhwx/images/luckyShake/1.png" class="img-responsive"/>
					
				<img onclick="showRules();" src="/hlhhwx/images/common/tips.png" style="position:absolute;z-index:2;width:5rem;height:5rem;right:1rem;top:9rem;" />
					
					
				<div style="text-align: center; color: #ae6113; font-size: 1.2rem; margin-bottom: 1rem;">
					还有&nbsp;<em id="em_times" style="font-style: normal; font-weight: 400; color:red; font-size:2rem;"></em>&nbsp;次抽奖机会
					&nbsp;					
					<span style="right:0rem;">我的积分:</span>
					<em id="em_pointers" style="font-style: normal; font-weight: 400; color:red; font-size:2rem;"></em>
				</div>

				<div class="wrap">
					<div id="div_WinningRecords"></div>
				</div>

				<div style="position: relative;">
					<img src="/hlhhwx/images/luckyShake/4.png" class="img-responsive"
						  style="" /> <img src="/hlhhwx/images/luckyShake/3.png"
						class="shake img-responsive"  
						style="position: absolute; width: 50%; left: 22%; top: 40%;" /> <img
						src="/hlhhwx/images/luckyShake/2.png" class="img-responsive"
						style="position: absolute; top: 90%; left: 23%; width: 50%;" />

				</div>


				<div style="text-align: center; margin-top: 10%; position: relative;">
					<img src="/hlhhwx/images/luckyShake/5.png" class="img-responsive"
						  style="width: 20%; display: inline;" />
										
					<div style="position: absolute; left: 44%; top:2%;font-size: 1rem; color: white;">我的奖品</div>
					<div id="div_MyPrizes" style="position:relative;bottom:0.5rem;border-radius:1.5rem;width:31rem;margin:0 auto;height: 15rem; margin-top:-0.1rem;text-align: center; padding-top:1rem; border: 0.1rem solid #ffbf4c; background-color: #ffefb7;">
					好忧桑，这么多人中奖，偶居然木有奖品~！				
					</div>

				</div>

				<!-- div
					style="text-align: center; margin-top: 0.5rem; position: relative;">
					<img src="/hlhhwx/images/luckyShake/5.png" class="img-responsive"
						  style="width: 20%; display: inline;" />
										
					<div style="position: absolute; left: 44%; top: 2%; font-size: 1rem; color: white;">活动规则</div>
					<div id="div_ActivityRules"
						style="line-height:1.2rem;bottom:0.5rem;position:relative;border-radius:1.5rem;height: 32rem; text-align: left; width:31rem;margin:0 auto;padding: 1.2rem; margin-top:0.3rem; border: 0.1rem solid #ffbf4c; background-color: #ffefb7;">
					</div>
				</div-->

				<div></div>
			</div>

		</div>
	</div>

<%@include file="/jsp/common/js.jsp"%>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script src="/hlhhwx/js/layer/mobile/layer.js"></script>
<script src="/hlhhwx/js/snowfall.js"></script>
<script src="/hlhhwx/js/tools.js"></script>
<!--<script src="/hlhhwx/js/jweixin-1.0.0.js"></script>暂不提供分享功能-->
<script>

var clicked=false;

//弹出活动规则
function showRules()
{
	//防止用户单击多次
	if(!clicked)
	{
		clicked=true;
		//获取活动规则
		$.get("/hlhhwx/activities/queryById/activity/1/0",function(result) {
		  
		  if(result!='')
		  {
			layer.open({
					   type: 1,
					   shade: 'background-color: rgba(0,0,0,.7)',
					   end: function(){clicked=false;},
					   content: 
					     '<div class="wrapRules">'+
						   '<div id="div_ActivityRules" style="color:white;padding:1rem;line-height:1.5rem;border-radius:1.5rem;border: 0.1rem solid #ffbf4c; ">'+
						    result.description+   
						   '</div>'+
						 '</div>'
					   });
		  };	
		});
	}	
}

	var winningWords=new Array("感谢CCTV","探囊取物","坐享其成",
						       "喜从天降","捡到便宜","举世无双",
						       "出神入化","气势磅礴","英姿飒爽",
						       "才高八斗","学富五车","文武双全",
						       "雄才大略","英勇无敌","富甲一方");
	

    //全局控制摇一摇是否生效开关
    var activityStarted=1;
    var layerIndex;
    
	$.ajaxSetup({
		cache : false
	}); //必须加上这个参数，否则同时发送多个ajax请求，会发生结果错乱。
	
	//不丢弃用户任何请求，每次摇奖扣次数，次数用尽提示用户
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		if (options.url.indexOf('/hlhhwx/wx/activities/luckyShake/draw') >= 0) {
			
			if(activityStarted==0)
		    {
				jqXHR.abort();//活动未开始，请求全部拦截
		    }
			
			/*
			drawCount++;
			if (drawCount > 1) {
				jqXHR.abort(); //前端频繁发送请求后丢弃请求，减轻服务器压力
			}*/
		
		}
	});
	
	//摇晃间隔 
	var SHAKE_THRESHOLD = 450, last_update = 0, x = y = z = last_x = last_y = last_z = 0;
	function deviceMotionHandler(eventData) {
		var acceleration = eventData.accelerationIncludingGravity;
		var curTime = new Date().getTime();
		{
			if ((curTime - last_update) > 450) { //多次移动事件中取两个点的事件间隔
				var diffTime = curTime - last_update;
				last_update = curTime;

				x = acceleration.x;
				y = acceleration.y;
				z = acceleration.z;

				//主要优化点1:原来的计算方式把x、y、z三方向的移动有可能会互相干扰。比如x往真方向，y往负方向，就互相抵消了。
				var dist = Math.sqrt((x - last_x) * (x - last_x) + (y - last_y)
						* (y - last_y) + (z - last_y) * (z - last_y)) //该方式存在问题
				var speed = dist / diffTime * 10000;

				//var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;      

				//优化点2:摇动速度测试调整，达到最优
				if (speed > SHAKE_THRESHOLD) { //摇一摇灵敏度

					
					if($("#em_times").text()=='0')
					{
							//次数用尽
							layer.open({
								  content: '木有次数了，微信关注江苏大丰荷兰花海可以赚取积分哦。'
								  ,style: 'background-color:#09C1FF; color:#fff; border:none;'//自定风格
								  ,time: 5
							});
							
							window.removeEventListener('devicemotion', deviceMotionHandler, false);
							return;
					}
				
					if(activityStarted==1)
					{
						if($("#em_times").text()!='0')
						{
							//document.getElementById('audio_Shake').load();
							document.getElementById('audio_Shake').muted = false;
							document.getElementById('audio_Shake').play();
						}
					}	

				$.ajax({ url : "/hlhhwx/wx/activities/luckyShake/draw/1/${openID}",
						type : "GET",
						success : function(result) {

							//alert(result.chance); 0：未中奖，-1次数用尽，-2：丢弃，其它：中奖
							if (result.chance == 0) {
								
								//只要积分足够，提示过程中仍可摇动手机抽奖，只有抽中奖品后弹出模态对话框后用户无法摇动手机
								layer.open({
									  content: '奖品，奖品，快到碗里来！'
									  ,style: 'width:25rem;background-color:#FFA93A;color:white;border:0.1rem solid #ffbf4c;border-radius:2rem;' //自定风格
									  ,time: 2
									});
								
								//扣除次数
								$("#em_times").text($("#em_times").text()-1);
								
								//alert('a');
								//扣除积分
								if($("#em_pointers").text().indexOf('赠送')<0)
								{
									$("#em_pointers").text(parseInt($("#em_pointers").text())-5);
								}	
									
								
								//扣除积分数
								/*
								if(result.webSiteMemberPointers!=-1)
								{
									$("#em_pointers").text(result.webSiteMemberPointers);
									//更新次数
									$("#em_times").text(parseInt(result.webSiteMemberPointers/5));
								}	
								*/
							} 
							else if(result.chance==-1||result.chance==-2)
							{
								
								/*
								layer.open({
									  content: '木有次数了，参与花海微信服务号中的活动可以赚取积分哦。'
									  ,style: 'background-color:#09C1FF; color:#fff; border:none;' //自定风格
									  ,time: 2
									});*/
								
							}
							else if (result.chance != 0){
								
								document.getElementById('audio_Winning').muted = false;
								document.getElementById('audio_Winning').play();
								
								//扣除次数
								$("#em_times").text($("#em_times").text()-1);
								//alert($("#em_times").text()-1);
								
									//扣除积分
								if($("#em_pointers").text().indexOf('赠送')<0)
								{
									$("#em_pointers").text(parseInt($("#em_pointers").text())-5);
								}	
								
								//扣除积分数
								if(result.webSiteMemberPointers!=-1)
								{
									$("#em_pointers").text(result.webSiteMemberPointers);
									//更新次数
									$("#em_times").text(parseInt(result.webSiteMemberPointers/5));
								}
								
								
								//中奖了 停止摇奖
								activityStarted=0;
								
								
								layerIndex=layer.open({
								  type: 1,
								  shadeClose:false,
								  success: function(elem){
									  /*奖品只取第一张图片*/
									  $("#raysLogo").attr("style","background: url(/hlhhwx/upload/images/"+result.pictures.split(',')[0]+") 0 0 no-repeat;"+
									  							  "position: absolute; "+
									  							  "background-size: 100% auto;"+
									  							  "width:20REM;"+
									  							  "height:20REM;"+
									  							  "left:10REM;"+
									  							  "top:10REM;"+
									  							  "z-index:3; ");
									},  
								  shade: 'background-color: rgba(0,0,0,.7)',
								  content: 
									  "<div id='raysHolder'><em style='text-align:center;font-size:20px;color:white;position:absolute;width:20rem;margin-left:10rem;'>"+winningWords[Math.round(Math.random()*1000)%15]+"&nbsp;!</em>"+
								        "<a onclick='layer.closeAll();activityStarted=1;getMyPrizes();getWinningRecords();' id='raysLogo'></a>"+
								    	"<div id='rays'></div>"+
								    	"<a onclick='layer.closeAll();activityStarted=1;getMyPrizes();getWinningRecords();' style='text-align:center;font-size:16px;color:yellow;position:absolute;width:20rem;margin-left:10rem;margin-top:30rem;'>收入囊中</a>"+
								      "</div>"
								  });
								
							}
							
						}
					});

				}
				last_x = x;
				last_y = y;
				last_z = z;
			}
		}
	}
	
	//获取中奖记录
	function getWinningRecords()
	{
		$.get("/hlhhwx/wx/activities/luckyShake/winningRecord/1/0/10",
				function(result) {

			     if(result!='')
			     {
			    	 $("#div_WinningRecords").empty();
			     }
			
					$(result).each(
									function(index, value) {
										//可能没有任何中奖记录
										if(value.openID!=null)
										{
											//对openID进行处理，固定5位字符，取首尾，中间用xxx代替
											var userName = value.openID
													.substring(0, 1)
													+ 'xxx'
													+ value.openID
															.substring(
																	value.openID.length,
																	value.openID.length - 1);

											//alert(value.name);
											$("#div_WinningRecords")
													.append(
															'<p class="roll" style="position:relative;">&nbsp;&nbsp;&nbsp;恭喜<span style="display:inline-block;width:32px;">'
																	+ userName
																	+ "</span>中了：<span style='color:white;display:inline-block;width:12rem;'>"
																	+ (value.prizeMemberPointers==0?value.name:'会员积分')
																	+ "&nbsp;"
																	+ (value.prizeMemberPointers==0?value.description:value.prizeMemberPointers)
																	+ '</span><span style="right:0">'
																	+ new Date(
																			value.createdTime)
																			.Format("MM-dd hh:mm")
																	+ '</span></p>');
										}

										

										//规则只加载一次
										
										/*
										if ($("#div_ActivityRules")
												.text().trim() == '') {
											$("#div_ActivityRules")
													.append(
															value.activityRules);
										}*/

									});
				}); 
	}
	
	//获取我的奖品
	function getMyPrizes()
	{
		//result.limits==0?
		$.get("/hlhhwx/wx/activities/luckyShake/winningRecord/1/${openID}",function(result)
				{
					if(result!='')
					{
						$("#div_MyPrizes").empty();
					}	
					
					$(result).each(function(index, value) {
						
					  $("#div_MyPrizes").append('<p style="position:relative;line-height:1.2rem;">'+"<em style='color:red;display:inline-block;text-align:left;width:15rem;'>"
										+ value.name
										+ "&nbsp;"
										+ value.description
										+ (value.quantity>1?'<em style="color:black">&nbsp;×'+value.quantity+'</em>':'')
										+ '</em>'+(value.receivedDesc=='已兑换'?'<span style="color:#999999;width:5rem;text-align:left;display:inline-block;">已兑换</span>':'<span style="width:5rem;text-align:left;display:inline-block;">'+value.receivedDesc
										+'</span>')
										+ "&nbsp;&nbsp;&nbsp;"
										+ (value.receivedDesc=='已兑换'?'<span style="color:#999999;">'+new Date(value.createdTime).Format("MM-dd hh:mm")+'</span>':new Date(value.createdTime).Format("MM-dd hh:mm"))
										+ '</p>');
					});
				});
	}

	$(function() {
		
		/*暂时不提供分享功能
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: "${WXJSAPIInfo.appID}", // 必填，公众号的唯一标识
		    timestamp: "${WXJSAPIInfo.timeStamp}", // 必填，生成签名的时间戳
		    nonceStr: "${WXJSAPIInfo.nonceStr}", // 必填，生成签名的随机串
		    signature: "${WXJSAPIInfo.signature}",// 必填，签名，见附录1
		    jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		*/
		
		last_update = new Date().getTime();
		if (window.DeviceMotionEvent) {
			window.addEventListener('devicemotion', deviceMotionHandler, false);
		} else {
			activityStarted=0;
			layer.open({
				  content: '您需要更换一个更好的手机才能参加摇一摇活动哦!'
				  ,style: 'background-color:#09C1FF; color:#fff; border:none;' //自定风格
				  ,time: 2
				});
		}
		
		//判断活动是否开始，活动未开始提示用户
		$.get("/hlhhwx/activities/queryById/activity/1/1",function(result){
			
			//alert(result);
			if(result=='')
			{
				//alert('摇一摇活动尚未开始哦，敬请期待！');
				activityStarted=0;
				layer.open({
					  content: '摇一摇活动尚未开始哦，敬请期待！'
					  ,style: 'background-color:#09C1FF; color:#fff; border:none;'//自定风格
					  ,time: 2
					});
			}
		});
		
		
		//1.根据openID找到会员ID,根据会员ID读取会员积分，同时换算成可抽奖次数，如果openID不是网站会员，默认赠送10次抽奖机会，会员积分显示为0
		$.get("/hlhhwx/wx/member/queryMemberPoints/${openID}",function(result)
		{
		  var type=0;
			
		  if(result!='')
		  {
			  //openID存在，判断用户是否关注，如果用户已关注，判断用户是否为网站会员，
			  //if(result.isSubscribed==1)不管是否关注，显示会员积分
			  {
				  if(result.pointers!=-99999) 
				  {
					  //openID存在，且存在会员积分
					  type=1;
				  }
				  else
				  {
					  //openID存在，不存在会员积分，判断是否关注，已关注type 0,未关注type 1
					  if(result.isSubscribed==1)
					  {
						  type=0;
					  }	  
					  else
					  {
						  type=2; //特殊处理
						  $("#em_pointers").html('<span style="font-size:12px;">分享有大礼</span>');
						  
						  //判断用户今天是否已参加抽奖，已抽奖从服务器内存中读取已抽奖次数,未抽奖给默认值10
						  $.get("/hlhhwx/wx/activities/luckyShake/getDrawTimesOfToday/1/${openID}",function(result){
							  $("#em_times").text(result);
						  });
					  }
					 
				  }
				  
			  }
			  
		  }		  
		  else
		  {
			 //openID不存在
			 type=0;
			 
			 //将openID插入微信会员表
			 $.get("/hlhhwx/wx/member/insertWXMember/${openID}",function(result)
			 {
				 //无处理
			 });
			 
		  }
		  
		  if(type==0)
		  {
			  $("#em_pointers").html('<span style="font-size:12px;">微信关注赠送100积分</span>');
			  
			  $.get("/hlhhwx/wx/activities/luckyShake/getDrawTimesOfToday/1/${openID}",function(result){
				  $("#em_times").text(result);
			  });
			  
		  }else if(type==1)
	      {
			  $("#em_pointers").text(result.pointers);
			  $("#em_times").text(parseInt(result.pointers/5));
	      }		  
		  
		});
		
		//2.读取我的奖品 
		getMyPrizes();
		

		//只取最近中奖的10行记录
		getWinningRecords();
		
		//读取奖品图片 性能优化点
		$.get("/hlhhwx/activities/selectAvailablePrizes/1",function(result){
	      if(result!='')
		  {
	    	var pictures=[];
	    	
	    	$(result).each(function(index, value) {
	    	 pictures[index]="/hlhhwx/upload/images/"+value.pictures.split(',')[0];
	      	});
	    	
	    	snowFall.snow($(".container"), {
				image : pictures,
				minSize : 60,
				maxSize : 60,
				flakeCount : 5,
				minSpeed : 0.5,
				maxSpeed : 0.5
			});
		 }
		});
		
		
		/*
		//部分IOS手机音频必须要先播放一次才有声音,安卓手机无此问题
		//document.getElementById('audio_Shake').load();
		//document.getElementById('audio_Shake').muted = true;
		document.getElementById('audio_Shake').play();
		
		//document.getElementById('audio_Winning').load();
		//document.getElementById('audio_Winning').muted = true;
		document.getElementById('audio_Winning').play();
		
		document.addEventListener("WeixinJSBridgeReady", function() {
			//document.getElementById('audio_Shake').load();
			//document.getElementById('audio_Winning').load();
			document.getElementById('audio_Shake').play();
			document.getElementById('audio_Winning').play();
		}, false);*/
		
		

	});
</script>

</body>
</html>







