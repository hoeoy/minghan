<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告设置</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
	<script type="text/javascript">
		/*function page(n,s,isRePage){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/advert/listLocation"+(isRePage ? '?repage':''));
			$("#searchForm").submit();
	    	return false;
	    }*/
	    $(document).ready(function() {
		    $("input[name='sort']").blur(function(){
		        $.ajax({
				    url:'${ctx}/sys/advert/sortLocation',
				    type:'POST', //GET
				    async:true,    //或false,是否异步
				    data:{sort:$(this).val(),id:$(this).attr('sid')},
				    success:function(data){
				    	if(data.code == 0){
	                        window.location.reload();
	                    }else{
	                    	alertx(data.msg);
	                    	return false;
	                    }
				    }
				});
		    });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/advert/listLocation">广告分类列表</a></li>
		<shiro:hasPermission name="sys:location:add"><li><a href="${ctx}/sys/advert/locationForm">广告分类添加</a></li></shiro:hasPermission>
	</ul>
	<%--<form:form id="searchForm" modelAttribute="version" action="${ctx}/invest/version/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>版本类型：</label>
				<form:select path="versionType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('7')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column sort">排序</th><th>广告分类ID</th><th>广告分类名称</th><th>广告分类标识</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="locationType">
			<tr>
				<td><input type="text" value="${locationType.sort}" name="sort" style="width:50px;margin-bottom:0;" sid="${locationType.id}"/></td>
				<td>${locationType.id}</td>
				<td>${locationType.name}</td>
				<td>${locationType.module}</td>
				<td>
					<shiro:hasPermission name="sys:advert:view">
						<a href="${ctx}/sys/advert/list?locationType=${locationType.id}">广告位管理</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>