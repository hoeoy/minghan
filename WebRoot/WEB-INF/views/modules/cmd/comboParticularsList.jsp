<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>套餐详情</title>
<meta name="decorator" content="default" />
<style type="text/css">
.rightText {
	text-align: right;
	width: 95px;
}

.leftText {
	text-align: left;
}

.form-horizontal tr td {
	padding: 5px;
}
</style>
<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/cmd/combo/particularList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/combo/particularList">项目详情列表</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="combo"
		action="${ctx}/cmd/combo/particularList" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>套餐名：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>套餐介绍：</label>
			<form:input path="description" htmlEscape="false"
					maxlength="50" class="input-medium" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable" class="form-horizontal">
		<tbody>
			<c:forEach items="${page.list}" var="combo">
				<tr style="border-bottom:1px solid #ccc;padding:10px;">
					<td>
						<table class="form-horizontal" style="margin:10px;">
							<tr>
								<td class="rightText">商品名称：</td>
								<td>${combo.name}</td>
								<td class="rightText">现价：</td>
								<td>${combo.currentPrice}</td>
							</tr>
							<!-- <tr>
						</tr> -->
							<tr>
								<td class="rightText">套餐介绍：</td>
								<td colspan="3" class="leftText">${combo.description}</td>
							</tr>
							<tr>
								<td class="rightText">图片Logo：</td>
								<td class="leftText"><c:choose>
										<c:when
											test="${combo.logo != null && combo.logo!=''}">
											<img src="${combo.logo}" width="100px"
												height="100px">
										</c:when>
										<c:otherwise>无</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="rightText">套餐滚动图片：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${combo.images == null || combo.images.size()==0}">
									无
								</c:if> <c:forEach items="${combo.images}" var="comboImages">
										<c:choose>
											<c:when
												test="${comboImages.image != null && comboImages.image!=''}">
												<img src="${comboImages.image}" width="100px"
													height="100px" style="margin:5px 15px;">
											</c:when>
											<c:otherwise>无</c:otherwise>
										</c:choose>
									</c:forEach></td>
							</tr>
							<tr>
								<td class="rightText">套餐合同图片：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${combo.contracts == null || combo.contracts.size()==0}">
									无
								</c:if> <c:forEach items="${combo.contracts}" var="comboContracts">
										<c:choose>
											<c:when
												test="${comboContracts.contract != null && comboContracts.contract!=''}">
												<img src="${comboContracts.contract}" width="100px"
													height="100px" style="margin:5px 15px;">
											</c:when>
											<c:otherwise>无</c:otherwise>
										</c:choose>
									</c:forEach></td>
							</tr>
						</table></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>