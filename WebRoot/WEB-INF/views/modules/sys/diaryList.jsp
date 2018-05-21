<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日记管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/diary/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/diary/list">日记列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/diary/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><%--<th>归属公司</th><th>归属部门</th>--%><%--<th class="sort-column login_name">登录名</th>--%>
		<th>作者名字</th>
		<th>日记内容</th>
		<th>发表时间</th>
		<!--<th>签名</th>--><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="diary">
			<tr>
				<%--<td>${user.company.name}</td>
				<td>${user.office.name}</td>--%>
				<%--<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				--%>
				<td>${diary.authorName}</td>
				<td>${diary.diary}</td>
				<td>${diary.time}</td>
				<%-- <td>${user.sign}</td>--%>
				<shiro:hasPermission name="sys:user:edit"><td>
					<a href="${ctx}/sys/diary/delete?id=${diary.id}" onclick="return confirmx('确认要删除该日记吗？', this.href)">删除</a>
					&nbsp;<a href="${ctx}/sys/diary/showDiaryImages?diaryId=${diary.id}">查看照片</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>