<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
   <title>写日记</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="static/mobile/css/mui.css" />
		<link rel="stylesheet" href="static/mobile/css/index.css?t=1" />
		<link rel="stylesheet" type="text/css" href="static/mobile/css/allCss.css?t=2" />
		<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
						<form class="mui-input-group upload-form" action="bd/publish.do"  accept-charset="UTF-8" method="post" enctype="multipart/form-data">
							<div class="before-photo-title">
								<p>上传术前照(最多4张)</p>
							</div>
							<div class="mui-table-view mui-grid-view mui-grid-9 mui-grid-before">
								<div class="img-box full">
									<div class="img-section">
										<div class="z_photo upimg-div clear mui-row">
											<div class="z_file fl mui-col-sm-3 mui-col-xs-3">
												<img src="static/mobile/images/iconfont-tianjia.png" class="add-img">
											   <!--  <input id="before" type="hidden" value="" name="photo"/>
												<input type="file" name="before[]" id="file-before" class="file-b" multiple="multiple" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" max="4" savePath="before"/>
											     -->
											    <input id="photoB" type="hidden" value="" name="photoB"/>
									            <%--<input  type="file" name="before[]" id="file-before" class="file-b" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="multiple" max="4" path="/diaryimg" savePathId="photoB"/>
									            --%>
									            <a href="javascript:void(0)" name="before[]" id="file-before" class="file-b" max="4" path="/diaryimg" savePathId="photoB">&nbsp;</a>
											</div>
											<div class="example-section z_file fl mui-col-sm-3 mui-col-xs-3">
												<!--					<img src="images/a7.png" class="example">-->
											</div>
										</div>
									</div>
								</div>
								<aside class="mask works-mask">
									<div class="mask-content">
										<p class="del-p ">您确定要删除该图片吗？</p>
										<p class="check-p"><span class="del-com wsdel-ok">确定</span><span class="wsdel-no">取消</span></p>
									</div>
								</aside>
				<div class="before-flag"><p>上传术前正面照</p></div>			
							</div>
							<div class="mui-full-back"></div>
							<div class="after-photo-title">
								<p>上传术后照(最多9张)</p>
							</div>
							<div class="mui-table-view mui-grid-view mui-grid-9 mui-grid-before">
								<div class="img-box full">
									<div class="img-section">
										<div class="z_photo upimg-div clear mui-row">
											<div class="z_file fl mui-col-sm-3 mui-col-xs-3">
												<img src="static/mobile/images/iconfont-tianjia.png" class="add-img">
											 	<input id="photoA" type="hidden" value="" name="photoA"/>
									            <%--<input  type="file" name="after[]" id="file-after" class="file-a" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="multiple" max="9" path="/diaryimg" savePathId="photoA"/>
									 	
											 --%>
											 	<a href="javascript:void(0)" name="after" id="file-after" class="file-a" max="9" path="/diaryimg" savePathId="photoA">&nbsp;</a>
											 </div>
											<div class="example-section z_file fl mui-col-sm-3 mui-col-xs-3">
											</div>
										</div>
									</div>
								</div>
								<aside class="mask works-mask">
									<div class="mask-content">
										<p class="del-p ">您确定要删除该图片吗？</p>
										<p class="check-p"><span class="del-com wsdel-ok">确定</span><span class="wsdel-no">取消</span></p>
									</div>
								</aside>
									<div class="after-flag"><p>上传术后正面照</p></div>	
							</div>
							
								<div class="mui-full-back"></div>
								<div class="mui-input-row mui-textarea">
									<textarea name="Diary" rows="8" cols="8" placeholder="记录下你的心得" class="textarea-dairy"></textarea>
								</div>
						<div class="dairy-btn"><button class="edit-commit" type="submit">提交</button></div>
						</form>
						<div class="dairy-flag">写日记分享变美过程就有机会获得日记奖励哦~</div>
						<div class="mui-line-height"></div>
					</div>
				</div>
				<div class="mui-off-canvas-backdrop"></div>
			</div>
		</div>
		<script type="text/javascript" src="static/jquery/jquery-1.9.1.min.js"></script>
		<script src="static/mobile/js/mui.min.js"></script>
		<script type="text/javascript" src="static/mobile/js/jweixin-1.2.0.js"></script>
		<script type="text/javascript" src="static/mobile/js/imgUpext.js?v=1"></script>
		<script>
			mui.init();
			var basePath = "${pageContext.request.contextPath}";
			
		</script>	
		<script type="text/javascript" src="static/mobile/js/minghan.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/mobile/js/wxBackRefresh.js"></script>
		<script type="text/javascript" src="static/mobile/js/wxTool.js"></script>
	</body>
</html>
