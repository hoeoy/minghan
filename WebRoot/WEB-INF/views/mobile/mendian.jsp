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
    <title>适用门店</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>
	<style>
			.mui-table-view.mendian-list img.mui-media-object  {
				margin: 10px 5px;
				width: 42px;
				height: 42px;
				object-fit: cover;
			}
			
			.mui-table-view.mendian-list .mui-table-view-cell:after {
				height: 1px;
				left: 0px;
				right: 0px;
			}
			.mui-table-view.mendian-list .mui-media-body p{line-height: 42px;}
		</style>
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

						<ul class="mui-table-view mendian-list">
							<!-- <li class="mui-table-view-cell mui-collapse-content mui-media">
								<img class="mui-media-object mui-pull-right" src="images/defult_img.png" data-preview-src="images/defult_img.png" data-preview-group="1" />
								<div class="mui-media-body">
									<p class="mui-ellipsis-2">门店名</p>
								</div>
							</li> -->
							<c:if test="${shops != null && fn:length(shops)>0 }">
								<c:forEach items="${shops}" var="shop">
									<li class="mui-table-view-cell mui-collapse-content mui-media">
										<img class="mui-media-object mui-pull-right" src="${shop.shopLogo}" data-preview-src="${shop.shopLogo}" data-preview-group="${shop.id}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;"/>
										<div class="mui-media-body">
											<p class="mui-ellipsis-2">${shop.shopName}</p>
										</div>
									</li>
								</c:forEach>
							</c:if>

						</ul>
					</div>
				</div>
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.zoom.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.previewimage.js" type="text/javascript"></script>
	<script>
		mui.init();
		mui.previewImage();
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
</body>
</html>
