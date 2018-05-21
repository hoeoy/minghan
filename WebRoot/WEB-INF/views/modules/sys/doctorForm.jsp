<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/doctor/list">专家列表</a></li>
		<li class="active"><a href="${ctx}/sys/doctor/form?id=${doctor.id}">专家<shiro:hasPermission name="sys:doctor:view">${not empty doctor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:doctor:view">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm"  modelAttribute="doctor" action="${ctx}/sys/doctor/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input name="loginFlag" type="hidden" value="1"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<form:hidden id="nameImage_min" path="minPhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:uploader input="nameImage" isImg="true" type="upload" uploadPath="/doctor" selectMultiple="false" maxWidth="100" maxHeight="100" maxCount="1" containerWidth="200"/>
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位:</label>
			<div class="controls">
				<form:input path="post" htmlEscape="false" maxlength="200"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">介绍:</label>
			<div class="controls">
				<form:textarea path="intro" htmlEscape="false" rows="4" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否认证:</label>
			<div class="controls">
				<form:select path="attestationFlag" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('attestation_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div><%--
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		--%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:doctor:view"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>