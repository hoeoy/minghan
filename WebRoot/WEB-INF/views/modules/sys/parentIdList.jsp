<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐人管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/parentId/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/parentId/list">用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/parentId/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>手机号：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<%--<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>--%>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><%--<th>归属公司</th><th>归属部门</th>--%><%--<th class="sort-column login_name">登录名</th>--%>
		<th class="sort-column name">姓名</th>
		<th>手机号</th>
		<th>身份证</th>
		<th>用户类型</th>
		<th>用户级别</th>
		<%--<th>用户id</th>
		--%>
		<th>推荐人</th>
		<!--<th>签名</th>--><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<%--<td>${user.company.name}</td>
				<td>${user.office.name}</td>--%>
				<%--<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				--%>
				<td>${user.name}</td>
				<%--<td>${user.phone}</td>
				--%>
				<td>${user.mobile}</td>
				<td>${user.card}</td>
				<td>${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</td>
				<td>${fns:getDictLabel(user.userLevel, 'sys_user_level', '无')}</td>
				<%--<td>${user.id}</td>
				--%>
				<td>${user.parentId}</td>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/parentId/form?id=${user.id}">推荐人设置</a>
    				<a href="${ctx}/sys/parentId/subordinate?id=${user.id}">查看关系</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>