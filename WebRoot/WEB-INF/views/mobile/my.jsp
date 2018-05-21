<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人中心</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
  <!--   <script type="text/javascript" src="test.js"></script> -->
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" href="static/mobile/css/allCss.css?w=3" />
	<link rel="stylesheet" href="static/mobile/css/phone-sp.css?k=3" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function zwkt(){
		mui.toast('暂未开通');
		}
		function noTeam(){
		mui.toast('您还没有小伙伴！');
		}
	</script>
  </head>
  
  <!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			<jsp:include page="bottom.jsp"></jsp:include>
			
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll" style="position: initial;">
					<!-- 主界面具体展示内容 -->
					<div class="my-person-title">
						<ul class="mui-table-view mui-one-center" style="background-color: #DFB354;">
							<li class="mui-table-view-cell mui-collapse-content mui-media">
							
									<img class="mui-media-object mui-pull-left" src="${user.minPhoto }" onerror="javascript:this.src='static/mobile/images/default_head.png';this.onerror=null;">
									<div class="mui-media-body" style="margin: 0px auto;">
										<%-- <div class="one-name">${user.name}&nbsp;&nbsp;
											<span class="one-member">
											<img src="static/mobile/images/personal_vip_icon.png"/>
											${user.userAmbassador }
											</span>
											<!--	<a style="display:block;background: url(http://placehold.it/20x20) top center no-repeat;"></a>-->
											<a href="vip/updateUser.do" style="float: right;">
											<img src="static/mobile/images/me_edit_btn.png">
											</a>
										</div>
										<p class="mui-ellipsis">
											<span>${user.province }</span>&nbsp;&nbsp;<span>${user.city }</span>
									    </p> --%>
									
										<div class="one-name">
											<p class="mui-ellipsis mui-left">${user.name}</p>
											<!--<span class="one-member">
												<img src="images/personal_vip_icon.png"/>
												普通会员
											</span>-->
											<!--<a style="display:block;background: url(http://placehold.it/20x20) top center no-repeat;"></a>-->
											<a href="vip/updateUser.do" class="xiugai">
												<img src="static/mobile/images/me_edit_btn.png">
												修改
											</a>
										</div>
										<p class="one-address">
											<span>${user.province }</span>&nbsp;&nbsp;<span>${user.city }</span>
										</p>
										<p class="one-member">
											<img src="static/mobile/images/personal_vip_icon.png"/>
											${user.userAmbassador }
										</p>
									</div>
							</li>
						</ul>
						<div class="mui-row mui-price">
							<div class="mui-col-sm-4 mui-col-xs-4 mui-text-center">
								<p>余额(元)</p>
								<p>${user.accountMoney }</p>
							</div>
							<div class="mui-col-sm-4 mui-col-xs-4 mui-text-center">
								<p>佣金(元)</p>
								<p id="userReward">${user.rewardMoney }</p>
							</div>
							<%-- <div class="mui-col-sm-3 mui-col-xs-3 mui-text-center">
								<p>返现(元)</p>
								<p>${user.backMoney }</p>
							</div> --%>
							<div class="mui-col-sm-4 mui-col-xs-4 mui-text-center">
								<p>积分</p>
								<p>${user.point }</p>
							</div>
						</div>
					</div>
					<div class="mui-money-lingqu mui-row">
						<div class="mui-col-sm-6 mui-col-xs-6">
							<div class="mui-text-center mui-money-fanxian">
								<a href="app/monthBack/getById.do">
									<img src="static/mobile/images/me_fanxian_icon.png" />返现领取
								</a>
							</div>
						</div>
						<div class="mui-col-sm-6 mui-col-xs-6">
							<div class="mui-text-center mui-money-tixian">
								<a href="javaScript:openOutWin()" id="openPopover">
									<img src="static/mobile/images/me_yonjin_icon.png" />佣金提现</a>
							</div>
						</div>
					</div>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-myproject-order">
						<li class="mui-table-view-cell mui-collapse-content">
							<a href="javascript:myItemOrders();" id="myItemOrders" class="mui-navigate-right">
								我的项目单
							</a>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-myproject-order">
						<li class="mui-table-view-cell mui-collapse-content">
							<a href="app/muser/goods-center.do" class="mui-navigate-right">
								商品中心
							</a>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-grid-view mui-grid-9 mui-financial-details">
						<h5>财务明细</h5>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="sys/userScore/getBalanceList.do">
								<!--<span class="mui-icon mui-icon-home"></span>-->
								<img src="static/mobile/images/personal_yue_icon.png" />
								<div class="mui-media-body">余额明细</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="sys/userScore/getBrokerageList.do">
								<img src="static/mobile/images/personal_yongjin_icon.png" />
								<div class="mui-media-body">佣金明细</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="javascript:scoreList();" id="scoreList">
								<img src="static/mobile/images/personal_jifen_icon.png" />
								<div class="mui-media-body">积分明细</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-3 mui-col-sm-3">
							<a href="javascript:returndetail();">
								<img src="static/mobile/images/personal_fanxian_icon.png" />
								<div class="mui-media-body">返现明细</div>
							</a>
						</li>
					</ul>
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-consume-address">
						<li class="mui-table-view-cell mui-collapse-content">
							<a href="javascript:recordList();" id="recordList" class="mui-navigate-right">
								消费记录
							</a>
						</li>
<!-- 						<li class="mui-table-view-cell mui-collapse-content">
							<a href="javascript:zwkt()" class="mui-navigate-right">
								地址管理
							</a>
						</li> -->
					</ul>
					<div class="mui-full-back"></div>
					
					<c:if test="${user.userType=='2' }">
						<ul class="mui-table-view mui-boss-function">
							<li class="mui-table-view-cell mui-collapse-content">
								<h5>主管功能</h5>
							</li>
							<li class="mui-table-view-cell">
								<a href="javascript:openPriceConfirm();" class="mui-navigate-right">
									价格确认
								</a>
							</li>
							<li class="mui-table-view-cell mui-collapse-content">
								<a href="javascript:checkList();" id="checkList" class="mui-navigate-right">
									提现审核
								</a>
							</li>
						</ul>
					</c:if>
					
					<div class="mui-full-back"></div>
					<ul class="mui-table-view mui-achievement">
						<li class="mui-table-view-cell mui-collapse-content">
							<h5>业绩综合&nbsp;&nbsp;<span>${user.performance }</span></h5>
						</li>
						<li class="mui-table-view-cell mui-collapse-content">
						<c:choose>
   							<c:when test="${sbNub >0}">
   								<a class="mui-navigate-right" href="vip/Subordinate.do ">我的推荐口碑营销
   							</c:when>
   					<c:otherwise> <a class="mui-navigate-right" href="javascript:noTeam()">我的推荐口碑营销
   						</c:otherwise>
					</c:choose>
								<span class="person-number">${sbNub }个小伙伴</span> </a>
						</li>
					</ul>
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
	 <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js"></script>
    <script type="text/javascript" src="static/mobile/js/common.js"></script>
	<script>
		mui.init();

		document.getElementById("fukuan").addEventListener('tap', function() {
			mui.toast('暂未开通');
		});
		document.getElementById("fahuo").addEventListener('tap', function() {
			mui.toast('暂未开通');
		});
		document.getElementById("shouhuo").addEventListener('tap', function() {
			mui.toast('暂未开通');
		});
		document.getElementById("dingdan").addEventListener('tap', function() {
			mui.toast('暂未开通');
		});
		function yjclick() {
			//$("#popover-yongjin").hide();
			//$("#popover-yongjin").removeClass("mui-active");
			//$(".mui-backdrop").hide();
			
			//提交输入的提现金额
			var money = $("#money").val();
			if(!money){
				mui.toast("请输入金额");
				return;
			}
			if(!isPositiveInteger(money)){
				mui.toast("请输入正整数");
				return;
			}
			if(parseFloat(money) < 100){
				mui.toast("每次提现金额必须大于100元");
				return;
			}
			var yue = $("#userReward").html();
			if(yue != ''){
				var total = parseFloat(yue);
				if(total < money){
					mui.toast("佣金余额不足");
					return;
				}
			}
			submitForm(money);
			
		}
		
		//价格确认
		function openPriceConfirm(){
			window.location.href = "${pageContext.request.contextPath}/weixin/item/priceConfirm.do";
		}
		
		//佣金提现
		function submitForm(money){
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/reward/withdrawDepositByReward.do?money='+money,
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
		
		//我的项目订单
		/* mui('body').on('tap', '#myItemOrders', function() {
			myItemOrders();
		}); */
		function myItemOrders(){
			var url = "${pageContext.request.contextPath}/weixin/order/getItemOrderByUser.do";
			window.location.href=url;
		}
		
		//消费记录
		/* mui('body').on('tap', '#recordList', function() {
			recordList();
		}); */
		function recordList(){
			var url = "${pageContext.request.contextPath}/weixin/order/getRecordList.do";
			window.location.href=url;
		}
		
		//提现审核
		/* mui('body').on('tap', '#checkList', function() {
			checkList();
		}); */
		function checkList(){
			var url = "${pageContext.request.contextPath}/weixin/manage/getALL.do";
			window.location.href=url;
		}
		
		//积分明细
		/* mui('body').on('tap', '#scoreList', function() {
			scoreList();
		}); */
		function scoreList(){
			var url = "${pageContext.request.contextPath}/sys/userScore/getScoreList.do";
			window.location.href=url;
		}
		
		function openOutWin(){
			$("#money").val("");
			mui("#popover-yongjin").popover("toggle");
		}
		
		//返现明细
		function returndetail(){
			var url = "${pageContext.request.contextPath}/sys/userScore/getReturnList.do";
			window.location.href=url;
		}
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/minghan.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
