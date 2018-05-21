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
    
    <title>订单列表</title>
    
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
					<c:if test="${fn:length(list)==0 }">
					<p class="mui-text-center" style="margin: 20px auto;line-height:30px;">---暂无内容---</p>
					</c:if>
					<div id="allOrder-list">
					<c:forEach items="${list}" var="list">
					<ul class="mui-table-view order-detail">
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-fahuo-title">${list.orderState }</p>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<a href="app/morder/getOrderDetail.do?id=${list.id }">
							<img class="mui-media-object mui-pull-left" src="${list.goodsLogo}">
							<div class="mui-media-body mui-row">
								<div class="mui-col-sm-10 mui-col-xs-10">
									<p class="order-detail-name">${list.goodsName}</p>
									<p class="order-detail-price">¥${list.goodsPrice}<span>¥${list.goodsMarket}</span></p>
								</div>
								<div class="mui-col-sm-2 mui-col-xs-2">
									<p class="mui-right order-detail-name">×${list.goodsNum}</p>
								</div>
							</div>
							</a>
						</li>
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-fahuo-title">
								<span class="mui-black-color mui-right mui-font-size">共${list.goodsNum }件商品&nbsp;合计：¥${list.goodsAllPrice }</span>
							</p>
						</li>
						<c:if test="${list.orderState eq '待付款' }">
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-right mui-fahuo-title">
								<a href="app/morder/exitOrder.do?id=${list.id }" class="order-cancel">取消订单</a>
								<a href="javascript:goPay('${list.id}');" class="order-payment" >去支付</a>
							</p>
						</li>
						</c:if>
						<c:if test="${list.orderState eq '待收货' }">
						<li class="mui-table-view-cell mui-collapse-content mui-media">
							<p class="mui-red-color mui-right mui-fahuo-title">
								<a href="app/morder/shouhuo.do?id=${list.id }" class="received">确认收货</a>
							</p>
						</li>
						</c:if>
					</ul>
					<div class="mui-full-back"></div>
					</c:forEach>
					
					</div>
				<!-- 	<div class="order-foot-refresh"><span class="order-foot-flag">做人是要有底线滴</span></div> -->
					
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
							<span class="mui-left" style="vertical-align: bottom;font-size:22px;" onclick="clickpay();">×</span>
							<span style="text-align: center;line-height: 25px;">选择支付方式</span>
						</li>
			
						<form class="mui-input-group">
							<div class="mui-input-row mui-checkbox ">
								<label><img src="static/mobile/images/weixin_icon.png" />微信支付(¥<span id="wx_zhifu">0.00</span>)</label>
								<input name="Checkbox" type="checkbox" checked>
							</div>

						</form>
					
						<li class="mui-table-view-cell mui-collapse-content">
							<span class="mui-left zhifu">需支付</span>
							<span class="mui-right zhifu-money">¥<span id="xu_zhifu"></span></span>
						</li>
					</ul>
					<a href="javascript:pay();" class="pop-queren-pay">确认支付</a>
				</div>
			</div>
		</div>
		<input type="hidden" id ="state" value="${state}">
		<input type="hidden" id = "orderId" > 
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init();
		function clickpay(){
		        $("#popover-tequan").hide();
				$("#popover-tequan").removeClass("mui-active");
				$(".mui-backdrop").hide();
		}
		function goPay(id){
		$.ajax({
		url:"app/morder/getOrder.do?id="+id,
		type:"GET",
		 dataType: 'json',
		 success:function(result){
		 var data = result.data;
		 $("#wx_zhifu").html(data);
		 $("#xu_zhifu").html(data);
		 $("#orderId").val(id);
		mui("#popover-tequan").popover("toggle");
		 }
		});
		}
		var prepay_id;
			var paySign;
			var appId;
			var timeStamp;
			var nonceStr;
			var packageStr;
			var signType;
			var orderNo;
		function pay() {
		var orderId = $("#orderId").val();
				$.ajax({
					url : "app/pay/wxDZFOrder.do",
					type : 'POST',
					dataType : 'json',
					data:{"orderId":orderId},
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
				$("#popover-tequan").hide();
				$("#popover-tequan").removeClass("mui-active");
				$(".mui-backdrop").hide();
				
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
	<script type="text/javascript">	
		var page = 1;//第一页
		var totalPage = ${allPage};//总页数
		var state=$("#state").val();//订单状态
		var url = "app/morder/orderBask.do";
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
		            page:page,
		            state:state
		           
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
		            page:page,
		            state:state
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
			            	"<p class='mui-red-color mui-fahuo-title'>"+data[i].orderState+"</p>"+
			            	"</li>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<a href='app/morder/getOrderDetail.do?id="+data[i].id+"'>"+
			            	"<img class='mui-media-object mui-pull-left' src='"+data[i].goodsLogo+"'>"+
			            	"<div class='mui-media-body mui-row'>"+
			            	"<div class='mui-col-sm-10 mui-col-xs-10'>"+
			            	"<p class='order-detail-name'>"+data[i].goodsName+"</p>"+
			            	"<p class='order-detail-price'>¥"+data[i].goodsPrice+"<span>¥"+data[i].goodsMarket+"</span></p>"+
			            	"</div><div class='mui-col-sm-2 mui-col-xs-2'>"+
			            	"<p class='mui-right order-detail-name'>×"+data[i].goodsNum+"</p>"+
			            	"</div></div></a></li>"+
			            	"<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<p class='mui-fahuo-title'>"+
			            	"<span class='mui-black-color mui-right mui-font-size'>共"+data[i].goodsNum+"件商品&nbsp;合计：¥"+data[i].goodsAllPrice +"</span>"+
			            	"</p></li>";
			
			            	if(data[i].orderState == "待付款"){
			            	html+="<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
			            	"<p class='mui-red-color mui-right mui-fahuo-title'>"+
			            	"<a href='app/morder/exitOrder.do?id="+data[i].id+"' class='order-cancel'>取消订单</a>"+
			            	"<a href='javascript:goPay('"+data[i].id+"')' class='order-payment'>去支付</a>"+
			            	"</p></li>"
						    }

						    if(data[i].orderState == "待收货"){
							html+="<li class='mui-table-view-cell mui-collapse-content mui-media'>"+
							"<p class='mui-red-color mui-right mui-fahuo-title'>"+
							"<a href='app/morder/shouhuo.do?id="+data[i].id+"' class='received'>确认收货</a>"+
							"</p></li>"
							}
						    html+="</ul>"+
			            	"<div class='mui-full-back'></div>";
			            	$("#allOrder-list").append(html);
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
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
</body>
</html>
