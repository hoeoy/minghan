<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日记图片</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	/* $(document).ready(
		function() {
			$("#tradeName").focus();
			$("#inputForm")
				.validate(
					{
						submitHandler : function(form) {
							loading('正在提交，请稍等...');
							form.submit();
						},
						errorContainer : "#messageBox",
						errorPlacement : function(error, element) {
							$("#messageBox").text("输入有误，请先更正。");
							if (element.is(":checkbox")
									|| element.is(":radio")
									|| element.parent().is(
											".input-append")) {
								error.appendTo(element.parent()
										.parent());
							} else {
								error.insertAfter(element);
							}
						}
					});
	}); */
</script>
<!-- <style type="text/css">
	/*去除下拉选中搜索栏*/
	.select2-search{
		display: none;
	}
</style> -->
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/diary/list">日记列表</a></li>
		<li class="active"><a href="${ctx}/sys/diary/showDiaryImages?diaryId=${diaryId}">日记图片</a></li>
		<%-- <li class="active"><a
			href="${ctx}/cmd/item/form?id=${item.id}">${not empty
				item.id?'修改':'添加'}项目</a>
		</li> --%>
	</ul>
	<br />
	<sys:message content="${message}" />

	<c:choose>
		<c:when test="${(before != null && fn:length(before)>0) && (after != null && fn:length(after)>0)}">
			<div class="control-group">
				<label class="control-label">术前照片:</label>
					<c:if test="${before != null && fn:length(before)>0}">
						<c:forEach items="${before}" var="b_img">
							<img src="${b_img.imgPath}" width="200px" height="200px">
						</c:forEach>
					</c:if>
			</div>
			<div class="control-group">
				<label class="control-label">术后照片:</label>
					<c:if test="${after != null && fn:length(after)>0}">
						<c:forEach items="${after}" var="a_img">
							<img src="${a_img.imgPath}" width="200px" height="200px">
						</c:forEach>
					</c:if>
			</div>
		</c:when>
		<c:otherwise>
			<h4>该日记没有上传图片</h4>
		</c:otherwise>
	</c:choose>
	
	<div class="form-actions">
		<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; -->
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
	</div>
</body>
</html>
