<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现单</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/cmd/orderOut/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/orderOut/list">提现单列表</a></li>
		<!-- <li><a>商品详情</a></li> -->
	</ul>
	<form:form id="searchForm" modelAttribute="takeOut" action="${ctx}/cmd/orderOut/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>提现用户：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>审核状态：</label>
				<span>
					<input id="auditFlag1" name="auditFlag"  type="radio" value="1" 
						<c:if test="${takeOut.auditFlag  eq '1'}"> checked="checked"</c:if> />
					<label for="auditFlag1">已审核</label>
				</span>
				<span>
					<input id="auditFlag2" name="auditFlag"  type="radio" value="0" 
						<c:if test="${takeOut.auditFlag eq '0'}"> checked="checked"</c:if> />
					<label for="auditFlag2">未审核</label>
				</span>
			</li>	
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>提现单号</th><th>提现类型</th><th>提现用户</th><th>佣金提现金额</th><th>返现金额</th><th>审核人</th><th>审核状态</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="takeOut">
			<tr>
				<td>${takeOut.id}</td>
				<td>${takeOut.type eq '1' ? '12月返现' : '佣金提现'}</td>
				<td>${takeOut.userName}</td>
				<td>${takeOut.type eq '1' ? '---' : takeOut.brokerage}</td>
				<td>${takeOut.type eq '1' ? takeOut.back : '---'}</td>
				<td>${takeOut.auditName}</td>
				<td>${takeOut.auditFlag eq '1' ? '已审核' : '未审核'}</td>
				<!-- <td><a href="${ctx}/cmd/orderCombo/form?id=${orderCombo.id}">${takeOut.type  eq '1'?'已付款':'未付款'}</a></td>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>