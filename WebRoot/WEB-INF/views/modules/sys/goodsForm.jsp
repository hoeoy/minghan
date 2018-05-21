<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品管理</title>
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
		<li><a href="${ctx}/sys/mgoods/list">商品列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/sys/mgoods/form?id=${goods.id}">${not empty
				goods.id?'修改':'添加'}商品</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="goods"
		action="${ctx}/sys/mgoods/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">商品名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品介绍:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4"
					maxlength="200" class="input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原价:</label>
			<div class="controls">
				<form:input path="market" htmlEscape="false" maxlength="200"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现价:</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="200"
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
					<form:option value="" label="${goods.type}"/>
					<form:options items="${types}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品Logo:</label>
			<div class="controls">
				<form:hidden path="logo" htmlEscape="false" maxlength="255"
					class="input-xlarge" />
				<sys:uploader input="logo" isImg="true" type="upload"
					uploadPath="/goodsLogo" selectMultiple="false" maxWidth="100"
					maxHeight="100" maxCount="1" containerWidth="200" />
				<span class="help-inline">鼠标放在图片上点击“删除图标”删除,单张图片不超过1M.建议Logo大小：1000
					× 145（像素）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品滚动图:</label>
			<div class="controls">
				<form:hidden path="imagesLocation" htmlEscape="false" maxlength="1000"
					class="input-xlarge" />
				<sys:uploader input="imagesLocation" isImg="true" type="upload"
					uploadPath="/goodsImage" selectMultiple="true" maxWidth="100"
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
