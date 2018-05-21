<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
    
    <title>余额明细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css" />

  </head>
  
  <body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			<!--	<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title">价格确认</h1>
</header>-->
			<div class="mui-content mui-scroll-wrapper"  id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
		<ul id="top-balance" class="mui-table-view withdraw-review">
		<c:forEach var="list" items="${list}">
		    <li class="mui-table-view-cell mui-row">
		       <div class="mui-left">
		       	<p class="tixian-discriable">${fns:getDictLabel(list.indFlag, 'sys_balance_ind_flag', '')}</p>
		       	<p class="mui-datetime">${list.time }</p>
		       </div>
		       <div class="mui-right">
		       	<p class="tixian-discriable-right">¥${list.money }</p>
		       </div>
		    </li>
		    <div class="line-full"></div>
		    </c:forEach>
		</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="static/mobile/js/mui.min.js"></script>
	<script type="text/javascript">
		var page = 1;//第一页
		var totalPage = ${allPage};//总页数
		var url = "sys/userScore/getBalanceListLimit.do";
		mui.init({
		    pullRefresh: {
		        container: '#pullrefresh',
		        up: {//上拉加载
		            contentrefresh: '正在加载...',
		            contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
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
			            	var html = "<li class='mui-table-view-cell mui-row'>"+
			            	" <div class='mui-left'>"+
			            	"<p class='tixian-discriable'>"+data[i].indFlag+"</p>"+
			            	"<p class='mui-datetime'>"+data[i].time+"</p>"+
			            	"</div>"+
			            	"<div class='mui-right'>"+
			            	"<p class='tixian-discriable-right'>¥"+data[i].money+"</p>"+
			            	"</div>"+
			            	"</li>"+
			            	" <div class='line-full'></div>" ;       
			            	$("#top-balance").append(html);
			                      //mui.currentWebview.show(); //显示当前页面
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
		var slider = mui("#slider");
		slider.slider({
			interval: 5000
		});

		mui('.mui-scroll-wrapper').scroll({
			deceleration: 0.02,
			indicators: false,
		}); //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
		mui('body').on('tap', 'a', function() {
			document.location.href = this.href;
		});
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
