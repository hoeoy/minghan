<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目套餐信息</title>
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
		<li><a href="${ctx}/cmd/orderItem/list">项目订单列表</a>
		</li>
		<li class="active"><a>项目信息</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="item"
		action="${ctx}/cmd/orderItem/particulars" method="post"
		class="form-horizontal">
		<table id="contentTable">
			<tbody>
				<tr>
					<td>
						<table class="form-horizontal" style="margin:10px;">
							<tr>
								<td class="rightText">项目名称：</td>
								<td>${item.name}</td>
							</tr>
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
								<td class="rightText">滚动图：</td>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td colspan="3" class="leftText"><c:if
										test="${item.images == null || item.images.size()==0}">
										无
									</c:if> <c:forEach items="${item.images}" var="img">
										<c:choose>
											<c:when
												test="${img.itemImage != null && img.itemImage !=''}">
												<img src="${img.itemImage}" width="100px"
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