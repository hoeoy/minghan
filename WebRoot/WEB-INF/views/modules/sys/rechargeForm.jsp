<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值入口</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/recharge/list">用户列表</a></li>
		<li class="active"><a>充值入口</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/recharge/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input name="loginFlag" type="hidden" value="1"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="200" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证:</label>
			<div class="controls">
				<form:input path="card" htmlEscape="false" maxlength="100" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额:</label>
			<div class="controls">
				<form:input path="accountMoney" htmlEscape="false" maxlength="200" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值金额:</label>
			<div class="controls">
				<input type="text"  maxlength="20" name="money"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值说明:</label>
			<div class="controls">
				<textarea name="explain" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"> </textarea>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:recharge:view"><input id="btnSubmit" class="btn btn-primary" type="submit" value="充 值" onclick="alert('确认充值？')"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>