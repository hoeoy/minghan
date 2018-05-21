$(function(){
	var delParent;
	var basepath = (basePath ? basePath:'');
	var defaults = {
		fileType         : ["jpg","png","bmp","jpeg"],   // 上传文件的类型
		fileSize         : 1024 * 1024 * 10 ,                 // 上传文件的大小 10M
		maxsize:1024 * 800,//大于800kb需要压缩
		isCompress:true,
		uploadApiUrl:basepath+'/app/upload/uploadWxjsFile',
		quality: 0.7
	};
	
	//var num=$("#file-before").attr("max");
	//var path = $("#file-before").attr("path");
	//var fileParamName = $("#file-before").attr("savePathId");
	
	//var num2 = $("#file-after").attr("max");
	//var pathA = $("#file-after").attr("path");
	//var fileParamNameA = $("#file-after").attr("savePathId");
	
	 //获取微信配置参数
	$.getJSON(basepath+"/weixin/getJsConfig", {
	   "url": location.href.split('#')[0]
	  },
	  function(json, textStatus) {
		  if(json.code == 0){
			  //bossId = json.data.bossId;
			  wx.config({
				  debug: false,  
				  appId: json.data.appId,  
				  timestamp:json.data.timestamp,  
				  nonceStr:json.data.nonceStr,  
				  signature:json.data.signature,  
				  jsApiList : [ 'checkJsApi','chooseImage','uploadImage']  
			  //jsApiList : [ 'checkJsApi', 'scanQRCode' ]  
			  });
			  wx.error(function(res) {  
				  mui.alert("出错了：" + res.errMsg);  
			  });  
			  
			  wx.ready(function() {  
				  /*wx.checkJsApi({  
					  jsApiList : ['chooseImage','uploadImage'],  
					  success : function(res) {  
						  
					  }  
				  }); */ 
			  }); //end_ready
			  return;
		  }
		  mui.alert("获取微信配置信息失败");
	  });
	// 拍照、本地选图
		var images_b = {
		    localId: [],
		    serverId: [],
		    index:0
		}; 
		var images_a = {
				localId: [],
				serverId: [],
				index:0
		}; 
	// 图片接口
	//if (num == 4) {
		$(".file-b,.file-a").on('tap',function(){
			var obj = this;
			var imgContainer = $(obj).parents(".z_photo"); //存放图片的父亲元素
	 		var numUp = imgContainer.find(".up-section").length;
	 		var num = $(obj).attr("max");
	 		if(numUp >= num){
	 			$(obj).parent().hide();
	 		}
			var count = num > 9 ? 9:num;
			var id = $(obj).attr("id");
			wx.chooseImage({
		  	  count: count, // 默认9
		  	  sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		  	  sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		  	  success: function (res) {
		  		//alert(res.localIds);
		  		var i=0,length = 0;
		  		if(id == "file-before"){
		  			images_b.localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		  			length = images_b.localId.length;
		  	  	}else{
		  	  		images_a.localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		  	  		length = images_a.localId.length;
		  	  	}
		  		function upload() { 
		  			var path = $(obj).attr("path");
		  			//alert(path);
		  			var localId = "";
		  			if(id == "file-before"){
		  				localId = images_b.localId[i];
		  			}else{
		  				localId = images_a.localId[i];
		  			}
		  			wx.uploadImage({
		  				localId:localId ,
		  				// 需要上传的图片的本地ID，由chooseImage接口获得
		  				isShowProgressTips: 1, // 默认为1，显示进度提示
		  				success: function (res) {
		  					var serverId = res.serverId; // 返回图片的服务器端ID
		  					i++; 
		  					if(id == "file-before"){
		  						images_b.serverId.push(serverId);
				  			}else{
				  				images_a.serverId.push(serverId);
				  			}
		  					//alert(serverId);
		  					$.ajax({
		  						type : "POST",
		  						url : defaults.uploadApiUrl,
		  						data : {'wxfileId' : serverId,'middlePath' : path},
		  						success : function(result){
		  							//alert(result);
		  							if(result.code == 0) {
		  								// mui.toast("上传成功");
		  								//success(resp.data);
		  								setHtmlSrc(obj,result.data,i);
		  								
		  								if (i < length) { 
		  									if(id == "file-before"){
		  							  			upload(obj,images_b);
		  							  	  	}else{
		  							  	  		upload(obj,images_a);
		  							  	  	} 
		  								}
		  							}else{
		  								mui.alert("上传失败");
		  							}
		  						},
		  						error:function(resp) {
		  							mui.alert(resp.msg);
		  						}
		  					});
		  				}
		  			});
		  		}
		  		if(id == "file-before"){
		  			upload(obj,images_b);
		  	  	}else{
		  	  		upload(obj,images_a);
		  	  	}
	      		 
		  	  }
		    }); 
		});
	//}
	function setHtmlSrc(obj,data,i){
		var imgContainer = $(obj).parents(".z_photo"); //存放图片的父亲元素
		 var imgSrc = data.filePaths;
		 var $section = $("<section class='up-section fl mui-col-sm-3 mui-col-xs-3' index='"+i+"' loc='"+$(obj).attr("savePathId")+"'>");
		     imgContainer.prepend($section);
		 var $span = $("<span class='up-span'>");
		     $span.appendTo($section);
		
	     var $img0 = $("<img class='close-upimg'>").on("tap",function(event){
			    event.preventDefault();
				event.stopPropagation();
				$(".works-mask").show();
				delParent = $(this).parent();
				
			});   
			$img0.attr("src","static/mobile/images/a7.png").appendTo($section);
	     var $img = $("<img class='up-img'>");
	         $img.attr("src",imgSrc);
	         $img.appendTo($section);
	    var paramName = $(obj).attr("savePathId");
    	var photo = $('#' + paramName).val();
    	//var paths = res.data;
    	var sp = "";
    	if(photo != ''){
    		sp =",";
    	}
    	photo = imgSrc+sp+photo;
    	$('#' + paramName).val(photo);
    	
    	//$(".up-section").removeClass("loading");
 	    //$(".up-img").removeClass("up-opcity");
    	return;
	      
	}
	function deletePath(obj,delIndex){
		if(delIndex<0)
			return;
		var paramName = $(obj).attr("loc");
		var photo = $('#' + paramName).val();
		if(photo !=''){
			var ps = photo.split(",");
			//var index = $(obj).attr("index");
			var result = "";
			for(var i=0;i<ps.length;i++){
				if(ps.length - i != delIndex+1){
					result += ps[i]+",";
				}
			}
			var last = result.lastIndexOf(",");
			if(last>0){
				result = result.substring(0, last);
			}
			$('#' + paramName).val(result);
		}
	}
   $(".z_photo").delegate(".close-upimg","click",function(){
     	  $(".works-mask").show();
     	  delParent = $(this).parent();
	});
		
	$(".wsdel-ok").on('tap',function(){
		$(".works-mask").hide();
		//保存删除的位置
		var delIndex = -1;//从图片末尾开始数
		delParent.parent().find(".up-section").each(function(index,item){
			if($(this).attr("index") == delParent.attr("index")){
				delIndex = index;
			}
		});
		 delParent.remove();
		
		//删除保存的路径
		deletePath(delParent,delIndex);
	});
	
	$(".wsdel-no").on('tap',function(){
		$(".works-mask").hide();
	});
})
