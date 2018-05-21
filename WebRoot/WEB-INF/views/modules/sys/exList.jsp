<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值明细</title>
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
			$("#searchForm").attr("action","${ctx}/sys/exList/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<%--<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	--%>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/sys/recharge/list">用户列表</a></li>
		<li class="active"><a>明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="balance" action="${ctx}/sys/recharge/exListTime" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><%--<th>归属公司</th><th>归属部门</th>--%><%--<th class="sort-column login_name">登录名</th>--%>
			<th class="sort-column name">用户姓名</th>
		<th>金额</th>
		<th>说明</th>
		<th>时间</th>
		<th>操作人</th>
		<tbody>
		<c:forEach items="${page.list}" var="balance">
			<tr>
				<%--<td>${user.company.name}</td>
				<td>${user.office.name}</td>--%>
				<%--<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				--%>
			<td>${balance.userId}</td>
			<td>${balance.money }</td>
			<td>${balance.explain }</td>
			<td>${balance.time}</td>
			<td>${balance.operator }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>