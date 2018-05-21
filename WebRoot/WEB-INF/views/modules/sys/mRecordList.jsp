<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易明细</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/mrecord/list");
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
	<form:form id="searchForm" modelAttribute="record" action="${ctx}/sys/mrecord/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
		<li><label>商品名：</label><form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>用户姓名：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
		<li><label>用户电话：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
				<label>日期：</label>
				<input id="start1"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${record.startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${record.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
				<%--<form:input type="text" id="start1" path="startTime" style="width:40%;display:inline-block;margin-right:6px;" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  /> 至   <form:input type="text" style="width:40%;display:inline-block;margin-left:8px;" id="start2" path="endTime" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  />
			--%>
			</li>
			<li><label>筛选：</label>
				<span>
					<input id="indFlag1" name="goodsId"  type="radio" value="1" 
						<c:if test="${record.goodsId  eq '1'}"> checked="checked"</c:if> />
					<label for="indFlag1">只看特权</label>
				</span>
				<span>
					<input id="indFlag2" name="goodsId"  type="radio" value="" 
						<c:if test="${record.goodsId eq ''}"> checked="checked"</c:if> />
					<label for="indFlag2">只看商品</label>
				</span>
			</li>
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
			<td>订单号</td>
			<td>用户名字</td>
			<td>用户手机</td>
			<td>商品名字</td>
			<td>商品数量</td>
			<td>消费金额</td>
			<td>时间</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="list">
			<tr>
			<td>${list.orderNo }</td>
			<td>${list.userName }</td>
			<td>${list.mobile }</td>
			<td>${list.goodsName }</td>
			<td>${list.goodsNum }</td>
			<td>${list.goodsPrice }</td>
			<td>${list.time }</td>
			</tr>
		</c:forEach>
		</tbody>
		
		<tfoot class="table table-striped table-bordered table-condensed">
		<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>总金额：</td>
		<td>￥${allMoney }元</td>
		</tr>
		<tr></tr>
		<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>商品名字</td>
		<td>商品数量</td>
		<td>交易金额</td>
		</tr>
		<c:forEach items="${map }"   var="map">
			<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>${map.key }</td>
		<td>${map.value.nub }</td>
		<td>${map.value.money }</td>
			</tr>
		</c:forEach>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>