<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>套餐列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/cmd/combo/list");
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/combo/list">套餐列表</a>
		</li>
		<li><a href="${ctx}/cmd/combo/form">添加套餐</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="combo"
		action="${ctx}/cmd/combo/list" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>套餐名：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>套餐介绍：</label>
			<form:input path="description" htmlEscape="false"
					maxlength="50" class="input-medium" />
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
				<th>套餐编号</th>
				<th>套餐名</th>
				<th>原价</th>
				<th>现价</th>
				<th>销售次数</th>
				<th>套餐介绍</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="combo">
				<tr>
					<td>${combo.number}</td>
					<td>${combo.name}</td>
					<td>${combo.oldPrice}</td>
					<td>${combo.currentPrice}</td>
					<td>${combo.saleCount}</td>
					<td title="${combo.description}">${fns:abbr(combo.description,100)}</td>
					<td><a href="${ctx}/cmd/combo/form?id=${combo.id}">修改</a>
						<a href="${ctx}/cmd/combo/delete?id=${combo.id}"
						onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>