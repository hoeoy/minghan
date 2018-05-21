<%@page import="com.ks.utils.Config"%>
<%@page import="com.ks.utils.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>注册协议设置</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		ol li{
			margin:5px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler: function(form){
				$("#type").val('1');
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
		<li class="active"><a href="${ctx}/sys/param/protocolForm">注册协议设置</a></li>
	</ul>
	<form:form id="inputForm"  class="form-horizontal" action="${ctx}/sys/param/protocolForm" method="post" autocomplete="off">
		<sys:message content="${message}"/>
		<input id="type" name="type" value="0" type="hidden"/>
		<div class="control-group">
			<label class="control-label">协议:</label>
			<div class="controls">
				<textarea name="protocol" style="width:700px;height:500px;" class="required">${protocol }</textarea>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<shiro:hasPermission name="sys:param:edit">
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</div>
		</shiro:hasPermission>
	</form:form>
</body>
</html>