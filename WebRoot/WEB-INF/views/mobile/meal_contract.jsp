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

<title>套餐合同</title>
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/mobile/css/allCss.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css" />
<script
	src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
	jQuery("img").on("error", function () {
		  jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
			});
	</script>
	<style>
		.mui-hetong li img{width: 100%;max-height: 220px;height:auto;object-fit: cover;}
		.mui-table-view:after{height: 0px;}
	</style>
</head>

<body>
		<c:if test="${contracts != null && fn:length(contracts)>0 }">
			<ul class="mui-table-view mui-hetong">
			<c:forEach items="${contracts}" var="contract">
				<li class="mui-table-view-cell mui-collapse-content"><a
					href="javascript:void(0)"> <img src="${contract.contract}" /> </a>
				</li>
			</c:forEach>
				</ul>
		</c:if>


	<script>
		mui.init({
			pullRefresh: {
				container: "#pullrefresh", //待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
				up: {
					height: 50, // 可选.默认50.触发上拉加载拖动距离
					auto: false, // 可选,默认false.自动上拉加载一次
					contentdown: "下拉可以刷新", //可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容  
					contentover: "释放立即刷新", //可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容 
					contentrefresh: "正在加载...", // 可选，正在加载状态时，上拉加载控件上显示的标题内容
					contentnomore: '没有更多数据了', // 可选，请求完毕若没有更多数据时显示的提醒内容；
					callback: function() {
						var self = this; // 这里的this == mui('#refreshContainer').pullRefresh()
						// 加载更多的内容
						loadMore(this);
					} //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
				}
			}
		});
		/*if(mui.os.plus) {  
		    mui.plusReady(function() {  
		        setTimeout(function() { mui('#pullrefresh').pullRefresh().pullupLoading(); }, 1000);  
		    });  
		} else {  
		    mui.ready(function() {   
		        mui('#pullrefresh').pullRefresh().pullupLoading();   
		    });  
		} */
		// 
		var loadMore = function(pullRefresh) {
			// 加载更多的内容到列表中
			// 如果没有更多数据了，则关闭上拉加载
			/*  pullRefresh.endPullupToRefresh(true);*/
			// 如果有更多数据，则继续*/
			/* pullRefresh.endPullupToRefresh(false);*/
		};
		var contextPath = '${pageContext.request.contextPath}';
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js?v=1"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
