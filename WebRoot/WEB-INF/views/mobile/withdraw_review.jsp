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
	<title>提现审核</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css"/>
	<style type="text/css">
		.mui-scroll{
			padding-bottom: 0px;
		}
		.mui-table-view.mui-beautifuldairy {
		    margin-bottom: 0px;
		}
	</style>
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
			<div class="mui-content mui-scroll-wrapper" id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<ul class="mui-table-view withdraw-review" id="firstUl">
					
						<c:if test="${list != null && fn:length(list)>0 }">
							<c:forEach items="${list}" var="takeout">
								<li class="mui-table-view-cell mui-row">
									<div class="mui-left">
										<p class="tixian-discriable">${takeout.userName}-${takeout.type eq '0' ? '佣金提现' : '返现提现'}</p>
										<p class="mui-datetime">${takeout.stringCreateDate}</p>
									</div>
									<div class="mui-right">
										<p class="tixian-discriable-right">¥${takeout.type eq '0' ? takeout.brokerage : takeout.back}</p>
										<a class="confire-btn <c:if test="${takeout.auditFlag eq '1'}">active</c:if>" id="${takeout.id}"  href="javascript:approval('${takeout.id}','${takeout.auditFlag}')">${takeout.auditFlag ne '1' ? '确认审核' : '已审核'}</a>
									</div>
								</li>
								<div class="line-full"></div>
							</c:forEach>
						</c:if>
						
					</ul>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="static/mobile/js/wxBackRefresh.js"></script>
	<script type="text/javascript">
		function approval(orderId,flag){
			if(flag =="1"){
				return;
			}
			/*else{
				var s = orderId.toString();
				var s1 = s.split("(")[1].split(",")[0];
				//alert(s1);
				var url = "${pageContext.request.contextPath}/weixin/manage/rewardAudit.do?orderId="+s1;
				$.getJSON(url, function(result){
					if(result.code == 0){
						mui.toast("审核成功");
						//window.location.href = "${pageContext.request.contextPath}/weixin/manage/getALL.do";
						$("#"+s1).addClass("active");
						$("#"+s1).html("已审核");
					}else{
						mui.toast("审核失败，请稍后重试");
					}
				});
			}*/
			if($("#"+orderId).hasClass("active")){
				return;
			}
			var url = "${pageContext.request.contextPath}/weixin/manage/rewardAudit.do?orderId="+orderId;
			$.getJSON(url, function(result){
				if(result.code == 0){
					mui.toast("审核成功");
					//window.location.href = "${pageContext.request.contextPath}/weixin/manage/getALL.do";
					$("#"+orderId).addClass("active");
					$("#"+orderId).html("已审核");
				}else{
					mui.toast("审核失败，请稍后重试");
				}
			});
		}
		var page = 1;//第一页
		var tmp ='${totalPage}';
		var totalPage = parseInt(tmp == '' ? '0':tmp,10);//总页数
		var url = "weixin/manage/getNext.do";
		mui.init({
		    pullRefresh: {
		        container: '#pullrefresh',
		        /*down: {//下拉刷新
		            auto:true,//可选,默认false.自动下拉刷新一次
		            contentdown : "下拉可以刷新",//可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容
		            contentover : "释放立即刷新",//可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容
		            contentrefresh : "正在刷新...",//可选，正在刷新状态时，下拉刷新控件上显示的标题内容
		            callback: pulldownRefresh
		        },*/
		        up: {//上拉加载
		            //auto:true,//可选,默认false.自动上拉加载一次
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
		mui(".mui-scroll-wrapper").scroll({
					deceleration: 0.001,
		     // bounce: false,//滚动条是否有弹力默认是true
		      indicators: false, //是否显示滚动条,默认是true
		});        
		mui('body').on('tap', 'a', function() {
			if(typeof this.href)
			document.location.href = this.href;
		});
		function pulldownRefresh() {
		    setTimeout(function() {
		    	page = 1;//刷新并显示第一页
		        var data={
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
		        var data={
	        		pageNo:page
		        };
		        type=2;//代表上拉加载
		        toList(data,type);//具体取数据的方法
		    }, 100);
		}
		function toList(data,type) {
		    //plus.nativeUI.showWaiting("等待中....",{padlock: true});//数据出来前显示等待加载框
		    $.ajax({
		    	url:url,
				type:'POST',
				async:true,
		        data:data,
		        dataType: 'json',
				success:function(result){
					if(result.code == 1){
						mui.toast("加载数据失败");
						return;
					}
					 var data = result.data;
					 if(data && data.length > 0){
			            for (var i in data) {
			            	var othercs = "";
			            	if(data[i].auditFlag != '0'){
			            		othercs = "active";
			            	}
			            	var html = "<li class='mui-table-view-cell mui-row'>"
			            				+"<div class='mui-left'>"
										+"<p class='tixian-discriable'>"+data[i].userName+"-"+(data[i].type == '0' ? '佣金提现' : '返现提现')+"</p>"
										+"<p class='mui-datetime'>"+data[i].stringCreateDate+"</p>"
										+"</div>"
										+"<div class='mui-right'>"
										+"<p class='tixian-discriable-right'>¥"+(data[i].type == '0' ? data[i].brokerage : data[i].back)+"</p>"
										+"<a class='confire-btn "+othercs+"' id='"+data[i].id+"' href='javascript:approval(\""+data[i].id+"\",\""+data[i].auditFlag+"\");'>"+(data[i].auditFlag == '0' ? '确认审核' : '已审核')+"</a>"
										+"</div>"
										+"</li><div class='line-full'></div>";
	                        $("#firstUl").append(html);
			             }
					 }
		             mui('#pullrefresh').pullRefresh().endPulldownToRefresh();//结束下拉刷新
		             /*结束上拉加载，并根据情况切换“下拉显示更多数据”，以及“没有更多数据了”。执行endPullupToRefresh()方法，结束转雪花进度条的“正在加载...”过程,若还有更多数据，则传入false; 否则传入true，之后滚动条滚动到底时*/
		             if(page < totalPage){
		                 mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
		             } else {
		                 mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
		             }
		             //plus.nativeUI.closeWaiting();
		             //mui.currentWebview.show(); //显示当前页面
		             
		             /*if(data.status!=1&&page==1){
		                alert("");
		                plus.nativeUI.closeWaiting();
		                mui.currentWebview.show(); //显示当前页面
		             }*/
		        },
		        error: function(xhr, type, errerThrown) {
		            mui.toast('网络异常,请稍候再试');
		            //plus.nativeUI.closeWaiting();  
		            mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
		        }
		    });
		};
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
