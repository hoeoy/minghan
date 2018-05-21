<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目管理</title>
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
<style type="text/css">
	/*去除下拉选中搜索栏*/
	.select2-search{
		display: none;
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cmd/item/list">商品列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/cmd/item/form?id=${item.id}">${not empty
				item.id?'修改':'添加'}商品</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="item"
		action="${ctx}/cmd/item/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">项目编号:</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原价:</label>
			<div class="controls">
				<form:input path="oldPrice" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现价:</label>
			<div class="controls">
				<form:input path="currentPrice" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">最低原价:</label>
			<div class="controls">
				<form:input path="oldMin" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高原价:</label>
			<div class="controls">
				<form:input path="oldMax" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最低现价:</label>
			<div class="controls">
				<form:input path="currentMin" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高现价:</label>
			<div class="controls">
				<form:input path="currentMax" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类:</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge">
					<form:option value="" label="${item.type}"/>
					<form:options items="${types}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">门店:</label>
			<div class="controls">
				<form:select path="shopId" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${shops}" itemLabel="shopName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">项目Logo:</label>
			<div class="controls">
				<form:hidden path="logo" htmlEscape="false" maxlength="255"
					class="input-xlarge" />
				<%--<sys:ckfinder input="pictureLogo" isImg="true" type="upload" uploadPath="/goods"/>
				<span class="help-inline">建议Logo大小：1000 × 145（像素）</span>
				
				--%>
				<sys:uploader input="logo" isImg="true" type="upload"
					uploadPath="/itemLogo" selectMultiple="false" maxWidth="100"
					maxHeight="100" maxCount="1" containerWidth="200" />
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M.建议Logo大小：1000
					× 145（像素）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目内容:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4"
					maxlength="200" class="input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目介绍:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4"
					maxlength="200" class="input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品滚动图:</label>
			<div class="controls">
				<form:hidden path="imagesLocation" htmlEscape="false" maxlength="1000"
					class="input-xlarge" />
				<%--<sys:ckfinder input="eLogo" isImg="true" type="upload" selectMultiple="true" uploadPath="/goods" />
				--%>
				<sys:uploader input="imagesLocation" isImg="true" type="upload"
					uploadPath="/itemImage" selectMultiple="true" maxWidth="100"
					maxHeight="100" maxCount="10" containerWidth="600" />
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M,最多上传10张.建议大小：1000
					× 800（像素）</span>
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
