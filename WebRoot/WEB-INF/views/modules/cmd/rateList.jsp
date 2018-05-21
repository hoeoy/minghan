<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>提现分销费率列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/cmd/shop/list");
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/orderOut/rateList">比例列表</a></li>
		<%-- <li><a href="${ctx}/cmd/shop/form">添加门店</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="rate"
		action="${ctx}/cmd/orderOut/rateLis" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<%-- <li><label>门店名：</label>
			<form:input path="shopName" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>门店地址：</label>
			<form:input path="shopAddress" htmlEscape="false"
					maxlength="50" class="input-medium" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /></li>
			<li class="clearfix"></li> --%>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>类型</th>
				<th>比例（%）</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rates}" var="rate">
				<tr>
					<td>${rate.id}</td>
					<td>${rate.description}</td>
					<td>${rate.value*100}%</td>
					<td>
						<a href="${ctx}/cmd/orderOut/rateForm?id=${rate.id}">修改</a>
						<%-- <a href="${ctx}/cmd/shop/delete?id=${shop.id}"onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>