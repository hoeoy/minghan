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
	<title>积分明细</title>
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
					<div class="jifen-title">
						<img src="static/mobile/images/personal_jifen_icon.png"><span>${userScore}</span>	
					</div>
					<ul class="mui-table-view jifen-table-view">
						
						<c:if test="${list != null && fn:length(list)>0 }">
							<c:forEach items="${list}" var="order">
								<li class="mui-table-view-cell">
									<h4>${order.goodsName}</h4> 
									<p class="jifen">
										<c:if test="${order.status eq '0'}">-${order.point}</c:if>
										<c:if test="${order.status eq '1'}">+${order.point}</c:if>
									</p>
									<p>购买
										<span>
											<c:if test="${order.orderType eq '0'}">项目</c:if>
											<c:if test="${order.orderType eq '1'}">套餐</c:if>
										</span>
											<c:if test="${order.status eq '0'}">使用积分</c:if>
											<c:if test="${order.status eq '1'}">赠送积分</c:if> 领取时间：
										<span>${order.stringCreateDate}</span>
									</p>
								</li>
								<div class="mui-full-back"></div>
							</c:forEach>
						</c:if>
						
					        <!-- <li class="mui-table-view-cell">
					          <h4>回龄术(项目名称)</h4> 
					          <p class="jifen">+3000</p>
					          <p>购买项目送积分 领取时间：<span>2.17-02-02 20:30</span></p>
					        </li> -->
					    </ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	
	<script>
		mui.init();
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
