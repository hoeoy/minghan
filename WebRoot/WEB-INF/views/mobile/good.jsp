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
	<title>整形项目</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/mui.css?v=1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/allCss.css?k=1"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/mobile/css/phone-sp.css?t=3"/>
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
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<div id="slider" class="mui-slider">
						<div id="sliderSegmentedControl" class="mui-segmented-control mui-project-good">
							<a class="mui-control-item mui-active" href="#item1">项目类</a>
							<!-- <a class="mui-control-item" href="javascript:void(0);" id="showGoods">商品类</a> -->
						</div>
						<div class="mui-content-padded">
							<div id="item1" class="mui-control-content mui-active">
								<div id="slider" class="mui-slider">
									<div id="sliderSegmentedControl" class="mui-segmented-control mui-tab-list">
										
										<c:if test="${types != null && fn:length(types)>0}">
											<c:forEach items="${types}" var="type" begin="0" end="0">
												<a class="mui-control-item mui-light-pale mui-active show-detail" href="javascript:showDdetail('${type.id}');">${type.typeName}</a>
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
										
										<!-- <a class="mui-control-item mui-light-pink mui-active show-detail" href="javascript:showDdetail(this);" >回龄术</a>
										<a class="mui-control-item mui-light-blue show-detail" href="javascript:void(0);">激光美肤</a>
										<a class="mui-control-item mui-light-red show-detail" href="javascript:void(0);">冰点脱毛</a>
										<a class="mui-control-item mui-light-gray show-detail" href="javascript:void(0);">面部轮廓</a>
										<a class="mui-control-item mui-light-green show-detail" href="javascript:void(0);">眼部整形</a>
										<a class="mui-control-item mui-light-pale show-detail" href="javascript:void(0);">眉部整形</a>
										<a class="mui-control-item mui-light-gray show-detail" href="javascript:void(0);">吸脂塑身</a>
										<span class="block-hidden">
										    <a class="mui-control-item mui-light-blue show-detail" href="javascript:void(0);">微整形</a>
											<a class="mui-control-item mui-light-pale show-detail" href="javascript:void(0);">口腔美容</a>
											<a class="mui-control-item mui-light-pale show-detail" href="javascript:void(0);">明航产品</a>
											<a class="mui-control-item mui-light-blue show-detail" href="javascript:void(0);">美胸整形</a>
											<a class="mui-control-item mui-light-pink show-detail" href="javascript:void(0);">眉部整形</a>
					                    </span>
										<div class="mui-hidden-show mui-light-green" id="hidden-btn">展开>></div> -->
					                    
									</div>
									<div class="mui-content-padded mui-tab-content">
										<div id="it1" class="mui-control-content mui-active">
											<ul id="itemList" class="mui-table-view mui-row mui-project-list">
												
												<%-- <c:if test="${items != null && fn:length(items)>0 }">
													<c:forEach items="${items}" var="item">
														<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6" v-for="item in items">
															<img src="${item.logo}" class="project-img-back" />
															<a href="javascript:void(0);" class="itemDetail">
															<p style="display:none;">${item.id}</p>
																<img class="mui-media-object mui-pull-right" src="static/mobile/images/project_-label.png">
																<div class="mui-media-body">
																	<p>${item.title}</p>
																	<span class="mui-left">¥${item.currentPrice}</span>
																	<span class="mui-right">¥${item.oldPrice}</span>
																</div>
															</a>
														</li>
													</c:forEach>
												</c:if> --%>
												
												<!-- <li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6">
													<img src="http://placehold.it/200x200" class="project-img-back" />
													<a href="javascript:;">
														<img class="mui-media-object mui-pull-right" src="images/project_-label.png">
														<div class="mui-media-body">
															<p>标题</p>
															<span class="mui-left">¥3000</span>
															<span class="mui-right">¥3000</span></div>
													</a>
												</li> -->
											</ul>
										</div>
									</div>
										
									<div class="mui-line-height"></div>
								</div>
							</div>
							<!--	<div id="item2" class="mui-control-content">
								暂未开通，敬请期待。。。。
							</div>-->
							<div id="item2" class="mui-control-content">
								<div id="slider" class="mui-slider">
									<div class="mui-content-padded mui-tab-content">
										<div class="mui-control-content mui-active">
											<ul class="mui-table-view mui-row mui-project-list">
												<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6" v-for="item in items">
													<img src="http://placehold.it/200x200" class="project-img-back" />
													<a href="javascript:;">
														<img class="mui-media-object mui-pull-right" src="images/project_-label.png">
														<div class="mui-media-body">
															<p>标题</p>
															<span class="mui-left">¥3000</span>
															<span class="mui-right">¥3000</span></div>
													</a>
												</li>
												<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6">
													<img src="http://placehold.it/200x200" class="project-img-back" />
													<a href="javascript:;">
														<img class="mui-media-object mui-pull-right" src="images/project_-label.png">
														<div class="mui-media-body">
															<p>标题</p>
															<span class="mui-left">¥3000</span>
															<span class="mui-right">¥3000</span></div>
													</a>
												</li>
												<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6">
													<img src="http://placehold.it/200x200" class="project-img-back" />
													<a href="javascript:;">
														<img class="mui-media-object mui-pull-right" src="images/project_-label.png">
														<div class="mui-media-body">
															<p>标题</p>
															<span class="mui-left">¥3000</span>
															<span class="mui-right">¥3000</span></div>
													</a>
												</li>
											</ul>
										</div>
									</div>
									<div class="mui-line-height"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="mui-off-canvas-backdrop"></div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/mui.min.js" type="text/javascript"></script>
	<script>
		mui.init();
		document.getElementById("hidden-btn").addEventListener("tap", function() {
			if($(".block-hidden").hasClass("mui-active")) {
				$(".block-hidden").removeClass("mui-active");
				$("#hidden-btn").html("展开>>");
			} else {
				$(".block-hidden").addClass("mui-active");
				$("#hidden-btn").html("收起<<");
			}
		});
		
		/* mui('body').on('tap', '#showGoods', function() {
			showGoods();
		});
		function showGoods(){
			//alert("暂未开通，敬请期待！");
			mui.toast("暂未开通，敬请期待！");
		} */
		
		$(document).ready(function(){
			//默认展示回龄术
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/getItemByType.do',
				type:'post',
				async:true,
				data:{"type":"1"},//回龄术id为1
				success:function(result){
					if(result.code == 0){
						var list = result.data;
						for(var i=0; i<list.length; i++){
							var li = 
								'<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6" v-for="item in items">'+
									'<img src="'+list[i].logo+'" class="project-img-back" onerror="javascript:this.src=\'static/mobile/images/defult_img.png\';this.onerror=null;"/>'+
									'<a href="javascript:itemDetail(\''+list[i].id+'\');" class="itemDetail">'+
									'<p style="display:none;">'+list[i].id+'</p>'+
										'<img class="mui-media-object mui-pull-right" src="static/mobile/images/project_-label.png">'+
										'<div class="mui-media-body">'+
											'<p>'+list[i].title+'</p>'+
											'<span class="mui-left">¥'+list[i].currentMin+'-'+list[i].currentMax+'</span>'+
											//'<span class="mui-right">¥'+list[i].oldPrice+'</span>'+
										'</div>'+
									'</a>'+
								'</li>';
							$("#itemList").append(li);
						}
					}else{
						mui.toast("该类型暂无项目");
					}
				}
			});
			
			/*jQuery("img").bind("error", function () {
		  		jQuery(this).attr("src", "static/mobile/images/defult_img.png");
		  		$(this).unbind("error");
				});*/
		});
		
		function showDdetail(type){
			//alert(type);
			//var text = $(th).html();
			$.ajax({
				url:'${pageContext.request.contextPath}/weixin/item/getItemByType.do',
				type:'post',
				async:true,
				data:{"type":type},
				success:function(result){
					if(result.code == 0){
						var list = result.data;
						$("#itemList").empty();
						//alert(list);
						for(var i=0; i<list.length; i++){
							var li = 
								'<li class="mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6" v-for="item in items">'+
									'<img src="'+list[i].logo+'" class="project-img-back" onerror="javascript:this.src=\'static/mobile/images/defult_img.png\';this.onerror=null;" />'+
									'<a href="javascript:itemDetail(\''+list[i].id+'\');" class="itemDetail">'+
									'<p style="display:none;">'+list[i].id+'</p>'+
										'<img class="mui-media-object mui-pull-right" src="static/mobile/images/project_-label.png">'+
										'<div class="mui-media-body">'+
											'<p>'+list[i].title+'</p>'+
											'<span class="mui-left">¥'+list[i].currentMin+'-'+list[i].currentMax+'</span>'+
											//'<span class="mui-right">¥'+list[i].oldPrice+'</span>'+
										'</div>'+
									'</a>'+
								'</li>';
							$("#itemList").append(li);
						}
					}else{
						mui.toast("该分类暂无项目");
					}
				}
			});
		}
		
		function itemDetail(id){
			//var id = $(th).children("p").html();
			var url = "${pageContext.request.contextPath}/weixin/item/getItemById.do?id="+id;
			window.location.href=url;
		}
		
		//function fun(event){
		//	var obj=event.srcElement;  //event在ie中自带有，可以不用传入，其他少数浏览器需要传入
		//	console.log(obj);
		//	alert(obj.innerHTML);
		//}
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
