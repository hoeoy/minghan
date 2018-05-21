<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>我的项目单</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	
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
					
					<c:if test="${orders != null && fn:length(orders)>0 }">
						<c:forEach items="${orders}" var="order">
							<ul class="mui-table-view mui-my-projectorder">
								<li class="mui-table-view-cell mui-collapse-content mui-media">
									<div class="mui-media-body">
										<span class="mui-ellipsis">审核人：
											<span>${order.checkName}</span>
										</span>
										<!-- <span class="shouhuo">已收货</span> -->
									</div>
								</li>
								<div class="line-full"></div>
								<li class="mui-table-view-cell mui-collapse-content mui-media">
									<p class="project-name">${order.goodsName}</p>
									<p class="project-time">${order.stringCreateDate}</p>
								</li>
								<div class="line-full"></div>
								<li class="mui-table-view-cell mui-collapse-content mui-media mui-row">
									<div class="mui-col-sm-5 mui-col-xs-5 mui-project-price">
										实付：<span class="money">¥${order.dealPrice}</span>
									</div>
									<div class="mui-col-sm-7 mui-col-xs-7 mui-project-price">
										<div class="mui-right">
											<c:if test="${order.checkState eq '1'}">
												<c:choose>
													<c:when test="${order.hasDiary eq '0'}">
															<a class="mui-edit-dairy"  href="bd/wBeautyDiary.do?orderId=${order.id }">待写日记</a>
													</c:when>
													<c:otherwise>
														<a class="mui-edit-dairy"  href="bd/seeOneBD.do?orderId=${order.id }">查看日记</a>
													</c:otherwise>
												</c:choose>
											</c:if>
											<%--<c:if test="${order.checkState eq '0'}">
												<c:if test="${order.hasDiary eq '0'}">
													<a class="mui-edit-dairy"  href="javascript:warning();">待写日记</a>
												</c:if>
											</c:if>
											--%>
											<!--<a class="mui-commit-check">查看日记</a>-->
										</div>
									</div>
								</li>
							</ul>
							<div class="mui-full-back"></div>
						</c:forEach>
					</c:if>
					
					<!-- <ul class="mui-table-view mui-my-projectorder">
						<li class="mui-table-view-cell mui-media">
							<div class="mui-media-body"> <span class="mui-ellipsis">审核人：<span>大大</span></span>
								<span class="shouhuo">已收货</span>
							</div>
						</li>
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-media">
							<p class="project-name">项目名称项目名称项目名称项目名称项目名称项目名称项目名称项目名称</p>
							<p class="project-time">2017-02-02&nbsp;20:30</p>
						</li>
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-media mui-row">
							<div class="mui-col-sm-5 mui-col-xs-5 mui-project-price">
								实付：<span class="money">¥2300</span>
							</div>
							<div class="mui-col-sm-7 mui-col-xs-7 mui-project-price">
								<div class="mui-right">
									<a class="mui-commit-check mui-active">待写日记</a>
									<a href="beautifuldairy-one.html" class="mui-edit-dairy">查看日记</a>
								</div>
							</div>
						</li>
					</ul>
					<div class="mui-full-back"></div> -->
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script>
		mui.init();
		
		function warning(){
			mui.toast("该项目暂未审核，不能写日记");
		}
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
