<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//不从缓存中读取数据
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>适用门店列表</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet"  href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css"/>

  </head>
  
<body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			
			<!-- 主界面具体展示内容 -->
			<ul class="mui-table-view mui-mendian-list">
				
				<c:if test="${shops != null && fn:length(shops)>0 }">
					<c:forEach items="${shops}" var="shop">
						<li class="mui-table-view-cell mui-media">
							<img class="mui-media-object mui-pull-left" src="${shop.shopLogo}">
							<div class="mui-media-body">
								<a href="javascript:goBack('${shop.id}');" class="chooseShop">
									<p>${shop.shopName}</p>
									<p class="mui-ellipsis">${shop.shopAddress}</p>
								</a>
								<p style="display: none;">${shop.id}</p>
							</div>
							<div class="mui-right">
								<a href="javascript:callTelFun('${shop.id}');"><img src="static/mobile/images/contact_icon_phone.png"/></a>
								<a href="javascript:navigation('${shop.shopName}', '${shop.longitude}', '${shop.latitude}', '${shop.shopAddress}');">
									<img src="static/mobile/images/contact_icon_adress.png" />
								</a>
							</div>
						</li>
					</c:forEach>
				</c:if>
				
			</ul>
			<p style="display: none;" id="itemId">${itemId}</p>

		</div>
	</div>
	<div class="mui-off-canvas-backdrop"></div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script>
		mui.init();
					jQuery("img").on("error", function () {
		  jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
		
		var shopId = null;
		$(document).ready(function(){
		});
		
		function goBack(e){
			var itemId = $("#itemId").html();
			var url = "${pageContext.request.contextPath}/weixin/item/getItemById.do?id="+itemId+"&shopId="+e;
			window.location.href = url;
		}
		
		//导航
		function navigation(title, longitude, latitude, address){
			if(longitude == null || latitude == null || longitude == "" || latitude == ""){
				mui.toast("获取门店位置失败");
				return;
			}
			var url = "http://apis.map.qq.com/uri/v1/marker?marker=coord:"+longitude+","+latitude+";title:"+title+";addr:"+address+"&referer=minghanyimei";
			window.location.href = url;
		}
		
		//电话
		function callTelFun(shopId){
			var btnArray=['取消','确定'];
	        $.ajax({
			    url:'${pageContext.request.contextPath}/weixin/shop/getShopTel.do?shopId=' + shopId,
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    success:function(data){
			    	if(data.code == 0){
			    		var phone=data.data;
				       // mui.confirm('确定拨打电话：'+phone+'吗?','提示',btnArray,function(e){
				       //     if(e.index == 1){
				            	//plus.device.dial(phone);
				            	window.location.href = 'tel:'+phone;
				        //    }
				       // });
			    		return;
			    	}else{
			    		mui.toast(data.msg);
			    	}
			    	//alert(data.msg);
			    }
			});
	    }

	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
</body>
</html>
