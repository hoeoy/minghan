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
	<title>项目详情</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>
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
													<img src="${imgs[fn:length(imgs)].itemImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
												</a>
											</div>
										</c:if>
										<div class="mui-slider-item mui-slider-item-duplicate">
											<a href="javascript:void(0)">
												<img src="${img.itemImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
											</a>
										</div>
										<c:if test="${advertStatus.count == fn:length(imgs) }">
											<div class="mui-slider-item mui-slider-item-duplicate">
												<a href="javascript:void(0)">
													<img src="${imgs[0].itemImage}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;" />
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
							
							<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
							<!-- <div class="mui-slider-item mui-slider-item-duplicate">
								<a href="javascript:void(0)">
									<img src="static/mobile/images/defult_img.png">
								</a>
							</div> -->
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
							<p class="taocan-name">${item.name}</p>
						   </div>
							
						   <div class="mui-col-sm-3 mui-col-xs-3 mui-text-right">
							<p class="selled">已售:${item.saleCount}</p>
							</div>
							<div class="mui-col-sm-8 mui-col-xs-8">
								<%-- <p class="taocan-price">¥<span class="taocan-money">${item.currentPrice}</span><span class="taocan-priced">原价:${item.oldPrice}</span></p> --%>
								<p class="taocan-price">¥<span class="taocan-money">${item.currentMin}-${item.currentMax}</span></p>
							</div>
							<div class="mui-col-sm-4 mui-col-xs-4 mui-text-right">
							<div class="mui-input-group add-odd mui-right">
								<input type="button" class="addon icon-minus" id="del" value="-"/>
								<input type="number" class="input" id="amount" name="amount" value="1"/>
								<input type="button" class="addon icon-plus" id="add" value="+"/>
								<!--<span class="addon text-red">¥<span id="totalMoney">235.00</span>元</span>-->
								<input type="hidden" value="${item.currentPrice}" name="money" id="money" />
							</div>
							</div>
						</div>
					
					</div>
					<%-- <ul class="mui-table-view mui-view-mendian">
						<li class="mui-table-view-cell mui-collapse-content">
							<a class="mui-navigate-right" href="javascript:shopList();" id="shopLine">
								<span class="mui-left" id="showShopName">
									<img src="static/mobile/images/project_stores_icon.png" class="mendian-img"/>
								适用门店</span>
								<p style="display: none;" id="shopName">${shop.shopName}</p>
								<p style="display: none;" id="shopId">${shop.id}</p>
								<span class="mui-right" id="numOfShop">共0个</span>
							</a>
						</li>
					</ul> --%>
					<div class="meal-consum">
						<h4>项目内容</h4>
						<p>${item.content}</p>
						<div class="hetong-btn">
							<%-- <a href="${pageContext.request.contextPath}/weixin/item/getComboContratsByComboId.do?id=${item.id}">查看合同</a> --%>
						</div>
					</div>
				</div>
			</div>
			<nav class="mui-bar mui-bar-tab mui-goumai">
				<div class="total-price">
					售价<span>¥<span id="totalMoney">${item.currentPrice}</span></span>
				</div>
				<div class="mui-tab-item mui-zhifu-btn">
					<!-- <a href="javascript:own11();" id="buy" >立即购买</a> -->
					<a href="javascript:payNow()" id="buy">立即购买</a>
					<p style="display:none;" id=itemId>${item.id}</p>
				</div>
			</nav>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
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
			mui.toast("美容项目每次限购1份");
			/*
			var num_add = parseInt($("#amount").val()) + 1;
			if($("#amount").val() == "") {
				num_add = 1;
			}
			$("#amount").val(num_add);
			var total = parseFloat($("#money").val()) * parseInt($("#amount").val());
			$("#totalMoney").html(total.toFixed(2));
			*/
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
		//提交数据
		/*function sub_with(itemid) {
			parent.location.href = "index.html";
		}*/
		
		
		//点击立即购买
		/* mui('body').on('tap', '#buy', function() {
			own11();
		}); */
		function payNow(){
			/* //不选择门店不能点击购买
			var name = $("#showShopName").html();
			var strList = name.split(">");
			//alert(strList[1]);
			if("适用门店" == strList[1].trim()){
				mui.toast("请先选择适用门店");
				return;
			} */
			var id = $("#itemId").html();
			/* var shopId = $("#shopId").html();
			var url = "${pageContext.request.contextPath}/weixin/item/buyItem.do?id="+id+"&shopId="+shopId; */
			var url = "${pageContext.request.contextPath}/weixin/item/buyItem.do?id="+id;
			window.location.href=url;
		}
		
		//门店
		//mui('body').on('tap', '#shopLine', function() {
		//	shopList();
		//});
		function shopList(){
			var itemId = $("#itemId").html();
			var url = "${pageContext.request.contextPath}/weixin/shop/getShopsByItemId.do?id="+itemId;
			window.location.href=url;
		}
		
		function warning(){
			mui.toast("暂无门店提供该产品");
		}
		
		$(document).ready(function(){
			//获取门店数量
			/* var url = "${pageContext.request.contextPath}/weixin/shop/getItemNumOfShop.do?id=${item.id}";
			$.getJSON(url, function(result){
				var num = result.data;
				$("#numOfShop").html("共"+num+"个");
				if(num < 1){//门店数量为0时不能购买
					$("#shopLine").attr("href", "javascript:void(0)");
					$("#buy").attr("href", "javascript:warning()");
				}else{//选择门店
					var shopName = $("#shopName").html();
					//alert(shopName);
					if(shopName != ""){
						var str = '<img src="static/mobile/images/project_stores_icon.png" class="mendian-img"/>' + shopName;
						$("#showShopName").empty();
						$("#showShopName").html(str);
					}
				}
			}); */
		});
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js?v=1"></script>
</body>
</html>
