<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>套餐信息</title>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cmd/orderCombo/list">套餐订单列表</a>
		</li>
		<li class="active"><a>套餐信息</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="combo"
		action="${ctx}/cmd/orderCombo/particulars" method="post"
		class="form-horizontal">
		<table id="contentTable">
			<tbody>
				<tr>
					<td>
						<table class="form-horizontal" style="margin:10px;">
							<tr>
								<td class="rightText">套餐名称：</td>
								<td>${combo.name}</td>
							</tr>
							<tr>
								<td class="rightText">商品介绍：</td>
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
								<td class="rightText">滚动图：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${combo.images == null || combo.images.size()==0}">
										无
									</c:if> <c:forEach items="${combo.images}" var="img">
										<c:choose>
											<c:when
												test="${img.image != null && img.image !=''}">
												<img src="${img.image}" width="100px"
													height="100px" style="margin:5px 15px;">
											</c:when>
											<c:otherwise>无</c:otherwise>
										</c:choose>
									</c:forEach></td>
							</tr>
							<tr>
								<td class="rightText">合同图：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${combo.contracts == null || combo.contracts.size()==0}">
										无
									</c:if> <c:forEach items="${combo.contracts}" var="img">
										<c:choose>
											<c:when
												test="${img.contract != null && img.contract !=''}">
												<img src="${img.contract}" width="100px"
													height="100px" style="margin:5px 15px;">
											</c:when>
											<c:otherwise>无</c:otherwise>
										</c:choose>
									</c:forEach></td>
							</tr>
						</table></td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>