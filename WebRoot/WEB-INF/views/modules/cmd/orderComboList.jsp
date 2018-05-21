<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>套餐订单</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/cmd/orderCombo/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/orderCombo/list">套餐订单列表</a></li>
		<!-- <li><a>商品详情</a></li> -->
	</ul>
	<form:form id="searchForm" modelAttribute="orderCombo" action="${ctx}/cmd/orderCombo/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>套餐名：</label><form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>支付状态：</label>
				<span>
					<input id="payState1" name="payState"  type="radio" value="1" 
						<c:if test="${orderCombo.payState  eq '1'}"> checked="checked"</c:if> />
					<label for="payState1">已付款</label>
				</span>
				<span>
					<input id="payState2" name="payState"  type="radio" value="0" 
						<c:if test="${orderCombo.payState eq '0'}"> checked="checked"</c:if> />
					<label for="payState2">未付款</label>
				</span>
			</li>	
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>订单号</th><th>购买人</th><th>套餐名</th><th>成交价</th><th>奖励</th><th>支付状态</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderCombo">
			<tr>
				<td>${orderCombo.id}</td>
				<td><a href="${ctx}/commodity/particulars/userParticulas?id=${orderCombo.buyId}"> ${orderCombo.buyName}</a></td>
				<td><a href="${ctx}/cmd/orderCombo/particulas?id=${orderCombo.goodsId}"> ${orderCombo.goodsName}</a></td>
				<td>${orderCombo.dealPrice}</td>
				<%-- <td>
					<a href="${ctx}/commodity/particulars/userParticulas?id=${orderCombo.changePriceId}">
						<c:if test="${orderCombo.changePriceId != null && orderCombo.changePriceId != ''}" >${orderCombo.changePriceId}</c:if>
					</a>
					<c:if test="${orderCombo.changePriceId == null || orderCombo.changePriceId == ''}" >未改价</c:if>
				</td> --%>
				<td><a href="${ctx}/cmd/orderCombo/reward?id=${orderCombo.id}">点击查看详情</a></td>
				<%-- <td><a href="${ctx}/cmd/orderCombo/form?id=${orderCombo.id}"> ${orderCombo.payState  eq '1'?'已付款':'未付款'}</a> </td> --%>
				<td>${orderCombo.payState  eq '1'?'已付款':'未付款'}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>