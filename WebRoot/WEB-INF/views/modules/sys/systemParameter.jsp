<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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
		<li><a href="${ctx}/sys/dict/systemParameter">系统参数设置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="systemParameter" action="${ctx}/sys/dict/updateSysParameter" method="post" class="form-horizontal">
	 	<form:hidden path="defaultMusicList.id"/> 
	 	<form:hidden path="defaultPadipataList.id"/> 
	 	<form:hidden path="copyrightList.id"/> 
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">善堂默认音乐:</label>
			<div class="controls">
				<form:input path="defaultMusicList.value" htmlEscape="false" maxlength="200" class="input-xlarge required" style="width:500px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">默认回向文内容:</label>
			<div class="controls">
				<form:textarea path="defaultPadipataList.value" htmlEscape="false" rows="4" maxlength="700" class="input-xxlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">音乐的版权声明:</label>
			<div class="controls">
				<form:textarea path="copyrightList.value" htmlEscape="false" rows="4" maxlength="700" class="input-xxlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>