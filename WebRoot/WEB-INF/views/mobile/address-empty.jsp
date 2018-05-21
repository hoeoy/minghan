<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>地址管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
  </head>
  
   <body>
	  	<!-- 主界面移动、菜单不动 -->
		<div class="mui-off-canvas-wrap mui-draggable">
			<!-- 主页面容器 -->
			<div class="mui-inner-wrap">
				<!-- 主页面标题 -->
				<div class="mui-content mui-scroll-wrapper">
					<div class="mui-scroll">
						<!-- 主界面具体展示内容 -->
						<div class="where-address">
							<img src="static/mobile/images/adress_no.png" />
							<p>收货地址在哪里</p>
						</div>
					</div>
				</div>
				<nav class="mui-bar mui-bar-tab mui-create">
					<div class="new-create-btn">
						<a href="app/address/addAddress.do"><span>＋</span>新建地址</a>
					</div>
				</nav>
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init();
		</script>
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
	</body>
</html>
