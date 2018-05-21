<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告位管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.select2-search{
			display: none;
		}
	</style>
	<script type="text/javascript">
		function page(n,s,isRePage){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/advert/list"+(isRePage ? '?repage':''));
			$("#searchForm").submit();
	    	return false;
	    }
		function goType(){
			var url = "${ctx}/sys/advert/listLocation?repage";
			window.location = url;
	    	return false;
	    }
		
		$(document).ready(function() {
		    $("input[name='sort']").blur(function(){
		        $.ajax({
				    url:'${ctx}/sys/advert/sort',
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
		<li class="active"><a href="${ctx}/sys/advert/list?locationType=${advert.locationType}">广告列表</a></li>
		<shiro:hasPermission name="sys:advert:add"><li><a href="${ctx}/sys/advert/form?locationType=${advert.locationType}">广告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="advert" action="${ctx}/invest/advert/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="locationType" id="locationType"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<%--<li><label>版本类型：</label>
				<form:select path="versionType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getEnumList('7')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>--%>
			<li class="btns"><input id="btnBack" class="btn btn-primary" type="button" value="返回分类列表" onclick="return goType();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column sort">排序</th><th>广告ID</th><th>图片</th><th>广告标题</th><th>关联ID</th><th>广告链接类型</th><th>点击链接</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="advert">
			<tr>
				<td><input type="text" value="${advert.sort}" name="sort"  style="width:50px;margin:5px 0px;" sid="${advert.id}"/></td>
				<td>${advert.id}</td>
				<td><img src="${advert.advertUrl}" width="100px" height="50px" style="height:50px;"/></td>
				<td>${advert.advertTitle}</td>
				<td>${advert.relateId}</td>
				<td>${fns:getEnumLabel('8',advert.advertType,  '无')}</td>
				<td>${advert.relateUrl}</td>
				<td>
					<shiro:hasPermission name="sys:advert:edit">
						<a href="${ctx}/sys/advert/form?id=${advert.id}">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:advert:delete">
						<a href="${ctx}/sys/advert/delete?id=${advert.id}&locationType=${advert.locationType}" onclick="return confirmx('确认要删除该广告吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>