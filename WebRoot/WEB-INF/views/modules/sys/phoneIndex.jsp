<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客服电话</title>
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
		<li class="active"><a href="${ctx}/sys/phone/index">客服电话</a></li>	
	</ul>
	<sys:message content="${message}"/>
	<form id="searchForm"  action="${ctx}/sys/phone/updatePhone" method="post" class="breadcrumb form-search" >
		<div class="print">
			<p>客服电话：</p>
			<p><input type="text" value="${phone }" name="phone"/></p>
			<input  class="btn btn-primary" type="submit" value="修改" />
		</div> 	
	</form>			
</body>
</html>