<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/cmd/item/list");
		$("#searchForm").submit();
    	return false;
    }
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
		<li class="active"><a href="${ctx}/cmd/item/list">项目列表</a>
		</li>
		<li><a href="${ctx}/cmd/item/form">添加项目</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="item"
		action="${ctx}/cmd/item/list" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>项目名：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>项目介绍：</label>
			<form:input path="description" htmlEscape="false"
					maxlength="50" class="input-medium" />
			</li>
			<li><label>项目分类：</label>
				<form:select path="type" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${types}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
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
				<th>项目编号</th>
				<th>项目名称</th>
				<th>排序</th>
				<th>原价</th>
				<th>现价</th>
				<th>最低原价</th>
				<th>最高原价</th>
				<th>最低现价</th>
				<th>最高现价</th>
				<th>分类</th>
				<th>销售次数</th>
				<th>项目介绍</th>
				<th>启用状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item">
				<tr>
					<td>${item.number}</td>
					<td>${item.name}</td>
					<td>${item.sort}</td>
					<td>${item.oldPrice}</td>
					<td>${item.currentPrice}</td>
					<td>${item.oldMin}</td>
					<td>${item.oldMax}</td>
					<td>${item.currentMin}</td>
					<td>${item.currentMax}</td>
					<td>${item.typeName}</td>
					<td>${item.saleCount}</td>
					<td title="${item.description}">${fns:abbr(item.description,100)}</td>
					<td><a href="${ctx}/cmd/item/status?id=${item.id}">${item.status eq '1' ? '已上架↑' : '已下架↓'}</a></td>
					<td>
						<a href="${ctx}/cmd/item/form?id=${item.id}">修改</a>
						<a href="${ctx}/cmd/item/delete?id=${item.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>