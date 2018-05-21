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
			$("#searchForm").attr("action","${ctx}/sys/userScore/list");
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
	<form:form id="searchForm" modelAttribute="score" action="${ctx}/sys/userScore/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="userId" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li>
				<label>日期：</label>
				<input id="start1"  name="startTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${reconciliation.startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择开始日期"/>
				至
				<input id="start2"  name="endTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:190px;"
			value="${reconciliation.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" placeholder="选择结束日期"/>
				<%--<form:input type="text" id="start1" path="startTime" style="width:40%;display:inline-block;margin-right:6px;" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  /> 至   <form:input type="text" style="width:40%;display:inline-block;margin-left:8px;" id="start2" path="endTime" onClick="WdatePicker()" class="form-control" placeholder="点击选择日期"  />
			--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<%--<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>--%>
			</li>
			<li><button id="btnSubmit" class="btn btn-primary" type="button" onclick="method5('contentTable')">导出Excel</button></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><%--<th>归属公司</th><th>归属部门</th>--%><%--<th class="sort-column login_name">登录名</th>--%>
		<th>用户姓名</th>
		<th>类型</th>
		<th>积分</th>
		<th>套餐/项目</th>
		<th>关联订单编号</th>
		<th>最后修改时间</th>
		<tbody>
		<c:forEach items="${page.list}" var="score">
			<tr>
				<td>${score.userName}</td>
				<td>${score.status eq '0' ? '使用' : '获得'}</td>
				<td>${score.point}</td>
				<td>${score.goodsName}</td>
				<td>
					<c:if test="${score.orderType eq '1'}">
						<a href="${ctx}/cmd/orderItem/list?id=${score.orderId}">NO.${score.orderId}</a>
					</c:if>
					<c:if test="${score.orderType eq '0'}">
						<a href="${ctx}/cmd/orderCombo/list?id=${score.orderId}">NO.${score.orderId}</a>
					</c:if>
				</td>
				<td>${score.stringUpdateDate}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>