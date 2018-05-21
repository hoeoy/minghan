<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>分享</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" href="static/mobile/css/allCss.css" />
	<script src="static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="static/mobile/js/mui.min.js" type="text/javascript"></script>
	<style type="text/css">
		.img-responsive {
		  display: inline-block;
		  height: auto;
		  max-width: 100%;
		}
	</style>
  </head>
 <body> 
  <!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			<jsp:include page="bottom.jsp"></jsp:include>
			
			<div class="mui-content mui-scroll-wrapper mui-index" style="background: #f7f7f7;padding:0px;">
				<div class="mui-scroll" style="margin:0px;padding:0px;padding-bottom:50px;">
					<!-- 主界面具体展示内容 -->
					<img alt="分享海报" src="${filePath}" class="img-responsive">
				</div>
			</div>
		</div>
	</div>
	<script>
		mui.init();
		mui('body').on('tap', 'a', function() {
			document.location.href = this.href;
		});
		mui(".mui-scroll-wrapper").scroll({
			 deceleration: 0.0005,
		     bounce: false,//滚动条是否有弹力默认是true
		     indicators: false, //是否显示滚动条,默认是true
		}); 
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
	</body>
</html>
