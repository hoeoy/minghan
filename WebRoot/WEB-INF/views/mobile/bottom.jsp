<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css?q=1" />
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	//$(document).ready(function() {
		//$("#telephone").bind('click',function(){
		function callTelFun(){
			var btnArray=['取消','确定'];
	        $.ajax({
			    url:'${pageContext.request.contextPath}/app/user/getCustomPhone.do',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    success:function(data){
			    	if(data.code == 0){
			    		var phone=data.data.value;
				       // mui.confirm('确定拨打电话：'+phone+'吗?','提示',btnArray,function(e){
				       //     if(e.index == 1){
				            	//plus.device.dial(phone);
				            	window.location.href = 'tel:'+phone;
				        //    }
				       // });
			    		return;
			    	}
			    	alert(data.msg);
			    }
			});
	    }
	//});
	</script>
  </head>
  
<nav class="mui-bar mui-bar-tab">
	<a href="vip/redirect?type=1" class="mui-tab-item <c:if test='${type==1 }'>mui-active</c:if>">
		<span class="mui-icon mui-tab-shouye" ></span>
		<span class="mui-tab-label">首页</span>
	</a>
	<a href="vip/redirect?type=3" class="mui-tab-item <c:if test='${type==3 }'>mui-active</c:if>">
		<span class="mui-icon mui-tab-share"></span>
		<span class="mui-tab-label">分享</span>
	</a>
	<a href="javascript:callTelFun()" class="mui-tab-item">
		<span class="mui-icon mui-tab-phone"></span>
		<span class="mui-tab-label">电话</span>
	</a>
	<a href="vip/redirect?type=2" class="mui-tab-item <c:if test='${type==2 }'>mui-active</c:if>">
		<span class="mui-icon mui-tab-my"></span>
		<span class="mui-tab-label">我的</span>
	</a>
</nav>
</html>
