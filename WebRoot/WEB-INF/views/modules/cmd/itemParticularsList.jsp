<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目详情</title>
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
			$("#searchForm").attr("action","${ctx}/cmd/item/particularList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cmd/item/particularList">项目详情列表</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="item"
		action="${ctx}/cmd/item/particularList" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<%-- 	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/> --%>
		<ul class="ul-form">
			<li><label>项目名：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"
					class="input-medium" />
			</li>
			<li><label>项目介绍：</label>
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
			<c:forEach items="${page.list}" var="item">
				<tr style="border-bottom:1px solid #ccc;padding:10px;">
					<td>
						<table class="form-horizontal" style="margin:10px;">
							<tr>
								<td class="rightText">商品名称：</td>
								<td>${item.name}</td>
								<td class="rightText">现价：</td>
								<td>${item.currentPrice}</td>
							</tr>
							<!-- <tr>
						</tr> -->
							<tr>
								<td class="rightText">项目介绍：</td>
								<td colspan="3" class="leftText">${item.description}</td>
							</tr>
							<tr>
								<td class="rightText">图片Logo：</td>
								<td class="leftText"><c:choose>
										<c:when
											test="${item.logo != null && item.logo!=''}">
											<img src="${item.logo}" width="100px"
												height="100px">
										</c:when>
										<c:otherwise>无</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="rightText">项目滚动图片：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${item.images == null || item.images.size()==0}">
									无
								</c:if> <c:forEach items="${item.images}" var="itemImages">
										<c:choose>
											<c:when
												test="${itemImages.itemImage != null && itemImages.itemImage!=''}">
												<img src="${itemImages.itemImage}" width="100px"
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