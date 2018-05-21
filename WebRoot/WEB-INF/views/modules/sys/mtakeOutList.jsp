<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品提现列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/mtakeout/list");
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/mtakeout/list">提现列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="takeout" action="${ctx}/sys/mtakeoutlist" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>用户名字：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
				<label>日期：</label>
				<input id="start1"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value='${takeout.startTime}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value='${takeout.endTime}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
			</li>
			<li><label>状态：</label>
				<span>
					<input id="indFlag1" name="state"  type="radio" value="0" 
						<c:if test="${takeout.state  eq '0'}"> checked="checked"</c:if> />
					<label for="indFlag1">审核中</label>
				</span>
				<span>
					<input id="indFlag2" name="state"  type="radio" value="1" 
						<c:if test="${takeout.state eq '1'}"> checked="checked"</c:if> />
					<label for="indFlag2">已结算</label>
				</span>
			</li>
			<li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li><button id="btnSubmit" class="btn btn-primary" type="button" onclick="method5('contentTable')">导出Excel</button></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<td>用户名字</td>
		<td>手机号</td>
		<td>银行名称</td>
		<td>银行卡号</td>
		<td>提现金额</td>
		<td>手续费</td>
		<td>实际打款</td>
		<td>发起时间</td>
		<td>结算时间</td>
		<td>结算人</td>
		<td>状态</td>
		</tr>
		<tbody>
		<c:forEach items="${page.list}" var="list">
			<tr>
				<td>${list.userName}</td>
				<td>-${list.userMobile }-</td>
				<td>${list.userBankType }</td>
				<td>-${list.userBankCard }-</td>
				<td>${list.money}</td>
				<td>${list.shouxu }</td>
				<td>${list.shiDa }</td>
				<td>${list.time }</td>
				<td>${list.remittanceTime }</td>
				<td>${list.remittanceGM }</td>
				<c:if test="${list.state=='0'}"><td><a  href="${ctx}/sys/mtakeout/transfer?id=${list.id}" onclick="return confirmx('确认结算该记录吗？', this.href)">结算</a></td></c:if>
				<c:if test="${list.state=='1'}"><td>已结算 </td></c:if>
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
		<td></td>
		<td></td>
		<td></td>
		<td>总金额：</td>
		<td>￥${bd }元</td>
		</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>