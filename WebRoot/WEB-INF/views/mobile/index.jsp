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
    
    <title>首页</title>
    <meta http-equiv="Expires" content="-1">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/swiper.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css?p=9"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css?y=1"/>
  </head>
  <body>
		<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
	<!-- 主页面容器 -->
	<div class="mui-inner-wrap">
		<jsp:include page="bottom.jsp"></jsp:include>
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper mui-index" style="background: #f7f7f7;">
			<div class="mui-scroll">
				<!-- 主界面具体展示内容 -->
				<div id="slider" class="mui-slider">
					<div class="mui-slider-group mui-slider-loop">
						<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
						<div class="mui-slider-item mui-slider-item-duplicate" id="top">
							<a href="javascript:void(0)">
								<img src="static/mobile/images/defult_img.png">
							</a>
						</div>
						<c:choose>
							<c:when test="${advertList != null && fn:length(advertList)>0 }">
								<c:forEach items="${advertList}" var="advert" varStatus="advertStatus">
									<c:if test="${advertStatus.count == 0 }">
										<div class="mui-slider-item mui-slider-item-duplicate" id="top">
											<a href="javascript:void(0)">
												<img src="${advertList[fn:length(advertList)].advertUrl}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;">
											</a>
										</div>
									</c:if>
									<div class="mui-slider-item mui-slider-item-duplicate">
										<a href="javascript:void(0)">
											<img src="${advert.advertUrl}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;">
										</a>
									</div>
									<c:if test="${advertStatus.count == fn:length(advertList) }">
										<div class="mui-slider-item mui-slider-item-duplicate">
											<a href="javascript:void(0)">
												<img src="${advertList[0].advertUrl}" onerror="this.src='static/mobile/images/defult_img.png';this.onerror=null;">
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
						<!-- <div class="mui-slider-item">
							<a href="javascript:void(0)">
								<img src="static/mobile/images/defult_img.png">
							</a>
						</div> -->
					</div>
					<div class="mui-slider-indicator">
						<c:if test="${advertList != null && fn:length(advertList)>0 }">
							<c:forEach items="${advertList}" var="advert" varStatus="status">
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
				</div>
				<%--<p><a href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=xxxxxxxxxxxx&scene=116#wechat_redirect">快手哦</a></p>
				<p><a href="weixin://contacts/profile/gh_3ce59adaa790">快手哦</a></p>
				--%>
				<ul class="mui-table-view mui-grid-view mui-grid-9 mui-grid-index">
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="about-minghan">
						<a href="static/mobile/about.jsp">
							<img src="static/mobile/images/home_icon_about.png" />
							<div class="mui-media-body">关于名韩</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="export-team">
						<a href="doct/getAll.do">
							<img src="static/mobile/images/home_icon_team.png" />
							<div class="mui-media-body">专家团队</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="huilingsu">
						<a href="${pageContext.request.contextPath}/weixin/item/getItemById.do?id=1">
							<img src="static/mobile/images/huilingshu.png" />
							<div class="mui-media-body">回龄术</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="zx-project">
						<a href="javascript:void(0);" id="goItemProject">
							<img src="static/mobile/images/home_icon_project.png" />
							<div class="mui-media-body">整形项目</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="beatiful-diary">
						<a href="bd/seeBeautyDiary.do">
							<img src="static/mobile/images/home_icon_diary.png" />
							<div class="mui-media-body">美丽日记</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-4 mui-col-sm-4"  id="consult-us">
						<a href="http://float2006.tq.cn/static.jsp?version=vip&admiuin=9801906&ltype=1&iscallback=0&page_templete_id=114023&is_message_sms=0&is_send_mail=0&uin=9801906" target="_blank">
							<img src="static/mobile/images/home_icon_contact.png" />
							<div class="mui-media-body">咨询我们</div>
						</a>
					</li>
				</ul>
				<div class="list-title">
					<img src="static/mobile/images/home_list_strategy_icon.png" />
					<p>美容攻略</p>
				</div>
					<div class="swiper-container">
							<div class="swiper-wrapper meirong-notice">
								<div class="swiper-slide">
									<img src="static/mobile/images/strategy_a.png">
								</div>
								<div class="swiper-slide">
									<img src="static/mobile/images/strategy_b.png">
								</div>
								<div class="swiper-slide">
									<img src="static/mobile/images/strategy_c.png">
								</div>
								<div class="swiper-slide">
									<img src="static/mobile/images/strategy_d.png">
								</div>
							</div>
						</div>
				<div class="list-title">
					<img src="static/mobile/images/home_list_vip_icon.png" />
					<p>新会员套餐</p>
				</div>
				<ul class="mui-table-view mui-grid-view mui-grid-9 new-membership-meal" id="combo">
					<!-- <li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-6 mui-col-sm-6">
						<a href="meal-detail.html">
							<div class="list-meal"><span class="meal-left">A套餐</span><span class="meal-right">7折&nbsp;•</span>
								<p class="meal-return">分12期返还</p>
							</div>
							<div class="mui-media-body"><span class="mui-left">¥3000</span><span class="mui-right">¥3000</span></div>
						</a>
					</li> -->
				</ul>
				<div class="list-title">
					<img src="static/mobile/images/home_list_goods_icon.png" />
					<p>名韩商品专享</p>
				</div>
				<ul class="mui-table-view mui-grid-view mui-grid-9 minghan-index">
					<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-12 mui-col-sm-12">
						<a href="app/mgoods/tequan_detail.do">
							<img src="static/mobile/images/tequan.png" style="width: 100%;height: auto;object-fit: cover;">
						</a>

					</li>
					<div class="mui-full-back"></div>
					<c:if test="${fn:length(mList)>0  }">
					<c:forEach items="${mList }" var="mList">
					<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6 shangpin" v-for="item in items">
						<img src="${mList.logo }" class="project-img-back" />
						<a href="app/mgoods/good_detail.do?id=${mList.id }">
							<div class="mui-media-body">
								<p>${mList.name }</p>
								<span class="mui-left">¥${mList.price }</span>
								<span class="mui-right">¥${mList.market }</span></div>
						</a>
					</li>
					</c:forEach>
					</c:if>
				</ul>
				<div class="list-title">
					<img src="static/mobile/images/home_list_jingpin_icon.png" />
					<p>精品项目</p>
				</div>
				<ul class="mui-table-view mui-index-project" id="refresh-ul">
					<!-- <li class="mui-table-view-cell mui-media">
						<img src="http://placehold.it/220x160" class="project-img-back"/>
						<a href="meal-detail.html">
							<img class="mui-media-object mui-pull-right" src="static/mobile/images/project_-label.png">
							<div class="mui-media-body">
								<p>标题</p>
								<span class="mui-left">¥3000-3900</span>
								<span class="mui-right">¥3000-3900</span></div>
						</a>
					</li> -->
				</ul>
			</div>
		</div>
		<!--  <div class="mui-off-canvas-backdrop"></div>-->
	</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/swiper.min.js"></script>
	<script type="text/javascript">
		mui.init();
		var swiper = new Swiper('.swiper-container', {
				slidesPerView: "auto",
				centeredSlides: false,
				spaceBetween: 10,
				pagination: {
					el: '.swiper-pagination',
					clickable: true,
				},
			});
	</script>
	<script type="text/javascript" src="static/mobile/js/minghan.js" ></script>
	<script type="text/javascript">
		$(document).ready(function(){
		jQuery("img").on("error", function () {
		  jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
			getCombo();//首页套餐信息
			var page = 1;//当前页
			var size = 4;//每页展示几条数据
			getItem(page, size);//首页项目信息
			
		});
		function getCombo(){
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/combo/getComboList.do',
				type:'get',
				async:true,
				success:function(result){
					if(result.code == 0){
						var list = result.data;
						for(var i=0; i<list.length; i++){
							var str = 
								'<li class="mui-table-view-cell mui-collapse-content mui-media mui-col-xs-6 mui-col-sm-6">'+
									'<a href="${pageContext.request.contextPath}/weixin/combo/getComboById.do?id='+list[i].id+'">'+
										'<div class="list-meal"><span class="meal-left">'+list[i].name+'</span><span class="meal-right">'+list[i].discount+'折</span>'+
											'<p class="meal-return">分12期返还</p>'+
										'</div>'+
										'<div class="mui-media-body"><span class="mui-left">'+'¥'+list[i].currentPrice+'</span><span class="mui-right">'+'¥'+list[i].oldPrice+'</span></div>'+
									'</a>'+
								'</li>';
							$("#combo").append(str);
						}
					}
				}
			});
		}
		
		function getItem(page, size){
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/getItemByPage.do'+'?page='+page+'&size='+size,
				type:'get',
				async:true,
				success:function(result){
					if(result.code == 0){
						var list = result.data;
						//alert(list.length);
						for(var i=0; i<list.length; i++){
							var str = 
								'<li class="mui-table-view-cell mui-collapse-content mui-media">'+
									'<img src="'+list[i].logo+'" class="project-img-back"/>'+
									'<a href="${pageContext.request.contextPath}/weixin/item/getItemById.do?id='+list[i].id+'">'+
										'<img class="mui-media-object mui-pull-right" src="static/mobile/images/project_-label.png">'+
										'<div class="mui-media-body">'+
											'<p>'+list[i].title+'</p>'+
											'<span class="mui-left">¥'+list[i].currentMin+'-'+list[i].currentMax+'</span>'+
											'<span class="mui-right">¥'+list[i].oldMin+'-'+list[i].oldMax+'</span>'+
										'</div>'+
									'</a>'+
								'</li>';
							$("#refresh-ul").append(str);
						}
							jQuery("img").on("error", function () {
		  jQuery(this).attr("src", "static/mobile/images/defult_img.png");
			});
					}
				}
			});
		}
		
		mui('body').on('tap', '#goItemProject', function() {
			goItemProject();
		});
		function goItemProject(){
			var url = "${pageContext.request.contextPath}/weixin/item/goItemProject.do";
			window.location.href=url;
		}
	</script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js?v=1"></script>
</body>
</html>
