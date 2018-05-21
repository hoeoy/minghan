<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>订单详情</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
<link rel="stylesheet" href="static/mobile/css/allCss.css" />
<link rel="stylesheet" href="static/mobile/css/phone-sp.css" />
<link rel="stylesheet" href="static/mobile/css/goodCss.css" />
<style>
*{
	margin: 0;
	padding: 0;
	-webkit-user-select: text;
}
</style>
</head>

<body>
	<!-- 主界面菜单同时移动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<div class="address-top-line"></div>
					<ul class="mui-table-view mui-pay-address active">
						<li class="mui-table-view-cell">
							<img src="static/mobile/images/adress_icon.png" />
								<p class="addr-info">${order.address }</p>
								<p class="lianxi-info">${order.linkman }&nbsp;${order.mobile }</p> 
						</li>
						<!-- 待发货需要的物流信息-->
						<c:if test="${order.orderState eq '待收货' or order.orderState eq '交易完成' }">
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-collapse-content">
                          <a href="http://m.kuaidi100.com" class="mui-navigate-right">
							<img src="static/mobile/images/logistics_icon.png" />
							<p class="addr-info">快递名称：<span>${order.expressName }</span></p>
							<p class="lianxi-info" style="font-size:14px;">快递单号(可复制)：<%-- <span>${order.expressNo }</span> --%><input type="text" value="${order.expressNo }" readonly="readonly" style="width: 50%;height:23px;border: 0px;margin-bottom: 0px;padding: 0px;color: #333;" /></p>
						  </a>
						</li> 
						</c:if>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view order-detail">
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-fahuo-title">${order.orderState }</p>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<img class="mui-media-object mui-pull-left"
							src="${order.goodsLogo }">
							<div class="mui-media-body mui-row">
								<div class="mui-col-sm-10 mui-col-xs-10">
									<p class="order-detail-name">${order.goodsName }</p>
									<p class="order-detail-price">
										¥${order.goodsPrice }<span>¥${order.goodsMarket }</span>
									</p>
								</div>
								<div class="mui-col-sm-2 mui-col-xs-2">
									<p class="mui-right order-detail-name">×${order.goodsNum }</p>
								</div>
							</div>
						</li>
						<!--	<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-fahuo-title">
								<span class="mui-black-color mui-left">运费</span>
								<span class="mui-black-color mui-right">¥0</span>
							</p>
						</li>-->
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-fahuo-title mui-black-color mui-left">免运费</p>
							<p class="mui-red-color mui-right mui-fahuo-title">实付：¥${order.nowPay }</p>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view order-detail">
						<li class="mui-table-view-cell mui-collapse-content order-detial-foot">
							<p>
								订单编号(可复制)：&nbsp;<input type="text" value="${order.orderNo }" readonly="readonly" style="width: 60%;height:23px;border: 0px;margin-bottom: 0px;padding: 0px;color: #333;" />
							</p>
							<p>
								支付方式：&nbsp;<span>微信支付</span>
							</p>
							<p>
								创建时间：&nbsp;<span><fmt:formatDate value='${order.createDate}' pattern='yyyy-MM-dd HH:mm:ss'/></span>
							</p>
							<c:if test="${order.orderState eq '待发货' }">
							<p>
								付款时间：&nbsp;<span>${order.payTime}</span>
							</p>
							</c:if>
							<c:if test="${order.orderState eq '待收货' }">
							<p>
								发货时间：&nbsp;<span>${order.sendTime}</span>
							</p>
							</c:if>
							<c:if test="${order.orderState eq '交易完成' }">
							<p>
								完成时间：&nbsp;<span>${order.completeTime}</span>
							</p>	
							</c:if>
						</li>
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
		function payclick() {
			$("#popover-tequan").hide();
			$("#popover-tequan").removeClass("mui-active");
			$(".mui-backdrop").hide();
		}
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
