<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>待付款</title>
    
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
		.order-detail-name {
    margin: 3px auto;
    color: #333;
    font-size: 15px;
    white-space: normal;
}
	</style>
  </head>
  
  <body>
    <!-- 主界面菜单同时移动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper" id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<div id="order-list">
					<ul class="mui-table-view order-detail">
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-fahuo-title">待付款</p>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<a href="app/morder/getOrderDetail.do">
							<img class="mui-media-object mui-pull-left" src="static/mobile/images/defult_img.png">
							<div class="mui-media-body mui-row">
								<div class="mui-col-sm-10 mui-col-xs-10">
									<p class="order-detail-name">5100无添加冰泉水润舒缓面膜男女深层补水保湿控油面膜5片装</p>
									<p class="order-detail-price">¥118<span>¥226.53</span></p>
								</div>
								<div class="mui-col-sm-2 mui-col-xs-2">
									<p class="mui-right order-detail-name">×1</p>
								</div>
							</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-fahuo-title">
								<span class="mui-black-color mui-right mui-font-size">共1件商品&nbsp;合计：¥118.00</span>
							</p>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-right mui-fahuo-title">
								<a class="order-cancel">取消订单</a>
								<a href="javascript:#popover-tequan" class="order-payment">去支付</a>
							</p>
						</li>
						<!-- <li class="mui-table-view-cell mui-collapse-content mui-media">
							<button class="all-order-btn mui-right red-background">确认收货</button>
								<button class="all-order-btn mui-right all-border" onclick="window.location.href='look-wuliu.html'">查看物流</button>	
						</li> -->
					</ul>
					<div class="mui-full-back"></div>
				    </div>
					<div class="order-foot-refresh"><span class="order-foot-flag">做人是要有底线滴</span></div>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
		<div id="popover-tequan" class="mui-popover">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll fukuang-method">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell mui-collapse-content mui-text-center">
							<span class="mui-left" style="vertical-align: bottom;font-size:22px;" onclick="payclick()">×</span>
							<span style="text-align: center;line-height: 25px;">选择支付方式</span>
						</li>
			
						<form class="mui-input-group">
							<div class="mui-input-row mui-checkbox ">
								<label><img src="static/mobile/images/weixin_icon.png" />微信支付(¥<span>0.00</span>)</label>
								<input name="Checkbox" type="checkbox" checked>
							</div>

						</form>
					
						<li class="mui-table-view-cell mui-collapse-content ">
							<span class="mui-left zhifu">需支付</span>
							<span class="mui-right zhifu-money">¥3000</span>
						</li>
					</ul>
					<a href="javascript:payclick();" onclick="payclick();" class="pop-queren-pay">确认支付</a>
				</div>
			</div>
		</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init();
		function payclick() {
				$("#popover-tequan").hide();
				$("#popover-tequan").removeClass("mui-active");
				$(".mui-backdrop").hide();
			}
			</script>
			<script type="text/javascript">
		var page = 1;//第一页
		var totalPage = ${allPage};//总页数
		var url = "sys/order/.....";
		mui.init({
			    pullRefresh: {
			        container: '#pullrefresh',
			        up: {//上拉加载
			            contentrefresh: '正在加载...',
			            contentnomore:"<p>做人是要有底线滴</p>",//可选，请求完毕若没有更多数据时显示的提醒内容；
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
			            if(data.money!=0){
			            	var html ="<ul class='mui-table-view order-detail'>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<p class='mui-red-color mui-fahuo-title'>待付款</p>"+
			            	"</li>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<a href='orderDetail-obligation.html'>"+
			            	"<img class='mui-media-object mui-pull-left' src='static/mobile/images/defult_img.png'>"+
			            	"<div class='mui-media-body mui-row'>"+
			            	"<div class='mui-col-sm-10 mui-col-xs-10'>"+
			            	"<p class='order-detail-name'>5100无添加冰泉水润舒缓面膜男女深层补水保湿控油面膜5片装</p>"+
			            	"<p class='order-detail-price'>¥118<span>¥226.53</span></p>"+
			            	"</div><div class='mui-col-sm-2 mui-col-xs-2'>"+
			            	"<p class='mui-right order-detail-name'>×1</p>"+
			            	"</div></div></a></li>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<p class='mui-fahuo-title'>"+
			            	"<span class='mui-black-color mui-right mui-font-size'>共1件商品&nbsp;合计：¥118.00</span>"+
			            	"</p></li>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<p class='mui-red-color mui-right mui-fahuo-title'>"+
			            	"<a class='order-cancel'>取消订单</a>"+
			            	"<a href='javascript:#popover-tequan' class='order-payment'>去支付</a>"+
			            	"</p></li></ul>"+
			            	"<div class='mui-full-back'></div>";
			            	$("#order-list").append(html);
			                      //mui.currentWebview.show(); //显示当前页面
			            }
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
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
