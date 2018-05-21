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
<title>提现记录</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/mobile/css/allCss.css" />
<style type="text/css">
.mui-scroll {
	padding-bottom: 0px;
}

.mui-table-view {
	margin-bottom: 0px;
}
.mui-table-view.withdraw-review:after {
height:0px;
}
.mui-table-view.withdraw-review:before {
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
					<p class="mui-text-center" style="margin: 20px auto;line-height:30px;">---暂无提现---</p>
					</c:if>
					<ul class="mui-table-view withdraw-review">
					<c:forEach items="${list }" var="list">
						<li class="mui-table-view-cell mui-row">
							<div class="mui-left">
								<p class="tixian-discriable">佣金提现(手续费:${list.shouxu })</p>
								<p class="mui-datetime">${list.time }</p>
							</div>
							<div class="mui-right">
								<p class="tixian-discriable-right">¥${list.money }</p>
								<c:if test="${list.state eq '0' }"><a href="javascript:void(0)" class="confire-btn active">审核中</a></c:if>
								<c:if test="${list.state eq '1' }"><a href="javascript:void(0)" class="confire-btn active">已结算</a></c:if>
							</div></li>
						<div class="line-full"></div>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js"
		type="text/javascript"></script>
	<script>
		mui.init();
	</script>
	<script
		src="${pageContext.request.contextPath}/static/mobile/js/minghan.js"
		type="text/javascript"></script>
</body>
</html>
