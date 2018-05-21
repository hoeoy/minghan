<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推荐人管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.list-name{
			float: right;
			width: 30px;
			height: 15px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/parentId/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/parentId/form?id=${user.id}">推荐人设置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/parentId/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input name="loginFlag" type="hidden" value="1"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="200"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证:</label>
			<div class="controls">
				<form:input path="card" htmlEscape="false" maxlength="100"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐人:</label>
			<div class="controls">
				<form:input path="parentName" htmlEscape="false" maxlength="100"  id="parebr-name"/>
				<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  				选择推荐人
				</button>
			</div>
		</div>
		
		<div class="control-group" style="display:none;">
			<div class="controls">
				<form:input path="parentId" htmlEscape="false" maxlength="100"  id="parebr-id"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		--%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">用户列表</h4>
      </div>
      <div class="modal-body">
	      <p>
				<input type="text"  id="user-name" name="user-name" class="input-large"  style="margin-bottom: auto;">
	        	 <button type="button" class="btn btn-primary" onclick="findUser();">搜索</button>
	      </p>
        	 <p></p>
        	 <div>
        	 <ul id ="top-ul">
        	 	<c:forEach items="${list}" var="list">
        	 	<li ><a href="javascript:getId('${list.name }','${list.id }' );">姓名:${list.name }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号:${list.mobile}</a></li>
        	 	</c:forEach>
        	 	</ul>
        	 </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
	function findUser(){
		var name = $("#user-name").val();
		var url = "${ctx}/sys/parentId/findUserList.do";
		$.ajax({
		url:url,
		type:'POST',
		data:{name:name},
		dataType: 'json',
		success:function(result){
		var s = result.data;
		 if(s && s.length > 0){
		 $("#top-ul").empty();
		 for (var i in s) {
			 var html = "<li ><a href='javascript:getId(\""+s[i].name+"\",\""+s[i].id+"\" );'>姓名:"+s[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号:"+s[i].mobile+"</a></li>";
			 $("#top-ul").append(html);        
			 }
		 }
		}
		})
	}
	
	function getId(data,daata){
	$("#parebr-name").attr("value",data);
	$("#parebr-id").attr("value",daata);
	$("#myModal").modal('hide'); 
	}
</script>
</body>
</html>