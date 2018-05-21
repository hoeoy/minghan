<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目订单</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/cmd/orderItem/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/orderItem/list">项目订单列表</a></li>
		<!-- <li><a>商品详情</a></li> -->
	</ul>
	<form:form id="searchForm" modelAttribute="orderItem" action="${ctx}/cmd/orderItem/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>项目名：</label><form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>支付状态：</label>
				<span>
					<input id="payState1" name="payState"  type="radio" value="1" 
						<c:if test="${orderItem.payState  eq '1'}"> checked="checked"</c:if> />
					<label for="payState1">已付款</label>
				</span>
				<span>
					<input id="payState2" name="payState"  type="radio" value="0" 
						<c:if test="${orderItem.payState eq '0'}"> checked="checked"</c:if> />
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
		<thead><tr>
			<th>订单号</th>
			<th>购买人</th>
			<th>项目名</th>
			<th>成交价</th>
			<th>改价人</th>
			<th>改价审核状态</th>
			<th>奖励</th>
			<th>支付状态</th>
			<!-- <th>备注</th> -->
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderItem">
			<tr>
				<td>${orderItem.id}</td>
				<%-- <td><a href="${ctx}/commodity/particulars/userParticulas?id=${orderItem.buyId}"> ${orderItem.buyName}</a></td> --%>
				<td>${orderItem.buyName}</a></td>
				<td><a href="${ctx}/cmd/orderItem/particulas?id=${orderItem.goodsId}"> ${orderItem.goodsName}</a></td>
				<td>${orderItem.dealPrice}</td>
				<td>
						<c:if test="${orderItem.changePriceId != null && orderItem.changePriceId != ''}" >${orderItem.changePriceName}</c:if>
					<%-- <a href="${ctx}/commodity/particulars/userParticulas?id=${orderItem.changePriceId}">
					</a> --%>
					<c:if test="${orderItem.changePriceId == null || orderItem.changePriceId == ''}" >未改价</c:if>
				</td>
				<td>${orderItem.checkState  eq '1'?'已审核':'未审核'}</td>
				<td><a href="${ctx}/cmd/orderItem/reward?id=${orderItem.id}">点击查看详情</a></td>
				<%-- <td><a href="${ctx}/cmd/orderItem/form?id=${orderItem.id}"> ${orderItem.payState  eq '1'?'已付款':'未付款'}</a> </td> --%>
				<td>${orderItem.payState  eq '1'?'已付款':'未付款'}</td>
				<%-- <td>${orderItem.text}</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>