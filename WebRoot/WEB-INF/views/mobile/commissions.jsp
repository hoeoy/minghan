<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>佣金明细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" href="static/mobile/css/allCss.css" />
    <style>
    .mui-table-view.mui-commissions:after {
height:0px;
}
.mui-table-view.mui-commissions:before {
height:0px;
}
    </style>
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
					<c:if test="${fn:length(list)==0 }">
					<p class="mui-text-center" style="margin: 20px auto;line-height:30px;">---暂无消费---</p>
					</c:if>
					<ul class="mui-table-view mui-commissions">
					<c:forEach items="${list }" var="list">
						<li class="mui-table-view-cell mui-media">
							<div class="mui-media-body"> <span class="mui-ellipsis">奖励<span>¥${list.money }</span>
							<c:if test="${list.awardLevel }">(${list.awardLevel })</c:if>
							
							</span>
								<span class="jiangli">${list.type }</span>
							</div>
						</li>
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-media">
							<p class="project-name">${list.consumerName }-${list.goodsName }</p>
							<p class="success-consum">成功消费<span>¥${list.goodsPrice }</span></p>
							<p class="project-time">${list.time }</p>
						</li>
						<div class="mui-full-back"></div>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
