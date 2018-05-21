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
    <title>确认付款</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/mobile/css/goodCss.css"/>
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
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<ul class="mui-table-view mui-pay-address active">
							<li class="mui-table-view-cell">
								<a class="mui-navigate-right" href="app/address/getMyaddress.do?flag=111">
									<img src="static/mobile/images/adress_icon.png" />
									<p class="addr-info">${order.address }</p>
									<p class="lianxi-info">${order.linkman }&nbsp;${order.mobile }</p>
								</a>
							</li>
						</ul>
					<ul class="mui-table-view mui-order-detail">
							<li class="mui-table-view-cell mui-media">
								<img class=" mui-pull-left" src="${order.goodsLogo }">
								<div class="mui-media-body">
									<p class="store-title">${order.goodsName }</p>
									<p class="taocan-price"><span class="taocan-money">¥${order.goodsPrice }</span><span class="taocan-priced">¥${order.goodsMarket }<span class="mui-right">X${order.goodsNum }</span></span>
									</p>
								</div>
								
							</li>
							<div class="line-full"></div>
							<li class="mui-table-view-cell mui-collapse-content goumai-detail">
								<p>购买详情</p>
									<p>购买会员：<span>${name }</span></p>
									<p>购买数量：<span>${order.goodsNum }</span></p>
									<p>手续费率：<span>${shouxulv }%</span></p>
									<div class="store-foot-info">
									<p>共<span>${order.goodsNum }</span>件商品&nbsp;&nbsp;合计:<span class="total-price-store">¥${order.goodsAllPrice }</span></p>
								</div>
							
							</li>
						</ul>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab mui-goumai">
				<div class="total-price">
					总计<span>¥<span>${order.nowPay }</span></span>
				</div>
				<div class="mui-tab-item mui-zhifu-btn">
					<!-- <a href="javascript:showPayBox();" id="openPopover">余额支付</a> -->
					<a href="javascript:#popover-tequan" id="openPopover">支付</a>
				</div>
			</nav>
		
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<div id="popover-tequan" class="mui-popover">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll fukuang-method">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-collapse-content mui-text-center">
						<span class="mui-left" style="vertical-align: bottom;font-size:22px;" onclick="payclick();">×</span>
						<span style="text-align: center;line-height: 25px;">选择支付方式</span>
					</li>
		
					<form class="mui-input-group">
						<div class="mui-input-row mui-checkbox ">
							<label><img src="static/mobile/images/weixin_icon.png" />微信支付(¥<span>${order.nowPay }</span>)</label>
							<input name="Checkbox" type="checkbox" checked>
						</div>

					</form>
				
					<li class="mui-table-view-cell mui-collapse-content">
						<span class="mui-left zhifu">需支付</span>
						<span class="mui-right zhifu-money">¥${order.nowPay }</span>
					</li>
				</ul>
				<a href="javascript:goPay();" class="pop-queren-pay" id="goppp">确认支付</a>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script>
		mui.init();
			function payclick() {
			$("#popover-tequan").hide();
			$("#popover-tequan").removeClass("mui-active");
			$(".mui-backdrop").hide();
		} 
		var prepay_id;
			var paySign;
			var appId;
			var timeStamp;
			var nonceStr;
			var packageStr;
			var signType;
			var orderNo;
		function goPay(){
		$("#goppp").attr("href","javascript:void(0);");
		var add = '${order.address}';
		if(!add){
			 mui.toast("请填写收货地址！");
			 return;
		};
	$.ajax({
					url : "app/pay/wxOrder.do",
					type : 'POST',
					dataType : 'json',
					success : function(result) {
					//alert(result);
						var code = result.code;
						if(code == 1){
							mui.alert(result.msg);
							return;
						}
						var data = result.data;
						
						prepay_id = data.prepay_id;
				        paySign = data.paySign;
				        appId = data.appId;
				        timeStamp = data.timeStamp;
				        nonceStr = data.nonceStr;
				        packageStr = data.packageStr;
				        signType = data.signType;
				        orderNo = data.orderNo;
				        callpay();
						return false;
					},
					error:function(response,textStatus,errorThrown){
						alert(response.readyState +","+response.responseText+","+response.status+","+response.statusText );
						alert(textStatus);
						alert(errorThrown);
						return false;
					}
				});
				return false;
					}
	function onBridgeReady(){
			    WeixinJSBridge.invoke(
			        'getBrandWCPayRequest', {
			           "appId"     : appId,     //公众号名称，由商户传入
			           "timeStamp" : timeStamp, //时间戳，自1970年以来的秒数
			           "nonceStr"  : nonceStr , //随机串
			           "package"   : packageStr,
			           "signType"  : signType,  //微信签名方式：
			           "paySign"   : paySign    //微信签名
			        },
			        function(res){
			            if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			               mui.toast("交易成功");
			                window.location.href="app/muser/goods-center.do";
			            }
			            if (res.err_msg == "get_brand_wcpay_request:cancel") {  
			               mui.toast("交易取消");  
			                //window.location.href="${base}/test/cancel";
			            }  
			            if (res.err_msg == "get_brand_wcpay_request:fail") {  
			                mui.toast("支付失败"); 
			                //window.location.href="${base}/test/fail";
			            }  
			        }
			    );
			}
	function callpay(){
			    if (typeof WeixinJSBridge == "undefined"){
			        if( document.addEventListener ){
			            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			        }else if (document.attachEvent){
			            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			        }
			    }else{
			        onBridgeReady();
			    }
			}
	
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
