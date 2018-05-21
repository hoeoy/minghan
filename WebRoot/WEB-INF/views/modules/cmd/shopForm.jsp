<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>门店列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
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
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cmd/shop/list">项目列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/cmd/shop/form?id=${shop.id}">${not empty
				shop.id?'修改':'添加'}项目</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="shop"
		action="${ctx}/cmd/shop/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<%-- <div class="control-group">
			<label class="control-label">门店编号:</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false" maxlength="200"
					class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red"></font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">门店名称:</label>
			<div class="controls">
				<form:input path="shopName" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门店电话:</label>
			<div class="controls">
				<form:input path="shopTel" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">门店地址:</label>
			<div class="controls">
				<form:textarea path="shopAddress" htmlEscape="false" rows="4"
					maxlength="200" class="input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度:</label>
			<div class="controls">
				<form:input path="longitude" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">维度:</label>
			<div class="controls">
				<form:input path="latitude" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<!-- <label class="control-label">门店Logo:</label> -->
			<label class="control-label">门店二维码:</label>
			<div class="controls">
				<form:hidden path="shopLogo" htmlEscape="false" maxlength="255"
					class="input-xlarge" />
				<%--<sys:ckfinder input="pictureLogo" isImg="true" type="upload" uploadPath="/goods"/>
				<span class="help-inline">建议Logo大小：1000 × 145（像素）</span>--%>
				<sys:uploader input="shopLogo" isImg="true" type="upload"
					uploadPath="/shopLogo" selectMultiple="false" maxWidth="100"
					maxHeight="100" maxCount="1" containerWidth="200" />
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M.建议Logo大小：145
					× 145（像素）</span>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button"
				value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>
