<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>门店列表</title>
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
		<li class="active"><a href="${ctx}/cmd/shop/list">门店列表</a>
		</li>
		<li><a href="${ctx}/cmd/shop/form">添加门店</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="shop"
		action="${ctx}/cmd/shop/list" method="post"
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
				<th>门店编号</th>
				<th>门店名</th>
				<th>门店地址</th>
				<th>经度</th>
				<th>维度</th>
				<th>门店电话</th>
				<th>二维码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="shop">
				<tr>
					<td>${shop.id}</td>
					<td>${shop.shopName}</td>
					<td>${shop.shopAddress}</td>
					<td>${shop.longitude}</td>
					<td>${shop.latitude}</td>
					<td>${shop.shopTel}</td>
					<td><img src="${shop.shopLogo}" width="50px" height="50px"></td>
					<td><a href="${ctx}/cmd/shop/form?id=${shop.id}">修改</a>
						<a href="${ctx}/cmd/shop/delete?id=${shop.id}"
						onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>