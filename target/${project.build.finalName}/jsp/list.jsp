<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!-- 引入JSTL -->
<%@include file="/jsp/common/jstl.jsp" %>



<!DOCTYPE html>

<html>
   <head>


 
   
      <title>秒杀列表页</title>
      <%@include file="/jsp/common/css.jsp" %>
     
   </head>
   <body>
 

     <div class="container">
     
       <div class="panel panel-default">	 
       
          <div class="panel-heading text-center">
          
          <h2>秒杀列表</h2>
          
          </div>
          
          <div class="panel-body">
          
             <table class="table table-hover">
             <thead>
             
             <tr>             
              <th>名称</th>
              <th>库存</th>
              <th>搞笑么？部分中文有乱码</th>
              <th>结束时间</th>
              <th>创建时间</th>
              <th>1111</th>             
             </tr>
               
             </thead>
             
             <tbody>       
                   
		           <c:forEach var="sk" items="${list}">
		           
		           <tr>
		             
		             <td>${sk.name}</td>
		             <td>${sk.number}</td>
		             <td>		             
		             <fmt:formatDate value="${sk.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		             </td>
		             
		             <td>		             
		             <fmt:formatDate value="${sk.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		             </td>
		             
		             <td>		             
		             <fmt:formatDate value="${sk.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		             </td>
		             
		             <td>	
		               <a class="btn btn-info" href="/seckill/${sk.seckill_id}/detail" target="_blank"></a>
		             </td>
		              
		           </tr>
		           
		           </c:forEach>
		           
		           
		     </tbody>
             
             </table>
          
          </div>
       
       </div>
     
     
     </div>
	
     
     
   </body>
   
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	 <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

	 <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	 <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

   
   
</html>