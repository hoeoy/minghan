<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>地址管理</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<!--   <script type="text/javascript" src="test.js"></script> -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
<link rel="stylesheet" type="text/css"
	href="static/mobile/css/allCss.css" />
		<style>
#popover-delete {
	height: 120px;
	width: 240px;
	top: calc(50% -   60px);
	left: calc(50% -   120px) !important;
}

.delete-title {
	height: 80px;
	text-align: center;
	line-height: 80px;
	color: #333;
	font-size: 18px;
}

.mui-popover.pop-delete .mui-scroll-wrapper {
	margin: 0px 0;
}

.delete-cancel {
	width: 50%;
	display: block;
	height: 40px;
	line-height: 40px;
	color: #007AFF;
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc;
	font-size: 16px;
}

.delete-submit {
	width: 50%;
	display: block;
	height: 40px;
	line-height: 40px;
	color: #007AFF;
	border-top: 1px solid #ccc;
	font-size: 16px;
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
					<div class="address-top-line"></div>
					<form class="mui-input-group">
						<c:forEach items="${list}" var="list">
							<ul class="mui-table-view mui-address-list">
								<li class="mui-table-view-cell mui-collapse-content">
								<a href="app/mgoods/updateOrderAddress.do?id=${list.id }">
									<div class="address-info-name">
										<span class="mui-left">${list.linkman }</span>
										<span class="mui-right">${list.mobile }</span>
									</div>
									<p class="detail-address">
										${list.province } ${list.city } ${list.zone } ${list.addr }
									</p>
									</a>
								</li>
								<li class="mui-table-view-cell mui-collapse-content">
									<div class="mui-input-row mui-radio mui-left">
										<label>设为默认地址</label>
										<c:if test="${list.primaryFlag==1 }"><input class="default" name="radio" type="radio"  value="${list.id }" checked /></c:if>
										<c:if test="${list.primaryFlag==0 }"><input class="default" name="radio" type="radio"  value="${list.id }"/></c:if>
									</div>
									<div class="mui-right edit-delete">
										<a href="app/address/updateAddress.do?id=${list.id }">
											<img src="static/mobile/images/adress_edit_btn.png" />
										</a>
									</div>
								</li>
							</ul>
							<div class="mui-full-back"></div>
							</c:forEach>
					</form>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab tab-address">
				<a href="app/address/addAddress.do" class="mui-tab-item">
					<span>＋</span>新建地址
				</a>
		        </nav>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
    <div id="popover-delete" class="mui-popover pop-delete">
			<div class="mui-scroll-wrapper">
			    <div class="mui-scroll">
				    <div class="delete-title">是否确认删除该地址</div>
				    <a class="mui-left mui-text-center delete-cancel" href="javascript:yfclick();">取消</a>
				    <a class="mui-left mui-text-center delete-submit" href="javascript:gogogo();">确认</a>
				</div>
			</div>
		</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init();
		
						function open(data){
				mui("#popover-delete").popover("toggle");
				 id = data; 
		}
			function yfclick() {
			$("#popover-delete").hide();
			$("#popover-delete").removeClass("mui-active");
			$(".mui-backdrop").hide();
		} 
		function gogogo(){
		window.location.href="app/address/deleteAddress.do?id="+id;
		}
       
		$(function() {
		var id;
		$(":radio").click(function(e){
				var id = this.value;
				 $.ajax({
				 url:"app/address/updatePrimaryFlag.do",
				 type:'POST',
				 data:{"id":id},
				 dataType:'json',
				success : function(result) {
				}
				});
 				    });
 						 })
		
		
/* 		$(":radio").click(
						function() {
							var id = $(this).val();
							window.location.href = "app/order/updateAddress.do?addressId="
									+ id;
						}) */
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
