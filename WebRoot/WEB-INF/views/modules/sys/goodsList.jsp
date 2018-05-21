<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/mgoods/list");
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
		<li class="active"><a href="${ctx}/sys/mgoods/list">商品列表</a>
		</li>
		<li><a href="${ctx}/sys/mgoods/form">添加商品</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="goods"
		action="${ctx}/sys/mgoods/list" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>商品名：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>商品分类：</label>
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
				<th>商品名称</th>
				<th>排序</th>
				<th>原价</th>
				<th>现价</th>
				<th>分类</th>
				<th>销售次数</th>
				<th>启用状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="goods">
				<tr>
					<td>${goods.name }</td>
					<td>${goods.sort }</td>
					<td>${goods.market }</td>
					<td>${goods.price }</td>
					<td>${goods.type }</td>
					<td>${goods.sellNum }</td>
					<td><a href="${ctx}/sys/mgoods/status?id=${goods.id}">${goods.putFlag eq '1' ? '已上架↑' : '已下架↓'}</a></td>
					<td>
						<a href="${ctx}/sys/mgoods/form?id=${goods.id}">修改</a>
						<c:if test="${goods.id ne '1' }"><shiro:hasPermission name="sys:mgoods:edit"><a href="${ctx}/sys/mgoods/delete?id=${goods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a></shiro:hasPermission></c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>