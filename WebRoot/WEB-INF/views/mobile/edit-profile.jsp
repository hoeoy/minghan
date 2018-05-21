<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    <title>修改资料</title>	
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.min.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.picker.min.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
	<script>
		window.FastClick = true;
	</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.poppicker.js" ></script>
	<script type="text/javascript" src="static/mobile/js/mui.picker.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/data.city.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.enterfocus.js" ></script>
	<style>
.edit-profile-btn {
	text-align: center;
	margin: 20px 10px;
}
	</style>
  </head>
  <body>
	<!-- 主界面移动、菜单不动 -->
<!--	<div class="mui-off-canvas-wrap mui-draggable">-->
	<!-- 主页面容器 -->
	<div class="mui-inner-wrap">
		<!-- 主页面标题 -->
		<!--	<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">修改资料</h1>
</header>-->
		<div class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!-- 主界面具体展示内容 -->
				<form class="mui-input-group mui-edit-profile" action="vip/updateUserCommit"  accept-charset="UTF-8" method="post">
					<div class="mui-input-row">
						<label>姓名</label>
						<c:if test="${user.updateFlagName == '1'}">
						<input type="text" class="mui-input-clear" required="required" placeholder="请输入真实姓名(必填项)" value="${user.name }" name="name"/>
						</c:if>
						<c:if test="${user.updateFlagName == '0'}">
						<input type="text" class="mui-input-clear" required="required" placeholder="请输入真实姓名(必填项)" value="" name="name"/>
						</c:if>
					</div>
					<div class="mui-input-row">
						<label>手机号</label>
						<input type="text" class="mui-input-clear" required="required" placeholder="请输入手机号(必填项)" value="${user.mobile }" name="mobile"/>
					</div>
					<div class="mui-input-row">
						<label id="useData">生日</label>
						<input type="text" id="useData_id" class="mui-input-clear" placeholder="请选择出生日期" readonly="readonly"  onfocus="this.blur();" value="${user.birthday }" name="birthday"/>
					</div>
					<div class="mui-input-row">
						<label>身份证</label>
						<input type="text" class="mui-input-clear" placeholder="请输入身份证(可选项)"  value="${user.card }" name="card"/>
					</div>
					<div class="mui-input-row">
						<label>邮箱</label>
						<input type="text" class="mui-input-clear" placeholder="请输入邮箱(可选项)"value="${user.mail }" name="mail" />
					</div>
					<div class="mui-input-row">
						<label>所在地</label>
						<input type="text" id="city_id" class="mui-input-clear" placeholder="请选择省市区" readonly="readonly" onfocus="this.blur();" value="${user.province }-${user.city}-${user.zone}" name="city"/>
					</div>
					<div class="mui-input-row">
						<label>详细地址</label>
						<input type="text" class="mui-input-clear" placeholder="请输入详细地址(可选项)" value="${user.addr }"  name="addr"/>
					</div>
					<div class="mui-input-row">
						<label>银行</label>
						<a href="#popover-bank" style="position: absolute;" id="pop-click">
							<input type="text" id="bank" class="mui-input-clear" required="required" placeholder="请选择开户行(必填项)" readonly="readonly" onfocus="this.blur();" value="${user.bankType }" name="bankType"/> </a>
					</div>
					<div class="mui-input-row">
						<label>账户</label>
						<input type="text" class="mui-input-clear" required="required" placeholder="银行账户(必填项)" value="${user.bankCard }"  name="bankCard"/>
					</div>
					<div class="edit-profile-btn-first">
					<button class="edit-commit" type="submit">提交</button>
					</div>
				</form>
				<div class="edit-profile-btn">
					<p>姓名和身份证一经填写无法进行第二次修改，请核对好信息再提交</p>
				</div>
			</div>
		</div>
		<!--<div class="mui-off-canvas-backdrop"></div>-->
	</div>
	<!--</div>-->
	<div id="popover-bank" class="mui-popover mui-bank-pop">
		<div class="mui-scroll-wrapper" style="margin: 10px auto;">
			<div class="mui-scroll">
			    <div class="mui-input-row mui-radio">
					<label>农业银行</label>
					<input name="radio" type="radio" value="农业银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio ">
					<label>工商银行</label>
					<input name="radio" type="radio" value="工商银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>建设银行</label>
					<input name="radio" type="radio" value="建设银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>中国银行</label>
					<input name="radio" type="radio" value="中国银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>中信银行</label>
					<input name="radio" type="radio" value="中信银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>交通银行</label>
					<input name="radio" type="radio" value="交通银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio ">
					<label>招商银行</label>
					<input name="radio" type="radio" value="招商银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>浦发银行</label>
					<input name="radio" type="radio" value="浦发银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio">
					<label>邮政储蓄银行</label>
					<input name="radio" type="radio" value="邮政储蓄银行" onclick="setvalue(this.value);">
				</div>
				<div class="mui-input-row mui-radio ">
					<label>民生银行</label>
					<input name="radio" type="radio" value="民生银行" onclick="setvalue(this.value);">
				</div>
			</div>
			
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>	
	<script type="text/javascript">
	mui.init();
	function setvalue(val) {
		document.getElementById("bank").value = val;
		$("#popover-bank").hide();
		$(".mui-backdrop").hide();
	}
	//时间选择就不多说了，mui框架自带，重点是城市
	var year=new Date().getFullYear() ; //年
var  month=new Date().getMonth()+1; //月
var day=new Date().getDate();//日
	var start_time_picker = new mui.DtPicker({"type":"date","beginYear":1917,"endYear":year,"endMonth":month,"endDay":day});
	/*var start_time_picker = new mui.DtPicker({
		type: "date", //设置日历初始视图模式 
		beginDate: "", //设置开始日期 
		endDate: "", //设置结束日期 
		labels: ['Year', 'Mon', 'Day'],//设置默认标签区域提示语 
	});*/
	$("#useData_id").on("tap", function() {
		setTimeout(function() {
			start_time_picker.show(function(items) {
				$("#useData_id").val(items.text);
				/*$("#useData").html(items.text);*/
				/*start_time_picker.dispose();*/
			});
		}, 200);
	});
	//选择省市区
	var city_picker = new mui.PopPicker({
		layer: 3,
	});
	city_picker.setData(init_city_picker);
	$("#city_id").on("tap", function() {
			city_picker.show(function(items) {
				$("#city_id").val((items[0] || {}).text + "-" + (items[1] || {}).text + "-" + (items[2] || {}).text); //该ID为接收城市ID字段
				/*$("#city_text").html((items[0] || {}).text + "" + (items[1] || {}).text + "" + (items[2] || {}).text);*/
			});
	});
        mui(".mui-scroll-wrapper").scroll({
        			deceleration: 0.0005,
              bounce: false,//滚动条是否有弹力默认是true
              indicators: false, //是否显示滚动条,默认是true
        });        
			var slider = mui("#slider");
			slider.slider({
				interval: 5000
			});
			mui('nav').on('tap', 'a', function() {
				document.location.href = this.href;
			});
		</script>
		<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
