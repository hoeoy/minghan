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
    
    <title>专家团队</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
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
		<!--	<header class="mui-bar mui-bar-nav">
                    <h1 class="mui-title">价格确认</h1>
                </header>-->
		<div class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!-- 主界面具体展示内容 -->
				<ul class="mui-table-view mui-team-expert">
				<c:forEach var="list" items="${list}">
					<li class="mui-table-view-cell mui-collapse-content mui-media">
						<a href="doct/getDoctById?id=${list.id }">
							<div class="mui-head-doctor mui-pull-left">
									<img class="mui-media-object" src="${list.minPhoto }" onerror="this.src='static/mobile/images/head_zhuanjia.png';this.onerror=null;">
								</div>
							<div class="mui-media-body">
								<p class="doctor-name">${list.name }<span>${list.post }</span>
								<span class="mui-right doctor-list-flag">${list.attestationFlag }</span>
								</p>
								<p class="mui-ellipsis">
									<img src="static/mobile/images/icon_introduce.png" />
									${list.intro }
								</p>
							</div>
						</a>
					</li>
					    </c:forEach>
				</ul>
			</div>
		</div>
		<div class="mui-off-canvas-backdrop"></div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
<script src="static/mobile/js/mui.min.js"></script>
<script>
	mui.init();
</script>
<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
