<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金明细</title>
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
			$("#searchForm").attr("action","${ctx}/sys/userBrokerage/list");
			$("#searchForm").submit();
	    	return false;
	    }
	    	    
	    var idTmr;  
        function  getExplorer() {  
            var explorer = window.navigator.userAgent ;  
            //ie  
            if (explorer.indexOf("MSIE") >= 0) {  
                return 'ie';  
            }  
            //firefox  
            else if (explorer.indexOf("Firefox") >= 0) {  
                return 'Firefox';  
            }  
            //Chrome  
            else if(explorer.indexOf("Chrome") >= 0){  
                return 'Chrome';  
            }  
            //Opera  
            else if(explorer.indexOf("Opera") >= 0){  
                return 'Opera';  
            }  
            //Safari  
            else if(explorer.indexOf("Safari") >= 0){  
                return 'Safari';  
            }  
        }  
        function method5(tableid) {  
            if(getExplorer()=='ie')  
            {  
                var curTbl = document.getElementById(tableid);  
                var oXL = new ActiveXObject("Excel.Application");  
                var oWB = oXL.Workbooks.Add();  
                var xlsheet = oWB.Worksheets(1);  
                var sel = document.body.createTextRange();  
                sel.moveToElementText(curTbl);  
                sel.select();  
                sel.execCommand("Copy");  
                xlsheet.Paste();  
                oXL.Visible = true;  
  
                try {  
                    var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");  
                } catch (e) {  
                    print("Nested catch caught " + e);  
                } finally {  
                    oWB.SaveAs(fname);  
                    oWB.Close(savechanges = false);  
                    oXL.Quit();  
                    oXL = null;  
                    idTmr = window.setInterval("Cleanup();", 1);  
                }  
  
            }  
            else  
            {  
                tableToExcel(tableid)  
            }  
        }  
        function Cleanup() {  
            window.clearInterval(idTmr);  
            CollectGarbage();  
        }  
        var tableToExcel = (function() {  
            var uri = 'data:application/vnd.ms-excel;base64,',  
                    template = '<html><head><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',  
                    base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },  
                    format = function(s, c) {  
                        return s.replace(/{(\w+)}/g,  
                                function(m, p) { return c[p]; }) }  
            return function(table, name) {  
                if (!table.nodeType) table = document.getElementById(table)  
                var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}  
                window.location.href = uri + base64(format(template, ctx))  
            }  
        })()  
	    </script>
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="brokerage" action="${ctx}/sys/userBrokerage/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>受益人：</label><form:input path="userId" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>贡献人：</label><form:input path="fromUserName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
				<label>日期：</label>
				<input id="start1"  name="start"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${brokerage.start}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="end"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${brokerage.end}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
				<%--<form:input type="text" id="start1" path="startTime" style="width:40%;display:inline-block;margin-right:6px;" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  /> 至   <form:input type="text" style="width:40%;display:inline-block;margin-left:8px;" id="start2" path="endTime" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  />
			--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<li><button id="btnSubmit" class="btn btn-primary" type="button" onclick="method5('contentTable')">导出Excel</button></li>
				<%--<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>--%>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td>受益者</td>
				<td>佣金类型</td>
				<td>佣金金额</td>
				<td>订单详情</td>
				<td>奖励等级</td>
				<td>贡献人</td>
				<td>购买商品</td>
				<td>时间</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="brokerage">
			<tr>
				<td>${brokerage.userName}</td>
				<td>${brokerage.status eq '0' ? '提现' : '获得'}</td>
				<td>${brokerage.money}</td>
				<td>
					<c:if test="${brokerage.orderType eq '2'}">
						<a href="${ctx}/cmd/orderItem/list?id=${brokerage.orderId}">NO.${brokerage.orderId}</a>
					</c:if>
					<c:if test="${brokerage.orderType eq '1'}">
						<a href="${ctx}/cmd/orderCombo/list?id=${brokerage.orderId}">NO.${brokerage.orderId}</a>
					</c:if>
					<c:if test="${brokerage.orderType eq '3'}">
						<a href="${ctx}/cmd/orderItem/list?id=${brokerage.orderId}">NO.${brokerage.orderId}</a>
					</c:if>
					<c:if test="${brokerage.orderType eq '0'}">
						<a href="${ctx}/cmd/orderOut/list?id=${brokerage.orderId}">NO.${brokerage.orderId}</a>
					</c:if>
				</td>
				<%-- <td>
					<c:if test="${brokerage.orderType eq '0'}">提现</c:if>
					<c:if test="${brokerage.orderType eq '1'}">买套餐</c:if>
					<c:if test="${brokerage.orderType eq '2'}">买项目</c:if>
					<c:if test="${brokerage.orderType eq '3'}">星级玩法获得</c:if>
				</td> --%>
				<td>
					<c:if test="${brokerage.status eq '1'}">
						<c:if test="${brokerage.star > 0}">${brokerage.star}星${brokerage.rewardType}</c:if>
						<c:if test="${brokerage.star < 1}">${brokerage.rewardType}</c:if>
					</c:if>
				</td>
				<td><c:if test="${brokerage.status eq '1'}">${brokerage.fromUserName}</c:if></td>
				<td><c:if test="${brokerage.status eq '1'}">${brokerage.orderName}</c:if></td>
				<td>${brokerage.time}</td>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>总金额：</td>
				<td>￥${allMoney }元</td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>