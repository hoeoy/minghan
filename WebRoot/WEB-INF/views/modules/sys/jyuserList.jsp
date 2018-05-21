<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/jyuser/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/jyuser/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/jyuser/list">用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/jyuser/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>手机号：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
			<label class="control-label">用户类型:</label>
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
		<th>用户类型</th>
		<th>用户级别</th>
		<th>形象大使</th>
		<th>余额</th>
		<th>佣金</th>
		<th>返现金额</th>
		<th>积分</th>
		<th>业绩</th>
		<th>注册时间</th>
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
				<%-- <td>${user.roleNames}</td>--%>
				<td>${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</td>
				<td>${fns:getDictLabel(user.userLevel, 'sys_user_level', '无')}</td>
				<td>${user.userAmbassador }</td>
				<td>${user.accountMoney}</td>
				<td>${user.rewardMoney}</td>
				<td>${user.backMoney}</td>
				<td>${user.point}</td>
				<td>${user.performance }</td>
				<td><fmt:formatDate value="${user.createDate }" type="date" pattern="yyyy-MM-dd"/></td>
				<%-- <td>${user.sign}</td>--%>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/jyuser/form?id=${user.id}">修改</a>
    				<c:if test="${user.startFlag=='0' }">
					<a href="${ctx}/sys/jyuser/delete?id=${user.id}&flag=0" onclick="return confirmx('确认要禁用该用户吗？', this.href)">禁用</a>
					</c:if>
					<c:if test="${user.startFlag=='1' }">
					<a href="${ctx}/sys/jyuser/delete?id=${user.id}&flag=1" onclick="return confirmx('确认要启动该用户吗？', this.href)">启动</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>