<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>奖励套餐信息</title>
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
		<li class="active"><a>奖励详情</a>
		</li>
	</ul>
	<form:form id="searchForm" modelAttribute="vo"
		action="${ctx}/cmd/orderCombo/particulars" method="post"
		class="form-horizontal">
		<table id="contentTable">
			<tbody>
				<tr>
					<td>
						<table class="form-horizontal" style="margin:10px;">
							<tr>
								<td class="rightText">消费产品：</td>
								<td>${vo.orderCombo.goodsName}</td>
							</tr>
							<tr>
								<td class="rightText">消费金额：</td>
								<td class="leftText">${vo.orderCombo.dealPrice}</td>
							</tr>
							<tr>
								<td class="rightText">购买人：</td>
								<td>${vo.orderCombo.buyName}</td>
							</tr>
							<c:forEach items="${vo.rewards}" var="reward">
								<c:if test="${reward.rewardType == '3'}">
									<tr>
										<td class="rightText">套餐一级分销：</td>
									</tr>
									<tr>
										<td class="rightText">受益人：</td>
										<td>${reward.userName}</td>
										<td>奖励金额：</td>
										<td>${reward.userReward}</td>
									</tr>
								</c:if>
							</c:forEach>
							<tr>
								<td class="rightText">星级玩法：</td>
							</tr>
							<c:forEach items="${vo.rewards}" var="reward">
								<c:if test="${reward.rewardType == '2'}">
									<tr>
										<td class="rightText">星级：</td>
										<td>${reward.userStar}</td>
										<td>受益人：</td>
										<td>${reward.userName}</td>
										<td>奖励金额：</td>
										<td>${reward.userReward}</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</td>
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