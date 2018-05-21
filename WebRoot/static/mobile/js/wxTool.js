//$(document).ready(function(){
	var config = "weixin/getJsConfig";
	hideWXMenu(config);
//});
function hideWXMenu(wxConfigUrl){
	//获取微信配置参数
	$.getJSON(wxConfigUrl, {
	   "url": location.href.split('#')[0]
	  },
	  function(json, textStatus) {
		  //alert(json.code);
		  if(json.code == 0){
			  wx.config({
				  debug: false,  
				  appId: json.data.appId,  
				  timestamp:json.data.timestamp,  
				  nonceStr:json.data.nonceStr,  
				  signature:json.data.signature,  
				  jsApiList : [ 'hideOptionMenu','hideMenuItems','hideAllNonBaseMenuItem']  
			  });
			  wx.error(function(res) {  
				  //mui.alert("出错了：" + res.errMsg);  
			  });  
			  
			  wx.ready(function() {  
				  wx.hideAllNonBaseMenuItem();
			  });
			  return;
		  }
	  });
}