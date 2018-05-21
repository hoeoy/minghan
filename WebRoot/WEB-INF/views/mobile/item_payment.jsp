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
					<ul class="mui-table-view mui-order-detail">
						<h5>${shop.shopName}</h5>
						<div class="line-full"></div>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<img class=" mui-pull-left" src="${item.logo}">
							<div class="mui-media-body">
								<p class="store-title">${item.title}</p>
								<p style="display:none;" id=itemId>${item.id}</p>
								<p style="display:none;" id=currentPrice>${item.currentPrice}</p>
								<p class="taocan-price"><span class="taocan-money">¥${item.currentPrice}</span><span class="taocan-priced">¥${item.oldPrice}<span class="mui-right">X1</span></span>
								</p>
							</div>
							<div class="store-foot-info">
								<p>共<span>1</span>件商品&nbsp;&nbsp;合计:<span class="total-price-store" id="goodsPrice">¥${item.currentPrice}</span></p>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab mui-goumai">
				<div class="total-price">
					总计<span>¥<span>${item.currentPrice}</span></span>
				</div>
				<div class="mui-tab-item mui-zhifu-btn">
					<a href="javascript:showPayBox();" id="openPopover">余额支付</a>
				</div>
			</nav>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<div id="popover-pay2" class="mui-popover">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-collapse-content mui-text-center">
						<span class="mui-left" style="vertical-align: bottom;font-size:22px;" onclick="payclick();">×</span>
						<span style="text-align: center;line-height: 25px;">选择付款方式</span>
					</li>
					
					<form class="mui-input-group">
						<div class="mui-input-row mui-checkbox ">
							<label><img src="static/mobile/images/jifen_icon.png" />积分<span class="score">0</span>，可抵<span class="score">0</span>元</label>
							<input name="Checkbox" type="checkbox" checked value="0" id="point">
						</div>
						<div class="mui-input-row mui-checkbox ">
							<label><img src="static/mobile/images/yue.png" />余额(¥<span id="money">0.00</span>)</label>
							<input name="Checkbox" type="checkbox" value="1" id="balance">
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
		var itemPrice = null; //项目总价
		var userYue = null; //用户余额
		var userJifen = null; //用户积分
		var clickFlag = true;//点击支付按钮开关，默认打开，点击后关闭

		function payclick() {
			$("#popover-pay2").hide();
			$("#popover-pay2").removeClass("mui-active");
			$(".mui-backdrop").hide();
		}
		
		//显示支付框
		function showPayBox(){
			//获取当前用户积分余额信息
			var url = "${pageContext.request.contextPath}/vip/getUserBySession.do";
			var param = {};
			//获取用户信息
			getUserInfo(url, param);
		}

		$(document).ready(function(){
			clickFlag = true;//打开开关
			//按钮变黄色
		    $(".mui-zhifu-btn").css("background","#dfb354");
		    
			jQuery("img").on("error", function () {
		  		jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
			
			/* //给复选框绑定change事件
			$(document).on("change", "#point, #balance", function(){
				//需要支付金额变化
				autoNeedPay();
			}); */
		});
		
		function getUserInfo(url, param){
			//alert("ddd");
			$.post(url, param,
				function(result){
					if(result.code == 0){
						//有积分则必须使用积分
						$("#point").attr("disabled", "disabled");
						$("#balance").attr("checked", false); //取消选中
					
						var user = result.data;
						userYue = user.accountMoney; //用户余额
						userJifen = user.point; //用户积分
						itemPrice = parseFloat($("#goodsPrice").html().split("¥")[1]);//项目价格
						
						$(".score").html(userJifen);//积分
						$("#money").html(userYue);//余额
						
						//先只展示余额
						$("#money").html(userYue);//余额
						if(userJifen >= itemPrice){//积分大于产品价格
							$("#needPay").html("0.00");
						}else{
							$("#needPay").html(itemPrice - userJifen);
						}
					}
					//点击余额支付后显示
					mui("#popover-pay2").popover("toggle");
				}, "json"
			);
		}
		
		function autoNeedPay(){
			if($("#balance").is(":checked")){//选中
				if(userJifen >= itemPrice){//积分>=项目价格
					
				}else if(userYue+userJifen >= itemPrice && itemPrice > userJifen){//余额+积分>=项目价格 >积分
					//$("#money").html(userYue+userJifen - itemPrice);//余额显示
					$("#needPay").html("0.00"); //还需支付
				}else{
					//$("#money").html(0);
					$("#needPay").html(itemPrice -userJifen- userYue);
				}
			}else{//未选中
				//$("#money").html(userYue);
				if(userJifen >= itemPrice){//积分大于产品价格
					$("#needPay").html("0.00");
				}else{
					$("#needPay").html(itemPrice - userJifen);
				}
			}
		}
		
		//点击确认购买
		function pay(){
			if(clickFlag){
				//如果都不选则提示
				if(!$("#point").is(":checked") && !$("#balance").is(":checked")){
					mui.toast("至少选择一种支付方式");
					return;
				}
				
				var payMoney = $("#needPay").html();
				var yue = $("#money").html();
				if(!$("#balance").is(":checked") && yue != 0 && payMoney != 0){//未选中余额&余额不为0&支付不为0
					mui.toast("您账户中还有余额，请选用余额支付");
					return;
				}
				
				/* alert(itemPrice);
				alert(userJifen);
				alert(userYue); */
				if((userJifen + userYue) < itemPrice){
					var flag = confirm("您的余额暂时不足，购买后需要联系业务员改价。请确定是否购买");
					if(!flag){
						return;
					}
					/* alert(itemPrice - userJifen - userYue);
					return; */
					//mui.toast("您的余额暂时不足，购买后请联系业务员改价");
				}
				
				var id = $("#itemId").html();//项目id
				var url = "${pageContext.request.contextPath}/weixin/item/payConfirm.do";
				//param = {"id":id};
				$.ajax({
					url:'${pageContext.request.contextPath}/weixin/item/payConfirm.do?id=' + id,
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
		}
		
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
