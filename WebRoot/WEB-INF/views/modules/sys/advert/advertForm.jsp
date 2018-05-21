<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var sort = $("#sort").val();
					if(!checknum(sort)){
						alertx("排序请输入整数");
						return;
					}
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
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
		<li><a href="${ctx}/sys/advert/list?locationType=${advert.locationType}">广告列表</a></li>
		<li class="active"><a href="${ctx}/sys/advert/form?id=${advert.id}&locationType=${advert.locationType}">广告<shiro:hasPermission name="sys:advert:edit">${empty advert.id ? '添加':'修改'}</shiro:hasPermission><shiro:lacksPermission name="sys:advert:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="advert" action="${ctx}/sys/advert/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="locationType"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="advertUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<%--<sys:ckfinder input="nameImage" isImg="true" type="upload" uploadPath="/advert" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			--%>
				<sys:uploader input="nameImage" isImg="true" type="upload" uploadPath="/advert" selectMultiple="false" maxWidth="100" maxHeight="100" maxCount="1" containerWidth="200"/>
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,当单张图片不超过1M</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告标题:</label>
			<div class="controls">
				<form:input path="advertTitle" htmlEscape="false" maxlength="100" class="required input-xxlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告类型:</label>
			<div class="controls">
				<form:select path="advertType" class="input-medium">
					<option value="">请选择</option>
					<form:options items="${fns:getEnumList('8')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联ID:</label>
			<div class="controls">
				<form:input path="relateId" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
				<%--<span class="help-inline"><font color="red">*</font> </span>
			--%></div>
		</div>
		<div class="control-group">
			<label class="control-label">点击链接:</label>
			<div class="controls">
				<form:input path="relateUrl" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
				<%--<span class="help-inline"><font color="red">*</font> </span>
			--%>
			</div>
		</div>
		<div class="control-group ios">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="10" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:advert:add"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>