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
	<title>返现明细</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<style type="text/css">
		.mui-scroll{
			padding-bottom: 0px;
		}
		.mui-table-view {
		    margin-bottom: 0px;
		}
	</style>
  </head>
  
<body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<div class="mui-content mui-scroll-wrapper" id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<ul class="mui-table-view mui-my-projectorder" id="content">
						<c:if test="${page != null && page.list != null && fn:length(page.list)>0 }">
							<c:forEach items="${page.list}" var="order">
									<li class="mui-table-view-cell mui-media">
										<div class="mui-media-body"> <span class="mui-ellipsis">审核人：<span>${order.auditName}</span></span>
											<span class="shouhuo">
												<c:if test="${order.auditFlag eq '0'}">
													未审核
												</c:if>
												<c:if test="${order.auditFlag eq '1' && order.remittanceFlag eq '0'}">
													已审核
												</c:if>
												<c:if test="${order.auditFlag eq '1' && order.remittanceFlag eq '1'}">
													已打款
												</c:if>
											</span>
										</div>
									</li>
									<div class="line-full"></div>
									<li class="mui-table-view-cell mui-media">
										<p class="project-name">${order.comboName}</p>
										<p class="project-time">${order.stringCreateDate}</p>
									</li>
									<div class="line-full"></div>
									<li class="mui-table-view-cell mui-media mui-row">
										<div class="mui-col-sm-12 mui-col-xs-12 mui-project-price">
											返现金额：<span class="money">¥${order.back}</span>&nbsp;&nbsp;<span class="shouxufei">(手续费${order.serviceCharge}元)</span>
										</div>
									</li>
								<div class="mui-full-back"></div>
							</c:forEach>
						</c:if>
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	var page = 1;//第一页
	var totalPage = '${page.totalPage}' == '' ? 0:parseInt('${page.totalPage}',10);//总页数
	var url = "sys/userScore/getNextReturnList.do";
	//var type = 0;
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
        		pageNo:page
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
	            pageNo:page
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
				if(result.code == 1){
					mui.toast(result.msg);
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
					return;
				}
				 var data = result.data;
				 if(data && data.length > 0){
		            for (var i in data) {
		            	var html = //"<ul class='mui-table-view mui-my-projectorder'>"
					            	"<li class='mui-table-view-cell mui-media'>"
					            	+"<div class='mui-media-body'> <span class='mui-ellipsis'>审核人：<span>"+data[i].auditName+"</span></span>"
					            	+"<span class='shouhuo'>"+(data[i].auditFlag == '1' ? '已审核' : '未审核')+"</span>"
					            	+"</div></li>"
					            	+"<div class='line-full'></div>"
					            	+"<li class='mui-table-view-cell mui-media'>"
					            	+"<p class='project-name'>"+data[i].comboName+"</p>"
					            	+"<p class='project-time'>"+data[i].stringCreateDate+"</p>"
					            	+"</li>"
					            	+"<div class='line-full'></div>"
					            	+"<li class='mui-table-view-cell mui-media mui-row'>"
					            	+"<div class='mui-col-sm-12 mui-col-xs-12 mui-project-price'>"
					            	+"返现金额：<span class='money'>¥"+data[i].back+"</span>&nbsp;&nbsp;<span class='shouxufei'>(手续费"+data[i].serviceCharge+"元)</span>"
					            	+"</div></li>"
					            	+"<div class='mui-full-back'></div>";
                      	$("#content").append(html);
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
	mui('.mui-scroll-wrapper').scroll({
		deceleration: 0.02,
		indicators: false,
	}); //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
	mui('body').on('tap', 'a', function() {
		document.location.href = this.href;
	});
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
