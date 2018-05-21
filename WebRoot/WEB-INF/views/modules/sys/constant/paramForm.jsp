<%@page import="com.ks.utils.Config"%>
<%@page import="com.ks.utils.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统参数设置</title>
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
		<li class="active"><a href="${ctx}/sys/param/form">系统参数设置</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="poster"  class="form-horizontal" action="${ctx}/sys/param/save" method="post" autocomplete="off">
		<form:hidden path="id" />
		<sys:message content="${message}"/>
			<div class="control-group">
			<label class="control-label">海报:</label>
			<div class="controls">
				<form:hidden path="path" htmlEscape="false" maxlength="255"
					class="input-xlarge" />
				<sys:uploader input="path" isImg="true" type="upload"
					uploadPath="/Poster" selectMultiple="false" maxWidth="100"
					maxHeight="100" maxCount="1" containerWidth="200" />
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M.建议海报大小：640 × 1138（像素）</span>
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