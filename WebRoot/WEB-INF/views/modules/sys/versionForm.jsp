<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本管理</title>
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
					var type = $("#versionType").val();
					var file = $("#fileName").val();
					if(type == '0'){
						if(file == null || file ==''){
							alertx("请上传APP文件");
							return;
						}
						if(file.indexOf('.apk') == -1){
							alertx("请上传apk格式的文件");
							return;
						}
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
			changeVersionType();
		});
		function changeVersionType(){
			var type = $("#versionType").val();
			if(type == '1'){
				$("#fileName2").attr("disabled",false);
				$(".ios").first().show();
				$(".android").first().hide();
				$("#fileName").attr("disabled",true);
			}else{
				$(".ios").first().hide();
				$("#fileName2").attr("disabled",true);
				$(".android").first().show();
				$("#fileName").attr("disabled",false);
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/version/list">版本列表</a></li>
		<li class="active"><a href="${ctx}/sys/version/form?id=${version.id}">版本${empty version.id ? '添加':'查看'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="version" action="${ctx}/sys/version/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">版本类型:</label>
			<div class="controls">
				<form:select path="versionType" class="input-medium" onchange="changeVersionType()">
					<form:options items="${fns:getEnumList('1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本名:</label>
			<div class="controls">
				<form:input path="versionName" htmlEscape="false" maxlength="20" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号:</label>
			<div class="controls">
				<form:input path="versionCode" htmlEscape="false" maxlength="10" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group android">
			<label class="control-label">APP文件:</label>
			<div class="controls">
				<%--<input type="file" name="apkFile"  class="required"/>
				--%>
				<form:hidden id="fileName" path="fileName" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<%--<sys:ckfinder input="fileName" isImg="false" type="upload" uploadPath="/app" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				--%>
				<sys:uploader input="fileName" isImg="false" type="upload" uploadPath="/app" selectMultiple="false" maxWidth="100" maxHeight="100" maxCount="1" containerWidth="200" fileType="apk" fileSingleSizeLimit="10"/>
				<span class="help-inline"><font color="red">*</font>备注：上传文件格式为apk,鼠标放在图片上点击“删除图标”删除,大小不超过10M</span>
			</div>
		</div>
		<div class="control-group ios">
			<label class="control-label">文件路径:</label>
			<div class="controls">
				<form:input id="fileName2" path="fileName" htmlEscape="false" maxlength="50" class="required input-xlarge" style="width:350px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">
				<form:textarea path="remarks" maxlength="2000" style="width:350px;height:100px;"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:version:add"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>