<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>返现领取</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css?v=1" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
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
					<table class="mui-table list">
						<tr class="table-title">
							<th>级别</th>
							<th>时间</th>
							<th>金额</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
						<c:forEach var="list" items="${list}">
						<c:choose>
						<c:when test="${list.time<0&&list.state=='点击领取' }">
						<tr class="table-list active">
						</c:when>
						<c:when test="${list.time>0||list.state=='已领取' }">
						<tr class="table-list">
						</c:when>
						</c:choose>
							<td>${list.comboName }</td>
							<td>
								<fmt:parseDate value="${list.startTimeString}" pattern="yyyy-MM-dd" var="masterDate"/>
								<fmt:formatDate value="${masterDate}" pattern="yyyyMMdd"/>
							</td>
							<td>
								￥<fmt:formatNumber value="${list.money }" pattern="#.#"/>
							</td>
							<td id="${list.id }audit" }>${list.auditFlag }</td>
							<td class="btn-color" id="${list.id }">${list.state }</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
		$(".table-list .btn-color").on("tap", function() {
			if($(this).parent(".table-list").hasClass("active")) {
				//mui.toast("领取中");
				$(this).parent(".table-list").removeClass("active");
				var id = $(this).attr('id');
				var _parent = this;
				$.ajax({
					url:"app/monthBack/getReturn.do",
					data:{id:id},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data == 1){
							mui.toast("领取成功");
							$(_parent).html("已领取");
							$("#"+id+"audit").html("待审核");
							return;
						}else if(data == 0){
							mui.toast("还未到领取时间");
						}else if(data == 2){
							mui.toast("您暂无返现");
						}else{
							mui.toast("操作失败，请稍后重试");
						}
					}
				})
			} else {
				mui.toast("不能领取");
			}
		});
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body></html>
