<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="false" description="files、images、flash、thumb,upload"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="isImg" type="java.lang.Boolean" required="false" description="是否是图片"%>
<ol id="${input}Preview"></ol><c:if test="${!readonly}"><a href="javascript:" onclick="${input}FinderOpen();" class="btn">${selectMultiple?'添加':'选择'}</a>&nbsp;<a href="javascript:" onclick="${input}DelAll();" class="btn">清除</a></c:if>
<script type="text/javascript">
	function ${input}FinderOpen(){//<c:if test="${type eq 'thumb'}"><c:set var="ctype" value="images"/></c:if><c:if test="${type ne 'thumb'}"><c:set var="ctype" value="${type}"/></c:if>
		var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1),
		day = date.getDate()>9?date.getDate():"0"+date.getMonth();
		var url = "${ctxStatic}/ckfinder/ckfinder.html?type=${ctype}&start=${ctype}:${uploadPath}/"+year+month+"/"+day+
			"/&action=js&func=${input}SelectAction&thumbFunc=${input}ThumbSelectAction&cb=${input}Callback&dts=${type eq 'thumb'?'1':'0'}&sm=${selectMultiple?1:0}";
		windowOpen(url,"文件管理",1000,700);
		//top.$.jBox("iframe:"+url+"&pwMf=1", {title: "文件管理", width: 1000, height: 500, buttons:{'关闭': true}});
	}
	function ${input}SelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		var minUrl = "";
		for(var i=0; i<files.length; i++){//<c:if test="${type eq 'thumb'}">
			url += files[i].getThumbnailUrl();//</c:if><c:if test="${type ne 'thumb'}">
			//alert(files[i].getThumbnailUrl());//nameImage_min
			minUrl +=files[i].getThumbnailUrl();
			url += files[i].getUrl();//</c:if>
			if (i<files.length-1){
			 	url+=",";
			 	minUrl+=",";
			 }
		}
		var minInput = $("#${input}_min");
		//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val()==""?url:","+url));
		if(minInput && minInput.length>0){
			$("#${input}_min").val($("#${input}_min").val()+($("#${input}_min").val()==""?minUrl:"|"+minUrl));
		}
		//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);
		if(minInput && minInput.length>0){
			$("#${input}_min").val(minUrl);
		}
		
		//</c:if>
		${input}Preview();
		//top.$.jBox.close();
	}
	function ${input}ThumbSelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){
			url += files[i].getThumbnailUrl();
			if (i<files.length-1) url+=",";
		}
		//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:","+url));//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);//</c:if>
		${input}Preview();
		//top.$.jBox.close();
	}
	function ${input}Callback(api){
		ckfinderAPI = api;
	}
	function ${input}Del(obj){
		var url = $(obj).prev().attr("url");
		var minurl = $(obj).prev().attr("minurl");
		$("#${input}").val($("#${input}").val().replace(","+url,"","").replace(url+",","","").replace(url,"",""));
		var minInput = $("#${input}_min");
		if(minInput && minInput.length>0){
			$("#${input}_min").val($("#${input}_min").val().replace(","+minurl,"","").replace(minurl+",","","").replace(minurl,"",""));
		}
		${input}Preview();
	}
	function ${input}DelAll(){
		$("#${input}").val("");
		var minInput = $("#${input}_min");
		if(minInput && minInput.length>0){
			$("#${input}_min").val("");
		}
		${input}Preview();
	}
	function ${input}Preview(){
		var li, urls = $("#${input}").val().split(",");
		var minurls = $("#${input}").val().split(",");
		var minInput = $("#${input}_min");
		var minurls = [];
		if(minInput && minInput.length>0){
			minurls = $("#${input}_min").val().split(",");
		}
		$("#${input}Preview").children().remove();
		for (var i=0; i<urls.length; i++){
			
			if (urls[i]!=""){//<c:choose><c:when test="${isImg}">
				li = "<li><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" minurl=\""+(minurls.length == 0 ? "":minurls[i])+"\" style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">";//</c:when><c:otherwise>
				li = "<li><a href=\""+urls[i]+"\" url=\""+urls[i]+"\" minurl=\""+(minurls.length == 0 ? "":minurls[i])+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//</c:otherwise></c:choose>
				li += "&nbsp;&nbsp;<c:if test="${!readonly}"><a href=\"javascript:\" onclick=\"${input}Del(this);\">×</a></c:if></li>";
				$("#${input}Preview").append(li);
			}
		}
		if ($("#${input}Preview").text() == ""){
			$("#${input}Preview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
		}
	}
	${input}Preview();
</script>