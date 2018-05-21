<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
	<script type="text/javascript">
		function page(n,s,isRePage){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/version/list"+(isRePage ? '?repage':''));
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/version/list">版本列表</a></li>
		<li><a href="${ctx}/sys/version/form?id=${version.id}">版本添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="version" action="${ctx}/invest/version/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>版本类型：</label>
				<form:select path="versionType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>版本名</th><th>版本号</th><th>版本类型</th><th>文件路径</th><th>内容</th><th>上传日期</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="version">
			<tr>
				<td><a href="${ctx}/sys/version/form?id=${version.id}">${version.versionName}</a></td>
				<td>${version.versionCode}</td>
				<td>${fns:getEnumLabel('1',version.versionType,  '无')}</td>
				<td>${version.fileName }</td>
				<td>${version.remarks }</td>
				<td><fmt:formatDate value="${version.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<%--<shiro:hasPermission name="sys:version:delete">
						--%>
						<a href="${ctx}/sys/version/delete?id=${version.id}" onclick="return confirmx('确认要删除该版本吗？', this.href)">删除</a>
					<%--</shiro:hasPermission>
				--%></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>