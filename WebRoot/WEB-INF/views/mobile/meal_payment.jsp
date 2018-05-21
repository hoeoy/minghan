<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma","no-cache"); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>确认付款</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css?z=1"/>
	<style>
		.mui-input-group {margin-bottom: 0px;}
	</style>
  </head>
  
<body>
	<!-- 主界面菜单同时移动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper mui-detail">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<ul class="mui-table-view mui-order-detail">
						<h5>${shop.shopName}</h5>
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<img class=" mui-pull-left" src="${combo.logo}">
							<div class="mui-media-body">
								<p class="store-title">${combo.title}</p>
								<p style="display:none;" id=comboId>${combo.id}</p>
								<p class="taocan-price"><span class="taocan-money">¥${combo.currentPrice}</span><span class="taocan-priced">¥${combo.oldPrice}<span class="mui-right">X1</span></span>
								</p>
							</div>
							<div class="store-foot-info">
								<p>共<span>1</span>件商品&nbsp;&nbsp;合计:<span class="total-price-store" id="goodsPrice">¥${combo.currentPrice}</span></p>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab mui-goumai">
				<div class="total-price">
					总计<span>¥<span>${combo.currentPrice}</span></span>
				</div>
				<div class="mui-tab-item mui-zhifu-btn">
					<a href="javascript:openPayBox();" id="openPopover">余额支付</a>
				</div>
			</nav>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<div id="popover-pay1" class="mui-popover">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll fukuang-method">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-collapse-content mui-text-center">
						<span class="mui-left" style="vertical-align: bottom;font-size:22px;" onclick="payclick();">×</span>
						<span style="text-align: center;line-height: 25px;">选择付款方式</span>
					</li>
					<form class="mui-input-group">

						<!-- <div class="mui-input-row mui-checkbox ">
							<label><img src="static/mobile/images/jifen_icon.png" />积分<span id="score">662</span>可抵<span>6.62</span>元</label>
							<input name="Checkbox" type="checkbox">
						</div> -->
						<div class="mui-input-row mui-checkbox ">
							<label><img src="static/mobile/images/yue.png" />余额(¥<span id="money">0.00</span>)</label>
							<input name="Checkbox" type="checkbox" id="balance">
						</div>

					</form>
					<li class="mui-table-view-cell mui-collapse-content">
						<span class="mui-left zhifu">需支付</span>
						<span class="mui-right zhifu-money">¥<span id="needPay">0.00</span></span>
					</li>
				</ul>
				<a class="pop-queren-pay mui-zhifu-btn" id="payConfirm" href="javascript:pay();">确认支付</a>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script>
		mui.init();
		var userYue = null; //用户余额
		var comboPrice = null; //套餐总价
		var clickFlag = true;//点击支付按钮开关，默认打开，点击后关闭
		var yueClickFlag = true;//余额支付按钮开关，默认打开，支付关闭则跟着关闭

		function payclick() {
			$("#popover-pay1").hide();
			$("#popover-pay1").removeClass("mui-active");
			$(".mui-backdrop").hide();
		}
		
		function openPayBox(){
			if(yueClickFlag){
				//获取当前用户积分余额信息
				var url = "${pageContext.request.contextPath}/vip/getUserBySession.do";
				var param = {};
				getUserInfo(url, param);
				
				//点击支付后显示
				mui("#popover-pay1").popover("toggle");
			}
		}

		$(document).ready(function(){
			clickFlag = true;//打开开关
			yueClickFlag = true;
			//按钮变黄色
			 $(".mui-zhifu-btn").css("background","#dfb354");
			jQuery("img").on("error", function () {
				jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
			
			/* //给单选选框绑定change事件
			$(document).on("change", "#balance", function(){
				//需要支付金额变化
				autoNeedPay();
			}); */
		});
		
		function autoNeedPay(){
			if($("#balance").is(":checked")){//选中
				if(userYue >= comboPrice){//余额>=套餐价格
					//$("#money").html(userYue - comboPrice);//余额显示
					$("#needPay").html(0); //还需支付
				}else{
					//$("#money").html(0);
					$("#needPay").html(comboPrice - userYue);
				}
			}else{//未选中
				//$("#money").html(userYue);
				$("#needPay").html(comboPrice);
			}
		}
		
		function pay(){
			if(clickFlag){
				//如果不选则提示
				if(!$("#balance").is(":checked")){
					mui.toast("请选择一种支付方式");
					return;
				}
				
				//var payMoney = $("#needPay").html();
				/* alert(userYue);
				alert(comboPrice);
				return; */
				if(comboPrice > userYue){
					mui.toast("您的支付金额不足");
					return;
				}
			
				//获取使用的积分余额
				var comboId = $("#comboId").html();
				var url = "${pageContext.request.contextPath}/weixin/combo/payConfirm.do";
				//param = {"comboId":comboId};
				$.ajax({
					url:'${pageContext.request.contextPath}/weixin/combo/payConfirm.do?comboId=' + comboId,
					type:'post',
					async:false,
					success:function(result){
						//点击完后变灰
						$(".mui-zhifu-btn").css("background","#ccc");
						var row = result.data;
						if(row == 1){
							mui.toast("购买成功");
							//去首页
							window.location.href = "${pageContext.request.contextPath}/vip/redirect?type=1";
						}else{
							mui.toast(result.msg);
						}
					}
				});
				/* $.post(url, param, function(result){
					var row = result.data;
					if(row == 1){
						mui.toast("购买成功");
						//去首页
						window.location.href = "${pageContext.request.contextPath}/vip/redirect?type=1";
					}else{
						mui.toast(result.msg);
					}
				}, "json"); */
			}
			clickFlag = false;
			//点击完后变灰
			$(".mui-zhifu-btn").css("background","#ccc");
			//将余额支付设置为不可点
			yueClickFlag = false;
		}
		
		function getUserInfo(url, param){
			$.post(url, param,
				function(result){
					if(result.code == 0){
						$("#balance").attr("checked", false); //取消选中
						var user = result.data;
						userYue = user.accountMoney;
						comboPrice = parseFloat($("#goodsPrice").html().split("¥")[1]);//套餐价格
						$("#money").html(userYue);//余额
						$("#needPay").html(comboPrice);
					}
				}, "json"
			);
		}
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
