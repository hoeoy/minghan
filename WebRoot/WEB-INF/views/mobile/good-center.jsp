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
    
    <title>商品中心</title>
    
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

  </head>
  
  <body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable mui-slide-in">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			<!--<header class="mui-bar mui-bar-nav">
                <h1 class="mui-title">首页</h1>
           </header>-->
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll" style="position: initial;">
					<!-- 主界面具体展示内容 -->
						<ul class="mui-table-view mui-one-center" style="background-color: #fff;">
							<li class="mui-table-view-cell mui-collapse-content mui-media">
								<p class="one-member-fask mui-left">
									<img src="static/mobile/images/personal_vip_icon.png"/>
									${user.mUserLevel }
								</p>
								<p class="up-people mui-right">
									上级：${user.parentName }
								</p>
							
							</li>
							<li class="mui-table-view-cell mui-collapse-content mui-media">
							<div style="width:100%;margin:30px auto 0px;text-align: center;">
							            <p class="one-member-fask"  style="background:#3dd3b3;margin-top:-25px;">
							            <img src="static/mobile/images/shangpin.png"/>
									<c:if test="${user.flag=='0' }">未拥有</c:if>
									<c:if test="${user.flag=='1' }">已拥有</c:if>
										</p>
										</div>
							</li>
							<li class="mui-table-view-cell mui-collapse-content mui-text-center" >
							<c:if test="${user.flag=='1' }">
								<p style="padding: 0px 0px 10px;color: #f5d58d;">面膜A级特权资格</p>
							</c:if>
								<p class="mui-text-center">可提现佣金</p>
							    <h3 class="good-price"><span style="font-size: 14px;">￥</span><span id="userReward">${user.mRewardMoney }</span></h3>
							    <a  id="openPopover" href="javascript:#popover-yongjin" class="yongjin-center">佣金提现</a>
							    <a href="app/mtakeout/getMyTakeOut.do" class="tixian-recond">提现记录></a>
							</li>
						</ul>
					
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-grid-view mui-grid-9 mui-mygood-order">
						<h5>我的商品订单</h5>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="app/morder/getOrderList.do?state=0" id="fukuan">
								<!--<span class="mui-icon mui-icon-home"></span>-->
								<img src="static/mobile/images/personal_daifu_icon.png" />
								<div class="mui-media-body">待付款</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="app/morder/getOrderList.do?state=1" id="fahuo">
								<img src="static/mobile/images/personal_daifa_icon.png" />
								<div class="mui-media-body">待发货</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="app/morder/getOrderList.do?state=2" id="shouhuo">
								<img src="static/mobile/images/personal_daishou_icon.png" />
								<div class="mui-media-body">待收货</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="app/morder/getOrderList.do" id="dingdan">
								<img src="static/mobile/images/personal_all_icon.png" />
								<div class="mui-media-body">全部订单</div>
							</a>
						</li>
					</ul>

					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-consume-address">
						<li class="mui-table-view-cell mui-collapse-content">
							<a href="app/mbrokerage/getList.do" class="mui-navigate-right">
								佣金明细
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content">
							<a href="app/address/getMyaddress.do" class="mui-navigate-right">
								地址管理
							</a>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-achievement">
						<li class="mui-table-view-cell mui-collapse-content">
							<h5>业绩综合&nbsp;&nbsp;<span>${user.performance }</span></h5>
						</li>
						<li class="mui-table-view-cell mui-collapse-content">
						<c:choose>
						<c:when test="${subNum > 0 }">
							<a class="mui-navigate-right" href="app/muser/myTeam.do?id=${user.id }">
								我的团队享佣
							</a>
							</c:when>
						<c:otherwise>
							<a class="mui-navigate-right" href="javascript:noSub();">
								我的团队享佣
							</a>
							</c:otherwise>
						</c:choose>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<div class="share" style="padding: 10px;">
						<a href="javascript:goShare()" class="share-btn">分享/推广</a>
					</div>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<div id="popover-yongjin" class="mui-popover">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
						<div class="mui-pop-title">请输入佣金金额，然后点击"佣金提现"按钮</div>
						<form class="mui-input-group mui-pop-form">
							<div class="mui-input-row">
								<input type="text" placeholder="请输入佣金金额" id="money">
								<div class="yongjin-btn" onclick="yjclick();">佣金提现</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<input type="hidden" value="${user.flag}" id="userFlag">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/common.js"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script>
		mui.init();
		function noSub(){
		mui.toast("您还没有团队小伙伴哦~")
		}
	jQuery("img").on("error", function () {
  jQuery(this).attr("src", "images/defult_img.png");
});
		function yjclick() {
			/* $("#popover-yongjin").hide();
			$("#popover-yongjin").removeClass("mui-active");
			$(".mui-backdrop").hide(); */
			
			//提交输入的提现金额
			var money = $("#money").val();
			if(!money){
				mui.toast("请输入金额");
				return;
			}
			if(parseFloat(money) < 100){
				mui.toast("每次提现金额必须大于100元");
				return;
			}
			if(!isPositiveInteger(money)){
				mui.toast("请输入正整数");
				return;
			}
			var yue = $("#userReward").html();
			if(yue != ''){
				var total = parseFloat(yue);
				if(total < money){
					mui.toast("佣金余额不足");
					return;
				}
				$.ajax({
				url:'${pageContext.request.contextPath}/app/mtakeout/tixianYuE.do?money='+money,
				type:'get',
				async:true,
				success:function(result){
					var data = result.data;
					if(data=='1'){
						submitForm(money);
					}else{
					mui.toast(result.msg);
					return;
					}
				}
			});
			}
			
		}
		
		//佣金提现
		function submitForm(money){
			$.ajax({
				url:'${pageContext.request.contextPath}/app/mtakeout/tixian.do?money='+money,
				type:'get',
				async:true,
				success:function(result){
					mui.toast(result.msg);
					mui("#popover-yongjin").popover("hide");
					var yue = $("#userReward").html();
					if(yue != ''){
						var total = parseFloat(yue);
						$("#userReward").html(total-money);
					}
				}
			});
		}
		
		function goShare(){
		var flag = $("#userFlag").val();
		if(flag=='0'){
			mui.toast("先购买特权才能分享给小伙伴们~");
			}else{
			var url = "app/muser/mShare.do"; 
			window.location.href=url;
		}
		}
	</script>
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
