<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务对账</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.rightText{
			text-align:right;
			width:195px;
		}
		.leftText{
			text-align:left;
		}
		.form-horizontal tr td{
			padding:5px;
		}		
		
		.print{ background: #fff;}
		.print h2{ height: 40px; line-height: 40px; text-align: center; font-size: 16px;}
		.print table{ width: 100%;  background: #FFF; font-size: 13px;border-collapse: collapse; }
		.print th,
		.print td{ padding: 10px 15px; text-align: left;border: 1px solid #e1e6eb; }
		.print p{ height: 30px; line-height: 30px; font-size: 12px; color: #888;}
			
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/cost/costIndex">财务对账</a></li>	
	</ul>
	<form:form id="searchForm" modelAttribute="cost" action="${ctx}/sys/cost/costIndex" method="post" class="breadcrumb form-search" >
		<ul class="ul-form">
			<li>
				<label>日期：</label>
				<input id="start1"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value="${cost.startTime }" type="date" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value="${cost.endTime }" type="date" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
				<%--<form:input type="text" id="start1" path="startTime" style="width:40%;display:inline-block;margin-right:6px;" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  /> 至   <form:input type="text" style="width:40%;display:inline-block;margin-left:8px;" id="start2" path="endTime" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  />
			--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<!-- <li class="btns"><input id="btnBack" class="btn btn-primary" type="button" value="返回分类列表" onclick="return goType();"/>
			</li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>			
		<div class="print">
			<table  >
				<tr>
					<td>业绩</td>
					<td>${cost.performance} 元</td>
				</tr>
				<tr>
					<td>佣金奖励</td>
					<td>${cost.brokerage} 元</td>
				</tr>
				<tr>
					<td>充值金额</td>
					<td>${cost.recharge} 元</td>
				</tr>
			</table>
		</div> 	
</body>
</html>