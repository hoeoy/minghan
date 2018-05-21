<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/muser/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/muser/form?id=${user.id}">用户<shiro:hasPermission name="sys:muser:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:muser:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/muser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input name="loginFlag" type="hidden" value="1"/>
		<sys:message content="${message}"/>
			<div class="control-group">
			<label class="control-label">用户级别:</label>
			<div class="controls">
				<form:select path="mUserLevel" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否购买资格:</label>
			<div class="controls">
				<form:select path="flag" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('m_user_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:muser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>