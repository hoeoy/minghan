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
	<title>消费记录</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<style type="text/css">
		.mui-scroll{
			padding-bottom: 0px;
		}
		.mui-table-view {
		    margin-bottom: 0px;
		}
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
			<div class="mui-content mui-scroll-wrapper" id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					
					<ul class="mui-table-view mui-my-projectorder" id="top-record">
						<c:if test="${orders != null && fn:length(orders)>0 }">
							<c:forEach items="${orders}" var="order">
								<li class="mui-table-view-cell mui-media">
									<div class="mui-media-body"><img src="static/mobile/images/project_stores_icon.png"/><span><%-- ${order.shopName} --%></span>
										<span class="recond-zhifu">${order.payState eq '0' ? '未支付' : '已支付'}</span>
									</div>
								</li>
								<div class="line-full"></div>
								<li class="mui-table-view-cell mui-media">
									<p class="project-name">${order.goodsName}</p>
									<p class="project-time">${order.stringCreateDate}</p>
								</li>
								<div class="line-full"></div>
								<li class="mui-table-view-cell mui-media mui-row">
									<div class="mui-col-sm-5 mui-col-xs-5 mui-project-price">
										金额：<span class="money">¥${order.dealPrice}</span>
									</div>
								</li>
								<div class="mui-full-back"></div>
							</c:forEach>
						</c:if>
					</ul>
					
					<!-- <ul class="mui-table-view mui-my-projectorder">
						<li class="mui-table-view-cell mui-media">
							<div class="mui-media-body"><img src="images/project_stores_icon.png"/><span>门店的名称</span>
								<span class="recond-zhifu">已支付</span>
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
								金额：<span class="money">¥2300</span>
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
		//mui.init();
		var page = 1;//第一页
		var totalPage = ${allPage};//总页数
		var url = "weixin/order/getRecordListLimit.do";
		mui.init({
		    pullRefresh: {
		        container: '#pullrefresh',
		        up: {//上拉加载
		            contentrefresh: '正在加载...',
		            contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
		            callback: pullupRefresh
		        }
		    }
		});
		$(document).ready(function() {
			if(page>=totalPage){
		    	mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
	    	}
		});
		function pulldownRefresh() {
		    setTimeout(function() {
		    	page = 1;//刷新并显示第一页
		        data={
		            page:page
		        };
		        type=1;//代表下拉刷新
		        toList(data,type);//具体取数据的方法
		    }, 100);
		}
		function pullupRefresh() {
		    setTimeout(function() {
		    	if(page>=totalPage){
			    	mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。  
			    	return;
		    	}
		    	page++;//翻下一页
		        data={
		            page:page
		        };
		        type=2;//代表上拉加载
		        toList(data,type);//具体取数据的方法
		    }, 100);
		}
		function toList(data,type) {
		    $.ajax({
		    	url:url,
				type:'POST',
				async:true,
		        data:data,
		        dataType: 'json',
				success:function(result){
					 var data = result.data;
					 if(data && data.length > 0){
			            for (var i in data) {
							var html = 
								'<ul class="mui-table-view mui-my-projectorder">'+
								'<li class="mui-table-view-cell mui-media">'+
									'<div class="mui-media-body"><img src="static/mobile/images/project_stores_icon.png"/>'+//<span>'+data[i].shopName+'</span>'+
										'<span class="recond-zhifu">'+(data[i].payState == 0 ? "未支付" : "已支付")+'</span>'+
									'</div>'+
								'</li>'+
								'<div class="line-full"></div>'+
								'<li class="mui-table-view-cell mui-media">'+
									'<p class="project-name">'+data[i].goodsName+'</p>'+
									'<p class="project-time">'+data[i].stringCreateDate+'</p>'+
								'</li>'+
								'<div class="line-full"></div>'+
								'<li class="mui-table-view-cell mui-media mui-row">'+
									'<div class="mui-col-sm-5 mui-col-xs-5 mui-project-price">'+
										'金额：<span class="money">¥'+data[i].dealPrice+'</span>'+
									'</div>'+
								'</li>'+
								'</ul>'+
								'<div class="mui-full-back"></div>';
			            	$("#top-record").append(html);
			                      //mui.currentWebview.show(); //显示当前页面
			             }
					 }
		             mui('#pullrefresh').pullRefresh().endPulldownToRefresh();//结束下拉刷新
		             /*结束上拉加载，并根据情况切换“下拉显示更多数据”，以及“没有更多数据了”。执行endPullupToRefresh()方法，结束转雪花进度条的“正在加载...”过程,若还有更多数据，则传入false; 否则传入true，之后滚动条滚动到底时*/
		             if(page < totalPage){
		                 mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
		             } else {
		                 mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
		             }
		        },
		        error: function(xhr, type, errerThrown) {
		            mui.toast('网络异常,请稍候再试');
		            mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
		        }
		    });
		};
		var slider = mui("#slider");
		slider.slider({
			interval: 5000
		});

		mui('.mui-scroll-wrapper').scroll({
			deceleration: 0.02,
			indicators: false,
		}); //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
		mui('body').on('tap', 'a', function() {
			document.location.href = this.href;
		});
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
