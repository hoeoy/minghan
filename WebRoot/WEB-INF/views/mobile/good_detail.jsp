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
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/goodCss.css"/>
  </head>
  
<body>
	<!-- 主界面菜单同时移动 -->
	<!-- 侧滑导航根容器 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper mui-detail">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<div id="slider" class="mui-slider mui-meal-detail">
						<div class="mui-slider-group mui-slider-loop">
							<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
							<div class="mui-slider-item mui-slider-item-duplicate">
								<a href="javascript:void(0)">
									<img src="static/mobile/images/defult_img.png">
								</a>
							</div>
							
							<c:choose>
								<c:when test="${imgs != null && fn:length(imgs)>0}">
									<c:forEach items="${imgs}" var="img" varStatus="advertStatus">
										<c:if test="${advertStatus.count == 0 }">
											<div class="mui-slider-item mui-slider-item-duplicate" id="top">
												<a href="javascript:void(0)">
													<img src="${imgs[fn:length(imgs)].goodsImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
												</a>
											</div>
										</c:if>
										<div class="mui-slider-item mui-slider-item-duplicate">
											<a href="javascript:void(0)">
												<img src="${img.goodsImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
											</a>
										</div>
										<c:if test="${advertStatus.count == fn:length(imgs) }">
											<div class="mui-slider-item mui-slider-item-duplicate">
												<a href="javascript:void(0)">
													<img src="${imgs[0].goodsImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
												</a>
											</div>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div class="mui-slider-item">
										<a href="javascript:void(0)">
											<img src="static/mobile/images/defult_img.png">
										</a>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="mui-slider-indicator">
							<c:if test="${imgs != null && fn:length(imgs)>0}">
								<c:forEach items="${imgs}" var="image" varStatus="status">
									<c:choose>
										<c:when test="${status.first}">
											<div class="mui-indicator mui-active"></div>
										</c:when>
										<c:otherwise>
											<div class="mui-indicator"></div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</div>
						
						<div class="mui-row mui-taocan">
					  	  	<div class="mui-col-sm-9 mui-col-xs-9">
								<p class="taocan-name">${goods.name }</p>
							</div>
							<div class="mui-col-sm-3 mui-col-xs-3 mui-text-right">
								<p class="selled">已售:${goods.sellNum }</p>
							
							</div>
							<div class="mui-col-sm-8 mui-col-xs-8">
								<p class="taocan-price">¥<span class="taocan-money">${goods.price }</span></p>
							</div>
							<div class="mui-col-sm-4 mui-col-xs-4 mui-text-right">
							<div class="mui-input-group add-odd mui-right">
								<input type="button" class="addon icon-minus" id="del" value="-"/>
								<input type="number" class="input" id="amount" name="amount" value="1"/>
								<input type="button" class="addon icon-plus" id="add" value="+"/>
								<!--<span class="addon text-red">¥<span id="totalMoney">235.00</span>元</span>-->
								<input type="hidden" value="${goods.price }" name="money" id="money" />
							</div>
							</div>
						</div>
					
					</div>
					<div class="mui-full-back"></div>
						<div class="meal-consum">
							<h4>商品详情</h4>
							<div class="line-full"></div>
							<p class="content">${goods.description }</p>

					</div>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab mui-goumai">
				<div class="total-price">
					售价<span>¥<span id="totalMoney">${goods.price }</span></span>
				</div>
				<div class="mui-tab-item mui-zhifu-btn">
					<!-- <a href="javascript:own11();" id="buy" >立即购买</a> -->
					<a href="javascript:payNow()" id="buy">立即购买</a>
					<p style="display:none;" id="goodsId">${goods.id}</p>
				</div>
			</nav>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<input type="hidden" id = "userFlag" value="${flag }">
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	
	<script>
		mui.init();
		$(function() {
			/*$("img").on("error", function () {
		  		$(this).attr("src", "static/mobile/images/defult_img.png");
			});*/
			$("#amount").keyup(function() {
				if(isNaN($(this).val()) || parseInt($(this).val()) < 1) {
					$(this).val("1");
					$("#totalMoney").html($("#money").val());
					return;
				}
				if(parseInt($(this).val()) > 100) {
					$(this).val("100");
					var total = parseFloat($("#money").val()) * parseInt($(this).val());
					$("#totalMoney").html(total.toFixed(2));
					return;
				}
				var total = parseFloat($("#money").val()) * parseInt($(this).val());
				$("#totalMoney").html(total.toFixed(2));
			});
		});
		/*数量+1*/
		function numAdd() {
			/* mui.toast("美容项目每次限购1份"); */
			
			var num_add = parseInt($("#amount").val()) + 1;
			if($("#amount").val() == "") {
				num_add = 1;
			}
			$("#amount").val(num_add);
			var total = parseFloat($("#money").val()) * parseInt($("#amount").val());
			$("#totalMoney").html(total.toFixed(2));
			
		}
		/*数量-1*/
		function numDec() {
			var num_dec = parseInt($("#amount").val()) - 1;
			if(num_dec < 1) {
				mui.toast("最少1份");
			} else {
				$("#amount").val(num_dec);
				var total = parseFloat($("#money").val()) * parseInt($("#amount").val());
				$("#totalMoney").html(total.toFixed(2));
			}
		}
		$(function() {
			//增加份数
			$('#add').click(function() {
				numAdd();
			});
			//减少份数
			$('#del').click(function() {
				numDec();
			});
		});
		function payNow(){
			var flag = $("#userFlag").val();
			if(flag=='0'){
			mui.toast("请先购买特权再进行商品购买~");
			}else{
			var id = $("#goodsId").html();
			var num = $("#amount").val();
			var url = "app/mgoods/good_payment.do?id="+id+"&num="+num;
			window.location.href=url;
			}
		}

	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js?v=1"></script>
</body>
</html>
