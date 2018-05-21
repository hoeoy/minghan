<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>发货管理</title>
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
		<li><a href="${ctx}/sys/morder/list">订单列表</a>
		</li>
		<li class="active"><a href="${ctx}/sys/morder/form?id=${order.id}">物流管理</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/sys/morder/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">快递公司名称:</label>&emsp;
				<form:select path="expressName" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('express_name')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">快递单号:</label>
			<div class="controls">
				<form:input path="expressNo" htmlEscape="false" maxlength="200" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:morder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
				value="保 存" /></shiro:hasPermission>&nbsp; <input id="btnCancel" class="btn" type="button"
				value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>
