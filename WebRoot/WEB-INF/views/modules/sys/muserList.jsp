<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/muser/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/muser/list">用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/muser/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>手机号：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
			<label class="control-label">用户星级:</label>
				<form:select path="mUserLevel" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>资格：</label>
				<span>
					<input id="indFlag1" name="flag"  type="radio" value="0" 
						<c:if test="${user.flag  eq '0'}"> checked="checked"</c:if> />
					<label for="indFlag1">未购买</label>
				</span>
				<span>
					<input id="indFlag2" name="flag"  type="radio" value="1" 
						<c:if test="${user.flag eq '1'}"> checked="checked"</c:if> />
					<label for="indFlag2">已购买</label>
				</span>
			</li>
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
		<th>姓名</th>
		<th>手机号</th>
		<th>身份证</th>
		<th>是否购买资格</th>
		<th>用户级别</th>
		<th>商品佣金</th>
		<th>注册时间</th>
		<th>推荐人</th>
		<!--<th>签名</th>--><shiro:hasPermission name="sys:muser:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.name}</td>
				<td>${user.mobile}</td>
				<td>${user.card}</td>
				<c:if test="${user.flag=='0' }"><td>未购买</td></c:if>
				<c:if test="${user.flag=='1' }"><td>已购买</td></c:if>
				<td>${user.mUserLevel}</td>
				<td>${user.mRewardMoney}</td>
				<td><fmt:formatDate value="${user.createDate }" type="date" pattern="yyyy-MM-dd"/></td>
				<td>${user.parentName }</td>
				<shiro:hasPermission name="sys:user:edit">
				<td>
				<a href="${ctx}/sys/muser/form?id=${user.id}">修改</a>&nbsp;&nbsp;
				<a href="${ctx}/sys/muser/subordinate?id=${user.id}">查看关系</a>&nbsp;&nbsp;
				<a href="${ctx}/sys/muser/parentform?id=${user.id}">推荐人设置</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>