<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目分类列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/cmd/item/itemTypeList");
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/item/itemTypeList">项目分类列表</a>
		</li>
		<li><a href="${ctx}/cmd/item/itemTypeForm">添加项目分类</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="itemType"
		action="${ctx}/cmd/item/itemTypeList" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>项目分类：</label>
			<form:input path="typeName" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目分类编号</th>
				<th>项目分类名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="itemType">
				<tr>
					<td>${itemType.id}</td>
					<td>${itemType.typeName}</td>
					<td><a href="${ctx}/cmd/item/itemTypeForm?id=${itemType.id}">修改&nbsp;</a>
						<a href="${ctx}/cmd/item/itemTypeDelete?id=${itemType.id}"
						onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>