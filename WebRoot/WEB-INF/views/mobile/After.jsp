<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>手术后相册</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
	<link rel="stylesheet" href="static/mobile/css/imageviewer.css" />
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
		          <div class="mui-row mui-content-padded">
		          <c:forEach  var="list" items="${list}">
		          	<div class="mui-col-sm-4 mui-col-xs-4">
		          		<img data-preview-src="${list.imgPath }" data-preview-group="1" src="${list.imgPath }" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null" />
		          	</div>
		          	</c:forEach>
		          </div>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.zoom.js" ></script>
	<script type="text/javascript" src="static/mobile/js/mui.previewimage.js" ></script>
	<script>
		mui.init();
		mui.previewImage();
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			jQuery("img").on("error", function () {
		  		jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
		})
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
