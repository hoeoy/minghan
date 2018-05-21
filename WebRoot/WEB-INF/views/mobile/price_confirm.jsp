<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>价格确认</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	
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
							<ul class="mui-table-view mui-price-confirm">
								<li class="mui-table-view-cell mui-collapse-content mui-media">
									<div class="mui-media-body"> <img class="mui-pull-left" src="static/mobile/images/record_vip_icon.png">
										<p class="mui-ellipsis">${order.buyName}</p>
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
										价格：<span class="money">¥${order.dealPrice}</span>
									</div>
									<div class="mui-col-sm-7 mui-col-xs-7 mui-project-price">
										<div class="mui-right">
											<a class="mui-change mui-left change" href="javascript:showPriceBox('${order.id}');" id="${order.id}">变更价格</a>
											<%-- <p style="display:none;">${order.id}</p> --%>
											<a class="mui-right shenhe active check" href="javascript:clickChange('${order.id}');">提交审核</a>
										</div>
									</div>
								</li>
							</ul>
							<div class="mui-full-back"></div>
						</c:forEach>
					</c:if>
					
					<!-- <ul class="mui-table-view mui-price-confirm">
						<li class="mui-table-view-cell mui-media">
							<div class="mui-media-body"> <img class="mui-pull-left" src="images/record_vip_icon.png">
								<p class="mui-ellipsis">会员名称</p>
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
								价格：<span class="money">¥2300</span>
							</div>
							<div class="mui-col-sm-7 mui-col-xs-7 mui-project-price">
								<div class="mui-right">
									<a class="mui-change mui-left" href="#popover-changeprice">变更价格</a>
									<div class="mui-right shenhe active" id="idsh">提交审核</div>
								</div>
							</div>
						</li>
					</ul> -->
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<div id="popover-changeprice" class="mui-popover">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<div class="mui-pop-title" id="boxTitle">请输入变更价格，然后点击“立即变更”按钮</div>
				<form class="mui-input-group mui-pop-form">
					<div class="mui-input-row">
						<input type="hidden" id="orderId" name="orderId" value=""/>
						<input type="text" placeholder="请输入变更价格" id="text">
						<div onclick="changeclick();" class="change-btn">立即变更</div>
					</div>
					<div class="mui-pop-beizhu">变更价格后请提交审核</div>
				</form>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		mui.init();
	/*	document.getElementById("idsh").addEventListener("tap", function() {
			$("#idsh").removeClass("active");
			mui.toast('审核已提交');
		});*/
		
		var initPrice = null;
		
		function changeclick() {
			var text = $("#text").val();
			if(text == null || text == ""){
				mui.toast("请输入变更价格");
				return;
			}
			if(parseFloat(text) < 0){
				mui.toast("变更价格需大于或等于0");
				return;
			}
			
			$("#popover-changeprice").hide();
			$("#popover-changeprice").removeClass("mui-active");
			$(".mui-backdrop").hide();
			
			var id = $("#orderId").val();
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/changePrice.do?id='+id+'&newPrice='+text,
				type:'get',
				async:true,
				success:function(result){
					mui.toast(result.msg);
					if(result.code == 0){
						flash();
					}
				}
			});
			
		}
		$(".shenhe").on("tap", function() {
			if($(this).hasClass("active")) {
				mui.toast("审核中");
				$(this).removeClass("active");
			} else {
				mui.toast("请勿重复提交");
			}
		});
		
		function showPriceBox(id){
			//获取当前点击按钮的价格，存入全局变量
			var obj = "#" + id;
			var strIdex = $(obj).parent().parent().parent().children().html();
			var curentOirderPrice = strIdex.split("¥")[1];
			initPrice = curentOirderPrice.split("<")[0];
		
			$("#orderId").val(id);
			$("#text").val("");
			mui("#popover-changeprice").popover("toggle");
			
			//获取项目区间价
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/getItemDetail.do?orderId='+id,
				type:'get',
				async:true,
				success:function(result){
					if(result.code == 0){
						var item = result.data;
						var str = "项目区间价为"+item.currentMin+"-"+item.currentMax;
						$("#boxTitle").html(str);
					}
				}
			});
		}
		
		//提交审核
		function clickChange(orderId){
			//id = orderId;
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/check.do?id='+orderId,
				type:'get',
				async:true,
				success:function(result){
					mui.toast(result.msg);
					if(result.code == 0){
						//改价不能再点
						$("#"+orderId).attr("href", "javascript:void(0);");
						$("#"+orderId).attr("class", "mui-change mui-left change mui-active");
						//若该订单改价审核完毕，则继续向下跑
						runOrder(orderId);
					}
				}
			});
		}
		
		function runOrder(id){
			$.get("${pageContext.request.contextPath}/weixin/item/afterChangePrice.do?id="+id);
		}
		
		//刷新本页
		function flash(){
			window.location.href = "${pageContext.request.contextPath}/weixin/item/priceConfirm.do";
		}
		
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
