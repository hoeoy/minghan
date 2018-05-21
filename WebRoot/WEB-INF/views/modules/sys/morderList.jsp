<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品订单列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/morder/list");
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
		<li class="active"><a href="${ctx}/sys/morder/list">订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/sys/morder/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>用户姓名：</label><form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>商品名：</label><form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>联系电话：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
				<label>日期：</label>
				<input id="start1"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value='${order.startTime}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="<fmt:formatDate value='${order.endTime}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
			</li>
			<li>
			<label class="control-label">订单状态:</label>
				<form:select path="orderState" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li><button id="btnSubmit" class="btn btn-primary" type="button" onclick="method5('contentTable')">导出Excel</button></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th class="sort-column name">订单号</th>
		<th>购买人</th>
		<th>商品名称</th>
		<th>商品数量</th>
		<th>消费金额</th>
		<th>联系人</th>
		<th>联系电话</th>
		<th>收货地址</th>
		<th>快递公司名称</th>
		<th>快递单号</th>
		<th>发货时间</th>
		<th>支付交易号</th>
		<th>支付时间</th>
		<th>订单状态</th>
		<shiro:hasPermission name="sys:morder:edit"><th>操作</th></shiro:hasPermission>
		<th>领取状况</th>
		</tr>
		
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="list">
			<tr>
			<td>-${list.orderNo }-</td>
			<td>${list.userName}</td>
			<td>${list.goodsName }</td>
			<td>${list.goodsNum }</td>
			<td>${list.goodsPrice }</td>
			<td>${list.linkman}</td>
			<td>-${list.mobile }-</td>
			<td>${list.address }</td>
			<td>${list.expressName }</td>
			<td>-${list.expressNo }-</td>
			<td>${list.sendTime }</td>
			<td>-${list.tradeNo }-</td>
			<td>${list.payTime }</td>
			<td>${list.orderState }</td>
				<shiro:hasPermission name="sys:morder:edit"><td>
    				<c:if test="${list.orderState == '待发货' }">
					<a href="${ctx}/sys/morder/form?id=${list.id}">发货</a>
					</c:if>
					<c:if test="${list.orderState == '待收货' }">
					<a href="${ctx}/sys/morder/form?id=${list.id}">修改物流信息</a>
					</c:if>
				</td></shiro:hasPermission>
				 <td>
				 <c:if test="${list.goodsId == '1'}">
				 <c:if test="${list.getFlag ==0 }">
				<a href="${ctx}/sys/morder/getFlag?id=${list.id}">确认领取</a>
				</c:if>
				<c:if test="${list.getFlag ==1 }">
				已领取
				</c:if>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>