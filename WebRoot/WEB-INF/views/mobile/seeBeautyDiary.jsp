<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>美丽日记</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
<link rel="stylesheet" type="text/css"
	href="static/mobile/css/allCss.css" />
<link rel="stylesheet" href="static/mobile/css/phone-sp.css" />
<style type="text/css">
.mui-scroll {
	padding-bottom: 0px;
}

.mui-table-view.mui-beautifuldairy {
	margin-bottom: 0px;
}
</style>
</head>
<body>
	<!-- 主界面菜单同时移动 -->
	<!-- 侧滑导航根容器 -->
	<!-- <div class="mui-off-canvas-wrap mui-draggable"> -->
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面内容容器 -->
			<div class="mui-content mui-scroll-wrapper" id="pullrefresh">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->

					<div id="slider" class="mui-slider">
						<div id="sliderSegmentedControl" class="mui-segmented-control mui-tab-list">
										<a class="mui-control-item mui-light-pale mui-active show-detail" href="javascript:showDdetail();">全部</a>
										<c:if test="${types != null && fn:length(types)>0}">
											<c:forEach items="${types}" var="type" begin="0" end="0">
												<a class="mui-control-item mui-light-pale  show-detail" href="javascript:showDdetail('${type.id}');">${type.typeName}</a>
											</c:forEach>
											<c:forEach items="${types}" var="type" begin="1" end="3">
												<a class="mui-control-item mui-light-pale show-detail" href="javascript:showDdetail('${type.id}');">${type.typeName}</a>
											</c:forEach>
											<span class="block-hidden">
												<c:forEach items="${types}" var="type" begin="4">
													<a class="mui-control-item mui-light-pale show-detail" href="javascript:showDdetail('${type.id}');">${type.typeName}</a>
												</c:forEach>
											</span>
											<div class="mui-hidden-show mui-light-green" id="hidden-btn">展开>></div>
										</c:if>
									</div>
						<div class="mui-content-padded mui-tab-content">
							<div id="bt1" class="mui-control-content mui-active">
									<ul class="mui-table-view mui-beautifuldairy" id="firstUl">
						<c:forEach var="list" items="${list}">
							<li class="mui-table-view-cell mui-collapse-content"><img
								class="mui-pull-left dairy-touxiang"
								src="${list.authorMinPhoto }"
								onerror="this.src='static/mobile/images/head_zhuanjia.png';this.onerror=null">
								<div class="mui-media-body">
									<span class="mui-left">${list.authorName }</span> <span
										class="mui-right">${list.time }<img
										src="static/mobile/images/riji_icon_time.png" />
									</span>
								</div>
								<div class="mui-row">
									<c:if test="${not empty list.before}">
										<div class="mui-col-sm-6 mui-col-xs-6">
											<a class="dairy-before-img"
												href="bd/seeBefore.do?diaryId=${list.id }"> <img
												src="${list.before }"
												style="height: 170px;object-fit: cover;"
												onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null" />
												<p class="flag">Before</p> </a>
										</div>
									</c:if>
									<c:if test="${not empty list.after}">
										<div class="mui-col-sm-6 mui-col-xs-6">
											<a class="dairy-after-img"
												href="bd/seeAfter.do?diaryId=${list.id }"> <img
												src="${list.after }"
												style="height: 170px;object-fit: cover; "
												onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null" />
												<p class="flag">After</p> </a>
										</div>
									</c:if>
								</div>
								<p class="share-dairy-content">
									#<span>${list.orderId }</span># ${list.diary }
								</p></li>
							<div class="mui-full-back"></div>
						</c:forEach>
					</ul>
					<%--<div style="text-align:center;">
					<p id="msg-p">按住此处用力上拉加载更多</p>
					</div>
				--%>
							</div>
						</div>
						<!-- <div class="mui-line-height"></div> -->
					</div>
				</div>
			</div>
			<!-- <div class="mui-off-canvas-backdrop"></div>
		</div> -->
	</div>
	<script type="text/javascript" src="static/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/mobile/js/mui.min.js"></script>

	<script type="text/javascript">
		var page = 1;//第一页
		var totalPage = ${allPage};//总页数
		var url = "bd/bdLoading.do";
		var typeId = "";
	document.getElementById("hidden-btn").addEventListener("tap", function() {
				if($(".block-hidden").hasClass("mui-active")) {
					$(".block-hidden").removeClass("mui-active");
					$("#hidden-btn").html("展开>>");
				} else {
					$(".block-hidden").addClass("mui-active");
					$("#hidden-btn").html("收起<<");
				}
			});
		//var type = 0;
		mui.init({
			pullRefresh : {
				container : '#pullrefresh',
				up : {//上拉加载
					contentrefresh : '正在加载...',
					contentnomore : '没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
					callback : pullupRefresh
				}
			}
		});
		$(document).ready(function() {
			if (page >= totalPage) {
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
			}
		});
		function pulldownRefresh() {
			setTimeout(function() {
				page = 1;//刷新并显示第一页
				data = {
					page : page
				};
				type = 1;//代表下拉刷新
				toList(data, type);//具体取数据的方法
			}, 100);
		}
		function pullupRefresh() {
			setTimeout(function() {
				if (page >= totalPage) {
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。  
					return;
				}
				page++;//翻下一页
				data = {
					page : page,
					type : typeId
				};
				type = 2;//代表上拉加载
				toList(data, type);//具体取数据的方法
			}, 100);
		}
		function toList(data, type) {
			$.ajax({
						url : url,
						type : 'POST',
						async : true,
						data : data,
						dataType : 'json',
						success : function(result) {
							var data = result.data;
							if (data && data.length > 0) {
								for ( var i in data) {
									var html = "<li class='mui-table-view-cell mui-collapse-content mui-media'>"
											+ "<img class='mui-pull-left dairy-touxiang' src='"+data[i].authorMinPhoto+"' onerror='this.src=\"static/mobile/images/head_zhuanjia.png\";this.onerror=null'>"
											+ "<div class='mui-media-body'>"
											+ "<span class='mui-left'>"
											+ data[i].authorName
											+ "</span>"
											+ "<span class='mui-right'>"
											+ data[i].time
											+ "<img src='static/mobile/images/riji_icon_time.png'/></span>"
											+ "</div>"
											+ "<div class='mui-row'>";

									if (data[i].before != null
											&& data[i].before != "") {
										html += "<div class='mui-col-sm-6 mui-col-xs-6'>"
												+ "<a class='dairy-before-img' href='bd/seeBefore.do?diaryId="
												+ data[i].id
												+ "'>"
												+ "<img src='"+data[i].before+"' style='height: 170px;object-fit: cover;' onerror='this.src=\"static/mobile/images/defult_img.png\";this.onerror=null'/>"
												+ "<p class='flag'>Before</p>"
												+ "</a>" + "</div>";
									}
									if (data[i].after != null
											&& data[i].after != "") {
										html += "<div class='mui-col-sm-6 mui-col-xs-6'>"
												+ "<a class='dairy-after-img' href='bd/seeAfter.do?diaryId="
												+ data[i].id
												+ "'>"
												+ "<img src='"+data[i].after+"' style='height: 170px;object-fit: cover;' onerror='this.src=\"static/mobile/images/defult_img.png\";this.onerror=null'/>"
												+ "<p class='flag'>After</p>"
												+ "</a>" + "</div>";
									}
									html += "</div>"
											+ "<p class='share-dairy-content'>#<span>"
											+ data[i].orderId
											+ "</span>#"
											+ data[i].diary
											+ "</p>"
											+ "</li>"
											+ "<div class='mui-full-back'></div>";
									$("#firstUl").append(html);
									//mui.currentWebview.show(); //显示当前页面
								}
							}
							mui('#pullrefresh').pullRefresh()
									.endPulldownToRefresh();//结束下拉刷新
							/*结束上拉加载，并根据情况切换“下拉显示更多数据”，以及“没有更多数据了”。执行endPullupToRefresh()方法，结束转雪花进度条的“正在加载...”过程,若还有更多数据，则传入false; 否则传入true，之后滚动条滚动到底时*/
							if (page < totalPage) {
								mui('#pullrefresh').pullRefresh()
										.endPullupToRefresh(false);
							} else {
								mui('#pullrefresh').pullRefresh()
										.endPullupToRefresh(true);
							}
						},
						error : function(xhr, type, errerThrown) {
							mui.toast('网络异常,请稍候再试');
							mui('#pullrefresh').pullRefresh()
									.endPullupToRefresh(true);
						}
					});
		};
		function showDdetail(type){
		page = 1;
		typeId = type;
			//alert(type);
			//var text = $(th).html();
			$.ajax({
				url:url,
				type:'post',
				async:true,
				data:{"type":type,"page":page},
				success:function(result){
				$("#firstUl").empty();
							var data = result.data;
							if (data && data.length > 0) {
								for ( var i in data) {
									var html = "<li class='mui-table-view-cell mui-collapse-content mui-media'>"
											+ "<img class='mui-pull-left dairy-touxiang' src='"+data[i].authorMinPhoto+"' onerror='this.src=\"static/mobile/images/head_zhuanjia.png\";this.onerror=null'>"
											+ "<div class='mui-media-body'>"
											+ "<span class='mui-left'>"
											+ data[i].authorName
											+ "</span>"
											+ "<span class='mui-right'>"
											+ data[i].time
											+ "<img src='static/mobile/images/riji_icon_time.png'/></span>"
											+ "</div>"
											+ "<div class='mui-row'>";

									if (data[i].before != null
											&& data[i].before != "") {
										html += "<div class='mui-col-sm-6 mui-col-xs-6'>"
												+ "<a class='dairy-before-img' href='bd/seeBefore.do?diaryId="
												+ data[i].id
												+ "'>"
												+ "<img src='"+data[i].before+"' style='height: 170px;object-fit: cover;' onerror='this.src=\"static/mobile/images/defult_img.png\";this.onerror=null'/>"
												+ "<p class='flag'>Before</p>"
												+ "</a>" + "</div>";
									}
									if (data[i].after != null
											&& data[i].after != "") {
										html += "<div class='mui-col-sm-6 mui-col-xs-6'>"
												+ "<a class='dairy-after-img' href='bd/seeAfter.do?diaryId="
												+ data[i].id
												+ "'>"
												+ "<img src='"+data[i].after+"' style='height: 170px;object-fit: cover;' onerror='this.src=\"static/mobile/images/defult_img.png\";this.onerror=null'/>"
												+ "<p class='flag'>After</p>"
												+ "</a>" + "</div>";
									}
									html += "</div>"
											+ "<p class='share-dairy-content'>#<span>"
											+ data[i].orderId
											+ "</span>#"
											+ data[i].diary
											+ "</p>"
											+ "</li>"
											+ "<div class='mui-full-back'></div>";
									$("#firstUl").append(html);
									//mui.currentWebview.show(); //显示当前页面
								}
							}
							mui('#pullrefresh').pullRefresh()
									.endPulldownToRefresh();//结束下拉刷新
							/*结束上拉加载，并根据情况切换“下拉显示更多数据”，以及“没有更多数据了”。执行endPullupToRefresh()方法，结束转雪花进度条的“正在加载...”过程,若还有更多数据，则传入false; 否则传入true，之后滚动条滚动到底时*/
							if (page < totalPage) {
								mui('#pullrefresh').pullRefresh()
										.endPullupToRefresh(false);
							} else {
								mui('#pullrefresh').pullRefresh()
										.endPullupToRefresh(true);
							}
						}
				}
			);
		}
		mui('.mui-scroll-wrapper').scroll({
			deceleration : 0.02,
			indicators : false,
		}); //设置减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
		mui('body').on('tap', 'a', function() {
			document.location.href = this.href;
		});
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
