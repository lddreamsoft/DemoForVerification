<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>

<title>
<!--因IE9存在兼容性问题，文件一律上传后提供预览-->

</title>

<%@include file="/jsp/common/css.jsp"%>
<link href="../../css/cropper.min.css" rel="stylesheet">


<style type="text/css">

/*响应式布局代码 当屏幕尺寸小于480px时，第一段样式<=800的样式同样会被继承，但是第二段样式400<=x<=800不会被继承*/
@media only screen and (max-width:480px) {

	.selectFilesRegion {
		display:none;
	}
}


body {
     overflow-x : hidden;   /*去掉横向滚动条*/  
     padding-top:30px;
}

a.reset
{ 
  display:inline-block;
  width:1.6rem;
  height:20px;
  background: url(../../images/common/bch.png) -116px 0px no-repeat;
  cursor:pointer;  
  
}

a.reset span{
 display:none;
}

a.reset:hover{
 background: url(../../images/common/bch.png) -116px -23px no-repeat;
}


a.rotate1:hover{
  background: url(../../images/common/bch.png) -0px -27px no-repeat;  
}

a.rotate1
{
  display:inline-block;
  width:1.5rem;
  height:20px;
  background: url(../../images/common/bch.png) 0px 0px no-repeat;
  cursor:pointer;
}

a.rotate1 span{
  display:none;
}

a.rotate2 span{
  display:none;
}

a.rotate2:hover{
  background: url(../../images/common/bch.png) -91px -2.7px no-repeat;
}

a.rotate2
{
  display:inline-block;
  background-color:red;
  width:1.5rem;
  height:20px;
  background: url(../../images/common/bch.png) -91px 0px no-repeat;
  cursor:pointer;
}


a.save
{
  display:inline-block;
  background-color:red;
  width:2.5rem;
  height:20px;
  background: url(../../images/common/bch.png) -138px 0px no-repeat;
  cursor:pointer;
}

a.save:hover{
  background: url(../../images/common/bch.png) -138px -27px no-repeat;
}


</style>

</head>

<body style="display: table-cell;vertical-align: middle;width:50rem;height: 350px;text-align: center;background-color:#fff;">


<!-- 图片选择区域 -->
<div id="selectFiles" class="selectFilesRegion">

<img id="img_blankImage" src="../../images/common/blankImage.png" style="position: absolute;top: 11rem;left: 20rem;">	
<!-- 兼容IE9 input设置透明 -->
<div style="border-radius:0.5rem;height:41px;background:#0086ff;width:15rem;position:relative;top: 6rem;margin-left:16rem;">
<div style="text-align:center;padding-top:12px;font-size:15px;color:white;font-weight:0;cursor:pointer;">点击上传PNG</div>
<!-- font-size:30px;必须加上，否则IE9下点击无效果。 multiple -->
<input id="fileupload" type="file" accept="image/gif,image/jpeg,image/tif,image/jpg,image/bmp,image/png" name="files[]"  style="width:14.4rem;height:41px;cursor:pointer;outline:medium none;position:absolute;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;left:0rem;top:0rem;">
</div>
</div>

<div id="basic" style="text-align:center;">

<!-- 兼容IE9 input设置透明 -->
<div style="border-radius:0.5rem;height:25px;width:2.7rem;position:absolute;top:0rem;right:1rem;cursor:pointer;">
<img src="../../images/common/add.png" style="position:fixed;top:0rem;right:0rem;width:3.5rem;height:35px;">
<input id="fileupload1" type="file" accept="image/gif,image/jpeg,image/tif,image/jpg,image/bmp,image/png" name="files[]" style="position:fixed;top:0rem;right:0rem;font-size:0rem;width:3.5rem;height:35px;cursor:pointer;outline:medium none;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;">
</div>

<!-- 预览图片区域 -->
<div id="uploadedFiles" style="">


</div>   


<div id="progressArea" style="padding-left:2rem;padding-right:2rem;padding-bottom:0rem;position:fixed;width:100%;bottom:-2rem;">    
  <div class="progress">
    <div id="progress" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
  </div>
</div>

</div>

</div>


<div id="advance" style="text-align:center;padding-left:0.5rem;padding-right:0.5rem;">

	<img onclick="back();" src="../../images/common/back.png" style="z-index:2;position:absolute;top:0rem;right:0rem;width:3rem;height:30px;cursor:pointer;">	

	<div style="height:300px;max-width:42rem;display:inline-block;">
	  <img id="img_Prizes" style="max-width:100%;max-height:100%;">	
	</div>
	
	<div id="preview1" style="width:15rem;height:150px;position:absolute;top:0rem;left:0rem;overflow:hidden;border:0rem solid #CBCBCB;">
	  <img/> 
	</div>
	
	<div class="text-center">
	  <a onmouseover="showTips(this);" class="rotate1" onclick="rotateToRight();"><!--span>向右旋转</span--></a>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onmouseover="showTips(this);" class="reset" onclick="reset();"><!--span>复原</span--></a>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onmouseover="showTips(this);" class="rotate2" onclick="rotateToLeft();"><!--span>向左旋转</span--></a>	  
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onmouseover="showTips(this);" class="save" onclick="saveCroppedFile();"></a>
	</div>
	

</div>

</body>




<%@include file="/jsp/common/js.jsp"%>

<script src="../../js/cropper/cropper.min.js"></script>
<script src="../../js/jquery.fileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="../../js/jquery.fileupload/js/jquery.iframe-transport.js"></script>
<script src="../../js/jquery.fileupload/js/jquery.fileupload.js"></script>
<script src="../../js/layer/layer.js"></script>


<!-- 存在IE9兼容问题 script src="../../js/localResize/lrz.bundle.js"></script> -->

<script>



   function back()
   {
	 $("#basic").toggle();
	 $("#advance").toggle();   
   }

   //定义全局变量裁剪区域
   cropRegion="";
   
   function showTips(object)
   {
     var tips="";
     if($(object).attr("class")=="save")
     {
       tips="上传裁剪后的图像";
     }
     else if($(object).attr("class")=="reset")
     {
       tips="图像复原";    	 
     }
     else if($(object).attr("class")=="rotate1")
     {
       tips="向右旋转";    	 
     }     
     else if($(object).attr("class")=="rotate2")
     {
       tips="向左旋转";    	 
     }
     
     layer.tips(tips, $(object), {
	   tips: [1, '#3595CC'],
	   time: 1500
	 });	   
   }
   
   
   
   //保存裁剪后的文件到服务器端
   function saveCroppedFile()
   {	 
	   
	 //同样也要附加进度条
	 var index = layer.load(1, {
  	 shade: [0.1,'#fff'] //0.1透明度的白色背景
     });
	 
	
     var fileName=$("#img_Prizes").attr("src").split("/")[6].replace("thumbnail.","");				 
     //debugger;
     
     
     //console.log("cropRegion:"+cropRegion);
     //debugger;		 
	 $.get("/hlhh/jquery/crop/"+fileName+"/"+cropRegion+"?type=luckyShake", function(result){		 
       
     layer.close(index);
		 
	 if(result!="Failed")
	 {
	   //裁剪完之后更改图片URL	,如果存在数据库记录，同时更改数据库中的URL
	
	   
	   //去掉http头
	   var src=document.getElementById('img_Prizes').src.split('/');
	   document.getElementById('img_Prizes').src="/"+src[3]+"/"+src[4]+"/"+src[5]+"/"+src[6]+"/"+src[7]+"/"+result;
	   //debugger;
	   	   
	   initCropper();	   
	  
	   //debugger;
	   $("#"+croppedDivID).remove();
	   //$("#"+croppedDivID).attr("src","/"+src[3]+"/"+src[4]+"/"+src[5]+"/"+src[6]+"/thumbnail."+result);
	   
	   var imageID='img_thumbnail'+Math.round(Math.random()*10000000000);
		   
	   
	   //debugger;
	   //console.log("1号:"+'/hlhh/upload/images/luckyShake/'+src[7]+"/thumbnail."+result);
	   //croppedDisplayName=result;
	   //alert(result);
	   
	   //显示图片缩略图 双击的功能改为更改图片，而不是删除图片
       $("#uploadedFiles").prepend(
	       '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'" style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
		     '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
		     '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
		     '<img ondblclick=document.getElementById("fileupload").click();globalImageID="'+imageID+'" id="'+imageID+'" style="cursor:pointer;max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+src[7]+"/thumbnail."+result+'><p/>'+croppedDisplayName+
		   '</div>'
		);
	   
	   //debugger;
	   
       layer.msg('上传成功^-^',
	   {
	     icon:6,
		 time:2000
	   });	  
       
       //自动返回  
       back();
       
       //debugger;
	   window.parent.updatePrize('Y');
	   
	   window.parent.picturesUrl=src[7]+"/"+result; //图片发生变化，更新父节点图片名称，否则会报找不到图片错误。
	   //debugger;
	 }
     else if(result=="Failed")
	 {	  
       //成功提示消失，失败则提示停留	 
       layer.msg('上传失败-_-',
       {
         icon:5//,
    	 //time:2000
       });	
	 }	   
	 });	   
   }
   
   function initCropper()
   {
	   $('#img_Prizes').cropper("destroy");
	   $('#img_Prizes').cropper({			
		    //viewMode:3,
		    modal:false,
			background:false,
			autoCropArea:1,
			preview:[$('#preview1')],
			//aspectRatio:4/3,				
			crop: function(e) {
			    // Output the result data for cropping image.			    
			    cropRegion=e.x+","+e.y+","+e.width+","+e.height+","+e.rotate;
			    //alert(cropRegion);
			    //console.log(cropRegion+"e.rotate:"+e.rotate+"e.scaleX:"+e.scaleX);
			    /*
			    console.log("e.x:"+e.x);
			    console.log("e.y:"+e.y);
			    console.log("e.width:"+e.width);
			    console.log("e.height:"+e.height);
			    console.log("e.rotate:"+e.rotate);
			    console.log("e.scaleX:"+e.scaleX);
			    console.log("e.scaleY:"+e.scaleY);
			    */
		  }
		});
	   
   }
   
   //定义全局变量
   croppedDivID="";
   croppedDisplayName="";

   
   //裁剪上传后的文件
   function cropUploadedFile(object)
   {
	   $("#basic").toggle();
	   $("#advance").toggle();	  
	 
	   
	   //debugger;
	   var fileName=$(object).next().attr("src").replace("thumbnail.","");    
	   
	   //console.log(fileName);
	   //alert($(object).parent().parent().attr("id"));
	   croppedDivID=$(object).parent().attr("id"); 
	   
	   
	   //alert(croppedDivID);
	      debugger;
	   croppedDisplayName=$(object).parent().text();
	
	   
	   $("#img_Prizes").attr("src",fileName);   
	  
	   initCropper();
	   
   }

   //删除已上传文件
   function deleteUploadedFile(object)
   {	   
	 var fileName="";
	 
	 
	 if($(object).attr("id")=="")
	 {
	   fileName=$(object).next().next().attr("src").split("/")[5];	   
	 }
	 else
	 {
	   fileName=$(object).attr("src").split("/")[5];
	 }
	   
	 //注意文件名中可能本身包含特殊字符.  	 	 
	 
	 //var fileName=$(object).next().next().attr("src").split("/")[5];
	 
	 fileName=(fileName.split(".")[1]+"."+fileName.split(".")[2]).replace("thumbnail.","");
	 //console.log("fileName:"+fileName);
	 //fileName="1.jpg";
	 
	 //debugger;
	 

	 
	 //如果上传文件被全部删除,显示selectFile面板		 
		 $(object).parent().remove();		 
		 //debugger;
		 
		 if($('#uploadedFiles').text().trim()=="")
		 {
		   $("#uploadedFiles").hide();	 
		   $("#selectFiles").show();	 
		 }	 
		 
	 /*
	 $.get("/hlhh/jquery/delete/"+fileName, function(result){		
	   //console.log("result:"+result);	 
	   if(result=="Success")
	   {		   
	     layer.msg('取消上传成功^-^',
		 {
		   icon:6,
		   time:2000
		 });
	     
	     //调用父窗口方法更新数据库  
	     window.parent.updatePrize('Y');
	   }else if(result=="Failed")
	   {
		 layer.msg('取消上传失败-_-',
		 {
		   icon:5//,
		   //time:2000
		 });		 
	   }
     });*/
	
   }
  
  
   
    function reset()
    {
      $('#img_Prizes').cropper('reset');    	
    }

	function rotateToRight()
	{	
	  $('#img_Prizes').cropper('rotate', 90);		
	}
	
	function rotateToLeft()
	{		
	  $('#img_Prizes').cropper('rotate', -90);		
	}

	
	
    //定义全局变量globalImageID,用于存储用户选中的图片ID
    globalImageID="default";
    //debugger;
    
	
	$(function() {		
	 
	  
	  //alert(window.parent.showSelectFilesRegion);
	 

		   
	  //begin
	  /*css hack*/
	  //if($.browser.msie) 引用该判断会导致页面错乱		
	  var browser=navigator.userAgent;
	  if(browser.indexOf("MSIE")>0)
	  {
		//alert('haha');20px后能不加分号
		$("#fileupload").css("font-size","5rem");
		$("#fileupload1").css("font-size","2rem");
		
		//alert($("#fileupload").css("font-size"));
	  }
	  else
	  {
		//$("#fileupload").css("font-size","0px")
		//$("#fileupload1").css("font-size","0px")
	  }
		
	  $('#progressArea').hide();
	  $('#advance').hide();
	 
	  var layerIndex;
	  
		  $('#fileupload').fileupload({		     
		   
			  /*
			  maxFileSize: 50 * 1024 * 1024,
		        maxNumberOfFiles: 3,
		        messages: {
		            maxFileSize: '超过允许的最大值！',
		            maxNumberOfFiles: '上传的文件数量超过允许的最大值！'*/
		            
			    autoUpload: false,	                      
		        url:"/hlhh/jquery/fileUpload?type=luckyShake",		        
		        change: function(e, data){		        	
		        	
		        	$("#selectFiles").hide();	            	
	            	layerIndex = layer.load(2);	
		              	
	            },
	            add: function(e, data){            		  
	            	          	       
	            	 
	                 data.submit();//调用该方法上传大图片存在IE资源争用导致loading无法显示的问题
	              
	            	//data.submit();             	
	              	//setTimeout(submit(data),3000);	            	
	            },	            
		        done: function (e, data) {	        	
		        	 
		        	//alert(globalImageID);
		        	//console.log("globalImageID:"+globalImageID);
		        	
		        	//console.log(data.result);		        	 
		             layer.close(layerIndex);		            
		        	 //alert('haha');
		        	 $("#uploadedFiles").show();	
		        	
		             //全部上传完成后，等待2秒后清空进度条显示 
		             $("#progress").fadeOut(2000,function()
		             {
		               $("#progressArea").hide();		                
		             });
		              
		            //获取已上传文件个数img_thumbnail
		            
		             $("#selectFiles").hide();
		            
		            
		             var imageID='img_thumbnail'+Math.round(Math.random()*10000000000);
		             
		            //debugger;
		            //浏览器兼容性处理，如果为IE浏览器则执行下面这段		           	            
		            if(data.result[0].documentElement)
		            {
		              //IE浏览器		            
		              var jsonData=JSON.parse(data.result[0].documentElement.innerHTML.substring(19,data.result[0].documentElement.innerHTML.length-7))
		          	
		              //debugger;
		              //console.log("2号:"+jsonData.fileName);
		              //alert(jsonData.fileName);
		              //显示图片缩略图
		              $("#uploadedFiles").prepend(
		                  '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'" style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
		                    '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
		                    '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
		                    '<img id="'+imageID+'" style="max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+jsonData.fileName+'><p/>'+jsonData.displayName+
		                  '</div>'		                  
		                );
		            	
		            }
		            else
		            {	            	
		            	//alert('进来了');
		            	//非IE浏览器
		              if(globalImageID!='default') //更改图片
		              {
		            	 document.getElementById(globalImageID).src='/hlhh/upload/images/luckyShake/'+data.result[0].fileName; 	
		            
		            	 $("#"+globalImageID).next().text(data.result[0].displayName);
		              }
		              else //新增图片
		              {
		            		 
		            	  //显示图片缩略图
			              $("#uploadedFiles").prepend(
			                  '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'" style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
			                    '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
			                    '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
			                    '<img ondblclick=document.getElementById("fileupload").click();globalImageID="'+imageID+'" id="'+imageID+'" style="cursor:pointer;max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+data.result[0].fileName+'><p>'+data.result[0].displayName+'<p/>'+		                 
							  '</div>'
			                );  	
		              }           
		             
		            };	  
		            
		            //调用父窗口方法更新数据库  
      		   	    //window.parent.updatePrize('Y');	   	    
		            
		        }, 
		        
		        //批量上传多个图片时 progressall来不及调用，当图片全部上传后才开始回调
		        progressall: function (e, data) {		
		        	//alert('haha');
		        	var progress = parseInt(data.loaded / data.total * 100, 10);	
		        	
		        	//debugger;
		        	//alert(progress);
		        	//console.log("progress:"+progress);
		        	if(progress!=100)
		        	{
		        	  $("#progressArea").show();		        		
		        	  $("#progress").show();
		        	}		        
		        	
		        	//IE9下有兼容性问题		        	
		            //console.log("进度走起："+progress);
		            //console.log("缩略图："+$('#progress'));
		            
		            $('#progress').css('width',progress + '%');
		            $('#progress').text(progress+"%");      
		           
		        } 
		        
		    });
		  
		  
		  
		  /*
		  $('#fileupload').bind('fileuploadprogress', function (e, data) {
			    // Log the current bitrate for this upload:
			    console.log(data.bitrate);
			});*/
		  
			$('#fileupload')
		    .bind('fileuploadchange', function (e, data) {	 	
		    	
		    	//console.log($('#loadingImage'));	  
		    	
		    	//如果选择图像过多，CPU调度不过来，下面语句无法实时执行
		    	$('#loadingArea').hide();
		    	
		    	//console.log("1");	    	
		    	
		    })
		    
		  
		
		  $('#fileupload1').fileupload({	     
		        
		         url:"/hlhh/jquery/fileUpload?type=luckyShake",
		         change: function(e, data){		        	
			        	
		        	//上传大图片存在IE资源争用导致loading无法显示的问题
			        	$("#selectFiles").hide();	            	
		            	layerIndex = layer.load(2);	
		            	
		            },
		        done: function (e, data) {		      	
		        	        	 
		            layer.close(layerIndex);            
		        
		        	 $("#uploadedFiles").show();	
		        	
		              //全部上传完成后，等待2秒后清空进度条显示 
		              $("#progress").fadeOut(2000,function()
		              {
		                $("#progressArea").hide();  
		                
		              });
		              
		            //获取已上传文件个数img_thumbnail
		            
		              $("#selectFiles").hide();
		            
		              var imageID='img_thumbnail'+Math.round(Math.random()*10000000000);
		            
		            //debugger;
		            //浏览器兼容性处理，如果为IE浏览器则执行下面这段		           	            
		            if(data.result[0].documentElement)
		            {
		              var jsonData=JSON.parse(data.result[0].documentElement.innerHTML.substring(19,data.result[0].documentElement.innerHTML.length-7))
		          	
		              //debugger;
		              //console.log("5号："+jsonData.fileName);
		            //显示图片缩略图
		                $("#uploadedFiles").prepend(
		                    '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'"style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
		                      '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
		                      '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
		                      '<img id="'+imageID+'" style="max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+jsonData.fileName+'><p/>'+jsonData.displayName+
		                    '</div>'
		                );
		            	
		            }
		            else
		            {   
		            	//debugger;
		            	//console.log("6号："+data.result[0].fileName);
		            	//非IE浏览器  	
		            	//显示图片缩略图
		                $("#uploadedFiles").prepend(
		                    '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'"style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
		                      '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
		                      '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
		                      '<img ondblclick=document.getElementById("fileupload").click();globalImageID="'+imageID+'" id="'+imageID+'" style="cursor:pointer;max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+data.result[0].fileName+'><p/>'+data.result[0].displayName+
		                    '</div>'		 
		                );  	
		            };	   
		            
		            //调用父窗口方法更新数据库  
     		   	     //window.parent.updatePrize('Y');	   	    
		        }, 
		        progressall: function (e, data) {		            
		        	var progress = parseInt(data.loaded / data.total * 100, 10);	
		        	
		        	//debugger;
		        	//alert(progress);
		        	//console.log("progress:"+progress);
		        	if(progress!=100)
		        	{
		        	  $("#progressArea").show();		        		
		        	  $("#progress").show();
		        	}		        	
		        	//IE9下有兼容性问题
		            $('#progress').css('width',progress + '%');
		            $('#progress').text(progress+"%");
		        } 
		    });
		
	

			if(window.parent.showSelectFilesRegion=='N')
			{
			  //显示传入的图片	
			  //debugger;
			  $("#selectFiles").hide();
			  var picturesUrl=window.parent.picturesUrl.split(",");
			  
				 $.each(picturesUrl, function (index, value) {
					
				   if(picturesUrl[index]!='')
				   {
					   var imageID='img_thumbnail'+Math.round(Math.random()*10000000000);
					   
					   //debugger;
					   //console.log("7号："+picturesUrl[index].split("/")[0]+"/thumbnail."+picturesUrl[index].split("/")[1]);
					   
					   //显示图片缩略图
				       $("#uploadedFiles").prepend(
					       '<div id="div_thumbnail'+Math.round(Math.random()*10000000000)+'" style="position:relative;font-size:1rem;text-align:center;border-radius:0.5rem;margin:0.2rem;display:inline-block;border:0.1rem solid #CBCBCB;padding:0.5rem;box-shadow: 0.3rem 0.3rem 1rem #cbcbcb;">'+
						     '<img id="" onclick="deleteUploadedFile(this);" style="position:absolute;right:0rem;top:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/close2.png"/>'+
							 '<img onclick="cropUploadedFile(this);" style="position:absolute;right:0rem;bottom:0rem;width:2rem;height:20px;cursor:pointer;" src="../../images/common/cut.png"/>'+
						     '<img ondblclick=document.getElementById("fileupload").click();globalImageID="'+imageID+'" id="'+imageID+'" style="cursor:pointer;max-width:100%;max-height:100%;" src='+'/hlhh/upload/images/luckyShake/'+picturesUrl[index].split("/")[0]+"/thumbnail."+picturesUrl[index].split("/")[1]+'><p/>'+picturesUrl[index].split("/")[1]+
						   '</div>'
						);
				   }
					 
				 });
					 
			};

	});
	
	  
	/*
	document.querySelector('#file').addEventListener('change', function () {
		
		if(document.getElementById('file').value!='')	
		{
			
		  //$('#img_blankImage').css("width","100%");
		  //$('#img_blankImage').css("height","100%"); 
	       
		  //不管用户选择了多大的图片，将图片缩放至指定大小后显示  lrz有IE9兼容性问题
	      lrz(this.files[0],{quality:1,width:630,height:355}).then(function (rst) 
	      {
	       // 处理成功会执行
	       //console.log(rst);
	       //document.getElementById('img_Prizes').src=rst.file;
	       
	        $('#btn_selectImage').hide();
	        document.getElementById('img_blankImage').src=rst.base64;       
	        //console.log('haha');      
	      })      
	      .always(function () {
	       // 不管是成功失败，都会执行
	       
	    	  $('#btn_selectImage').hide();
	      });
		}
	});
	*/
	
</script>

</html>







