<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目分类</title>
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
		<li><a href="${ctx}/cmd/item/itemTypeList">项目分类列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/cmd/item/itemTypeForm?id=${itemType.id}">${not empty
				itemType.id?'修改':'添加'}项目分类</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="itemType"
		action="${ctx}/cmd/item/itemTypeSave" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<%-- <div class="control-group">
			<label class="control-label">项目分类编号:</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false" maxlength="200"
					class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red"></font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">项目分类名称:</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
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
