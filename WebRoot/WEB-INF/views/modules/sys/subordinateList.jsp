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
    
    <title>团队详情</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
		<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <script type="text/javascript">
	function seeSubordinate(id){
	var subTable = $("#"+id+" tr");
	if(subTable.length>0) return;
	$.ajax({
		url:"vip/seeSubordinate.do",
		data:{"id":id},
		type:"get",
		dataType:"json",
		success:function(data){
			var s = data.data;
			//$('#'+id).empty();
			for(i=0;i<s.length;i++){
			var html ="<tr class='outer'><td>"+
				"<img src='"+s[i].minPhoto+"' class='touxiang'/>"+
				"<span class='name'>"+s[i].name+"</span>"+
				"<span class='member-dengji'>"+
				"<img src='static/mobile/images/record_vip_icon.png'/>"+
				s[i].userLevelEntity+"</span>"+
				"</td>"+
				"<td><span >手机:</span><span>"+s[i].mobile+"</span></td>"+
				"<td>业绩<span class='yeji'>¥"+s[i].performance+"</span>";
				if(s[i].subFlag!=0){
				html+="<span value='"+s[i].id+"' class='switch'  id='fun_"+s[i].id+"'></span>";
				}
				html+="</td></tr>"+
				"<tr class='inner'>"+
				"<td colspan='2'>"+
				"<table class='list-team'  id='"+s[i].id+"' >"+
				"</table>"+
				"</tb>"+
				"</tr>";
				$('#'+id).append(html);
					$("#fun_"+s[i].id).bind('click',function () {
								if($(this).parents(".outer").is(".open")) {
									$(this).parents(".outer").removeClass("open").next(".inner").removeClass("open");
								} else {
								var subId = $(this).attr("value");
								var subTable = $("#"+subId+" tr");
									if(subTable.length==0){
									    seeSubordinate(subId);
									}
									$(this).parents(".outer").addClass("open").next(".inner").addClass("open");
								}
							});
					}
			}
	})
	}

</script>
  </head>
  <body>
		<!-- 主界面移动、菜单不动 -->
<!--		<div class="mui-off-canvas-wrap mui-draggable">-->
			<!-- 主页面容器 -->
			<div class="mui-inner-wrap">
				<!-- 主页面标题 -->
				<!--	<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">价格确认</h1>
</header>-->
				<div class="mui-content mui-scroll-wrapper">
					<div class="mui-scroll">
						<!-- 主界面具体展示内容 -->
						<!--<div id="container">-->
						<table id="listTable-team" class="list-team" style="position: relative;">
							<thead>
								<tr>
									<th width="20%"></th>
									<th width="20%"></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach  var="list" items="${list}">
								<tr class="outer">
									<td>
										<img src="${list.minPhoto }" class="touxiang"/>
										<span class="name">${list.name }</span>
										<span class="member-dengji">
										<img src="static/mobile/images/record_vip_icon.png"/>
										${list.userLevelEntity }</span>
									</td>
									<td>
										<span >手机:</span>
										<span>${list.mobile }</span>
									</td>
									<td>
										业绩
										<span class="yeji">¥${list.performance }</span>
										<c:if test="${list.subFlag !=0 }">
										<span value="${list.id }" class="switch" ></span>
										</c:if>
									</td></tr>
								<tr  class="inner">
								<td colspan="2">
								<table class="list-team"  id="${list.id }" >
								</table>
								</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--</div>-->
						
						<div class="table-team-member">直接下级的成员共<span>${sbNub }位</span></div>
					</div>
				</div>
			<!--	<div class="mui-off-canvas-backdrop"></div>-->
			</div>
		<!--</div>-->
	    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
		<script src="static/mobile/js/mui.min.js"></script>
		<script>
			/*Javascript代码片段*/
			function resize() {
				$(".list-team thead th").each(function(index) {
					var width = $(this).width();
					$(".list-team .outer td:nth-child(" + index + ")").css("width", width);
					$(".list-team .leaf td:nth-child(" + index + ")").css("width", width);
				});
			}
			resize();
			$(window).resize(resize);
			$(".switch").click(function () {
				if($(this).parents(".outer").is(".open")) {
					$(this).parents(".outer").removeClass("open").next(".inner").removeClass("open");
				} else {
				 var subId = $(this).attr("value");
					var subTable = $("#"+subId+" tr");
					if(subTable.length==0){
					    seeSubordinate(subId);
					}
					$(this).parents(".outer").addClass("open").next(".inner").addClass("open");
				}
			});
			$("tr.outer, tr.leaf").click(function() {
				$("tr.focus").removeClass("focus");
				$(this).addClass("focus");
			});
			
		</script>
		<script>
			mui.init();
		</script>
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
	</body>
</html>
