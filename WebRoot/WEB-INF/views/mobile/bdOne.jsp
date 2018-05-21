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
    
    <title>查看日记</title>
    
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
   <!-- 主界面菜单同时移动 -->
	<!-- 侧滑导航根容器 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<ul class="mui-table-view mui-beautifuldairy">
						<li class="mui-table-view-cell mui-media">
							<img class="mui-pull-left dairy-touxiang" src="${abd.authorMinPhoto }" onerror="this.src='static/mobile/images/head_zhuanjia.png';this.onerror=null">
							<div class="mui-media-body">
								<span class="mui-left">${abd.authorName }</span>
								<span class="mui-right">${abd.time }<img src="static/mobile/images/riji_icon_time.png"/></span>
							</div>
							<div class="mui-row">
								<c:if test="${not empty abd.before}">
								<div class="mui-col-sm-6 mui-col-xs-6">
									<a class="dairy-before-img" href="bd/seeBefore.do?diaryId=${abd.id }">
									<img src="${abd.before }" style="height: 170px;object-fit: cover;" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null"/>
										<p class="flag">Before</p>
									</a>
								</div>
								</c:if>
								<c:if test="${not empty abd.after}">
								<div class="mui-col-sm-6 mui-col-xs-6">
									<a class="dairy-after-img" href="bd/seeAfter.do?diaryId=${abd.id }">
									<img src="${abd.after }" style="height: 170px;object-fit: cover; " onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null"/>
										<p class="flag">After</p>
									</a>
								</div>
								</c:if>
							</div>
						<p class="share-dairy-content">#<span>${abd.orderId }</span># ${abd.diary }</p>
						</li>
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/minghan.js"></script>
  </body>
</html>
