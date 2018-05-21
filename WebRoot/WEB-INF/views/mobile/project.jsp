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
    <style>
			.good-center {
				display:none;
				position: absolute!important;
				width: 50px;
				height: 50px;
				right: 10px;
				bottom: 20px;
				z-index: 999999;
				background: url(static/mobile/images/shangpin_btn.png) top center no-repeat;
				background-size: contain;
			}
		</style>
  </head>
  
<body>
	<!-- 主界面移动、菜单不动 -->
	<div class="mui-off-canvas-wrap mui-draggable">
		<!-- 主页面容器 -->
		<div class="mui-inner-wrap">
			<!-- 主页面标题 -->
			<a href="app/muser/goods-center.do" id="btn-center" class="good-center">
				</a>
			<div class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!-- 主界面具体展示内容 -->
					<div id="slider" class="mui-slider">
						<div id="sliderSegmentedControl" class="mui-segmented-control mui-project-good">
							<a id="list1" class="mui-control-item mui-active" href="javascript:#item1" onclick="play();">项目类</a>
							<a id="list2" class="mui-control-item" href="javascript:#item2" onclick="play();">商品类</a>
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
									</div>
									<div class="mui-content-padded mui-tab-content">
										<div id="it1" class="mui-control-content mui-active">
											<ul id="itemList" class="mui-table-view mui-row mui-project-list">
											</ul>
										</div>
									</div>
										
									<div class="mui-line-height"></div>
								</div>
							</div>
							<div id="item2" class="mui-control-content">
								<div id="slider" class="mui-slider">
									<div id="sliderSegmentedControl2" class="mui-segmented-control mui-tab-list">
										<c:if test="${goodsType != null && fn:length(goodsType)>0}">
											<c:forEach items="${goodsType}" var="goodstype" begin="0" end="0">
												<a class="mui-control-item mui-light-pale show-detail" href="javascript:showGoods('${goodstype.id}');">${goodstype.typeName}</a>
											</c:forEach>
											<c:forEach items="${goodsType}" var="goodstype" begin="1" end="3">
												<a class="mui-control-item mui-light-pale show-detail" href="javascript:showGoods('${goodstype.id}');">${goodstype.typeName}</a>
											</c:forEach>
											<span class="block-hidden">
												<c:forEach items="${goodsType}" var="goodstype" begin="4">
													<a class="mui-control-item mui-light-pale show-detail" href="javascript:showGoods('${goodstype.id}');">${goodstype.typeName}</a>
												</c:forEach>
											</span>
											<div class="mui-hidden-show mui-light-green" id="hidden-btn2">展开>></div>
										</c:if>
									</div>
									<div class="mui-content-padded mui-tab-content">
										<div class="tequan">
											<a href="app/mgoods/tequan_detail.do">
												<img src="static/mobile/images/tequan.png" style="width: 100%;height: auto;" />
											</a>
										</div>
										<div id="it2_1" class="mui-control-content mui-active">
											<ul  id ="goodsList" class="mui-table-view mui-row mui-project-list">
											</ul>
										</div>
									</div>
									<div class="mui-line-height"></div>
								</div>
								<!--	<div style="position: absolute;right: 10px;top: 50%;z-index: 999999;">
								<p>商品中心</p>
							</div>-->
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
		$("#list2").on("tap",function(){
					$("#btn-center").css("display", "block");
		});
		$("#list1").on("tap",function(){
					$("#btn-center").css("display", "none");
		});
	/* 	function play() {
				if($("#list2").hasClass("mui-active")) {
					$("#btn-center").css("display", "block");
				} else {
					$("#btn-center").css("display", "none");
				}
			} */
		document.getElementById("hidden-btn2").addEventListener("tap", function() {
				if($(".block-hidden").hasClass("mui-active")) {
					$(".block-hidden").removeClass("mui-active");
					$("#hidden-btn2").html("展开>>");
				} else {
					$(".block-hidden").addClass("mui-active");
					$("#hidden-btn2").html("收起<<");
				}
			});
		
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
		
		
		function showGoods(type){
			//alert(type);
			//var text = $(th).html();
			$.ajax({
				url:'${pageContext.request.contextPath}/app/mgoods/getGoodsByType.do',
				type:'post',
				async:true,
				data:{"type":type},
				success:function(result){
					if(result.code == 0){
						var list = result.data;
						$("#goodsList").empty();
						for(var i=0; i<list.length; i++){
							var li = "<li class='mui-table-view-cell mui-collapse-content mui-col-sm-6 mui-col-xs-6' v-for='item in items'>"+
										 "<img src='"+list[i].logo+"' class='project-img-back' onerror='this.src='static/mobile/images/defult_img.png';this.onerror=null;'/>"+
										 "<a href='app/mgoods/good_detail.do?id="+list[i].id+"'>"+
										 "<img class='mui-media-object mui-pull-right' src='static/mobile/images/project_-label.png'>"+
										 "<div class='mui-media-body'>"+
										 "<p>"+list[i].name+"</p>"+
										 "<span class='mui-left'>¥"+list[i].price+"</span>"+
										 "</div>"+
										 "</a>"+
										 "</li>";
							$("#goodsList").append(li);
						}
					}else{
						mui.toast("该分类暂无项目");
					}
				}
			});
		}
	</script>
	<script src="${pageContext.request.contextPath}/static/mobile/js/minghan.js" type="text/javascript"></script>
	<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
</body>
</html>
