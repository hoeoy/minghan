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
    
    <title>地址管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.min.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.picker.min.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
		<!--<script>
		window.FastClick = true;
	</script>-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.poppicker.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.picker.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/data.city.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.enterfocus.js"></script>
  </head>
  
  <body>
	<!-- 主界面移动、菜单不动 -->
		<!--<div class="mui-off-canvas-wrap mui-draggable">-->
	<!-- 主页面容器 -->
	<div class="mui-inner-wrap">
		<!-- 主页面标题 -->
		<!--	<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">修改资料</h1>
</header>-->
		<div class="mui-content mui-scroll-wrapper" style="background-color: #f7f7f7;">
			<div class="mui-scroll">
				<!-- 主界面具体展示内容 -->
				<form action="app/address/saveAddress.do" class="mui-input-group mui-create-address" method="post" accept-charset="utf-8">
					<input type="hidden" value = "${addr.id }" name = "id">
					<div class="mui-input-row">
						<label>收货人姓名</label>
						<input name="linkman"  type="text" class="mui-input-clear" required="required" value="${addr.linkman }"/>
					</div>
					<div class="mui-input-row">
						<label>联系电话</label>
						<input name="mobile" type="tel" class="mui-input-clear" required="required"  value = "${addr.mobile }"/>
					</div>
					
					<div class="mui-input-row">
						<label>省、市、区</label>
						<span class="mui-icon mui-icon-arrowright"></span>
						<c:if  test="${addr.id !=null&&addr.id !='' }"><input name="pcz" type="text" id="adrress_city" class="mui-city" required="required" readonly="readonly" onfocus="this.blur();" value="${addr.province }-${addr.city }-${addr.zone }"/></c:if>
						<c:if  test="${addr.id ==null||addr.id =='' }"><input name="pcz" type="text" id="adrress_city" class="mui-city" required="required" readonly="readonly" onfocus="this.blur();" /></c:if>
					</div>
					<div class="mui-input-row">
						<label>详细地址</label>
						<input name="addr"  type="text" class="mui-input-clear" required="required" value = "${addr.addr }"/>
					</div>
					<div class="baocun-btn">
					<button class="edit-commit" type="submit">保存</button>
				    </div>
				</form>
				
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mui.init();
		//时间选择就不多说了，mui框架自带，重点是城市
		//选择省市区
		var city_picker = new mui.PopPicker({
			layer: 3,
		});
		city_picker.setData(init_city_picker);
		$("#adrress_city").on("tap", function() {
			city_picker.show(function(items) {
				$("#adrress_city").val((items[0] || {}).text + "-" + (items[1] || {}).text + "-" + (items[2] || {}).text); //该ID为接收城市ID字段
				/*$("#city_text").html((items[0] || {}).text + "" + (items[1] || {}).text + "" + (items[2] || {}).text);*/
			});
		});
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js" ></script>
</body>
</html>
