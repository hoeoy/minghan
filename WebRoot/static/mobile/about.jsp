<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>关于名韩</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		.mui-scroll {
		    -webkit-overflow-scrolling: touch;
		    padding-bottom: 0px;
		}
	</style>
  </head>
  
  <body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
		          <div class="mui-row">
		          	<img alt="关于名韩" src="static/mobile/images/about.jpg" width="100%">
		          </div>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
		mui(".mui-scroll-wrapper").scroll({
			deceleration: 0.0005,
		    bounce: false,//滚动条是否有弹力默认是true
		    indicators: false, //是否显示滚动条,默认是true
		});
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js?v=2"></script>
</body>
</html>
