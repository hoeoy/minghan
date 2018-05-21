$(function() {
	var delParent;
	var defaults = {
		fileType : [ "jpg", "png", "bmp", "jpeg", "JPG" ], // 上传文件的类型
		fileSize : 1024 * 1024 * 10,
		maxsize:1024 * 800,//大于800kb需要压缩
		isCompress:true,
		uploadApiUrl:(basePath ? basePath : '')+'/app/upload/uploadBase64File',
		quality: 0.7
	// 上传文件的大小 10M
	};
	var num2 = $("#file-after").attr("max");
	var num=$("#file-before").attr("max");
	var path = $("#file-before").attr("path");
	var fileParamName = $("#file-before").attr("savePathId");
	var pathA = $("#file-after").attr("path");
	var fileParamNameA = $("#file-after").attr("savePathId");
	/* 点击图片的文本框 */
	if (num == 4) {
		$(".file-b").change(
						function() {
							var idFile = $(this).attr("id");
							var file = document.getElementById(idFile);
							var imgContainer = $(this).parents(".z_photo"); // 存放图片的父亲元素
							/* alert(imgContainer.length); */
							var fileList = file.files; // 获取的图片文件
							/*alert(fileList.value);*/
							var input = $(this).parent();// 文本框的父亲元素
							var imgArr = [];
							// 遍历得到的图片文件
							var numUp = imgContainer.find(".up-section").length;
							var totalNum = numUp + fileList.length; // 总的数量
							if (fileList.length > num || totalNum > num) {
								alert("上传图片数目不可以超过num个，请重新选择"); // 一次选择上传超过num个
								// 或者是已经上传和这次上传的到的总数也不可以超过num个
								return;
							} else if (numUp < num) {
								/* $(".file-b").setAttribute('type','file'); */
								fileList = validateUp(fileList);

								for ( var i = 0; i < fileList.length; i++) {
									/*alert(fileList[i]);*/
								
									var imgUrl = window.URL.createObjectURL(fileList[i]);
									imgArr.push(imgUrl);
									var $section = $("<div class='up-section fl mui-col-sm-3 mui-col-xs-3 loading' index='"
											+ i + "'>");
									imgContainer.prepend($section);
									var $span = $("<span class='up-span'>");
									$span.appendTo($section);
									var $img0 = $("<img class='close-upimg'>")
											.on("tap", function(event) {
												event.preventDefault();
												event.stopPropagation();
												$(".works-mask").show();
												delParent = $(this).parent();
											});
									$img0.attr("src",
											"static/mobile/images/a7.png")
											.appendTo($section);
									var $img = $("<img class='up-img up-opcity'>");
									$img.attr("src", imgArr[i]);
									$img.appendTo($section);
									var $p = $("<p class='img-name-p'>");
									$p.html(fileList[i].name)
											.appendTo($section);
									var $input = $("<input id='taglocation' name='taglocation' value='' type='hidden'>");
									$input.appendTo($section);
									var $input2 = $("<input id='tags' name='tags' value='' type='hidden'/>");
									$input2.appendTo($section);
									/* $(".file-b").attr('type', 'text'); */
								      ///my
									   photoCompress(fileList[i], {
						                    quality: defaults.quality
						                }, function(base64Codes){
						                	$.ajax({
						                        type : "POST",
						                        url : defaults.uploadApiUrl,
						                        // data : {'img' : imgArr,'type' : imgTypeArr},// 如果是上传多图就用这个方式
						                        data : {'img' : base64Codes,'middlePath' : path},
						                        success : function(res){
						                            if(res.code == 0){
						                            	mui.toast("上传成功");
						                            	//var filePathObj = $('#' + fileParamName);
						                            	var photo = $('#' + fileParamName).val();
						                            	var paths = res.data;
						                            	var sp = "";
						                            	if(photo != ''){
						                            		sp =",";
						                            	}
						                            	photo = paths.filePaths+sp+photo;
						                            	$('#' + fileParamName).val(photo);
						                            	return;
						                            }
						                            mui.alert("上传失败");
						                        }
						                    });
						                });
								/*	 var ready=new FileReader();
							            //    开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.
							                ready.readAsDataURL(fileList[i]);
							                ready.onload=function(){
							                    var re=this.result;
							                	$.ajax({
							                        type : "POST",
							                        url : defaults.uploadApiUrl,
							                        // data : {'img' : imgArr,'type' : imgTypeArr},// 如果是上传多图就用这个方式
							                        data : {'img' : re,'middlePath' : path},
							                        success : function(res){
							                            if(res.code == 0){
							                            	mui.toast("上传成功");
							                            	//var filePathObj = $('#' + fileParamName);
							                            	var photo = $('#' + fileParamName).val();
							                            	var paths = res.data;
							                            	var sp = "";
							                            	if(photo != ''){
							                            		sp =",";
							                            	}
							                            	photo = paths.filePaths+sp+photo;
							                            	$('#' + fileParamName).val(photo);
							                            	return;
							                            }
							                            mui.alert("上传失败");
							                        }
							                    });
							              };*/
									
									
								}

							}
							setTimeout(function() {
								$(".up-section").removeClass("loading");
								$(".up-img").removeClass("up-opcity");
							}, 400);
							numUp = imgContainer.find(".up-section").length;
							/*
							 * names=$(".file-b").attr("name"); alert(names);
							 */
							if (numUp >= num) {
								$(this).parent().hide();
							}
							// input内容清空
							$(this).val("");
							/* $(".file-b").attr('type', 'file'); */

						});
		function deletePath(obj) {
			var photo = $('#' + fileParamName).val();
			if (photo != '') {
				var ps = photo.split(",");
				var index = $(obj).attr("index");
				var result = "";
				for ( var i = 0; i < ps.length; i++) {
					if (ps.length - i != index + 1) {
						result += ps[i] + ",";
					}
				}
				var last = result.lastIndexOf(",");
				if (last > 0) {
					result = result.substring(0, last);
				}
				$('#' + fileParamName).val(result);
			}
		}

		$(".z_photo").delegate(".close-upimg", "tap", function() {
			$(".works-mask").show();
			delParent = $(this).parent();
		});

		$(".wsdel-ok").on("tap",function() {
			$(".works-mask").hide();
			var numUp = delParent.siblings().length;
			if (numUp < num + 1) {
				delParent.parent().find(".z_file").show();
			}
			delParent.remove();
			// 删除保存的路径
			deletePath(delParent);
		});

		$(".wsdel-no").on("tap",function() {
			$(".works-mask").hide();
		});

		function validateUp(files) {
			var arrFiles = [];// 替换的文件数组
			/*alert(files.name);*/
			for ( var i = 0, file; file = files[i]; i++) {
				// 获取文件上传的后缀名
				var newStr = file.name.split("").reverse().join("");
				if (newStr.split(".")[0] != null) {
					var type = newStr.split(".")[0].split("").reverse().join("");
					console.log(type + "===type===");
					if (jQuery.inArray(type, defaults.fileType) > -1) {
						// 类型符合，可以上传
						if (file.size >= defaults.fileSize) {
							alert(file.size);
							alert('您这个"' + file.name + '"文件大小过大');
						} else {
							// 在这里需要判断当前所有文件中
							arrFiles.push(file);
						}
					} else {
						alert('您这个"' + file.name + '"上传类型不符合');
					}
				} else {
					alert('您这个"' + file.name + '"没有类型, 无法识别');
				}
			}
			return arrFiles;
		}

	}

	/* 点击图片的文本框 */
	if (num2 == 9) {
		$(".file-a").change(
						function() {
							var idFile = $(this).attr("id");
							var file = document.getElementById(idFile);
							var imgContainer = $(this).parents(".z_photo"); // 存放图片的父亲元素
							/* alert(imgContainer.length); */
							var fileList = file.files; // 获取的图片文件
							/*alert(fileList.value);*/
							var input = $(this).parent();// 文本框的父亲元素
							var imgArr = [];
							// 遍历得到的图片文件
							var numUp = imgContainer.find(".up-section").length;
							var totalNum = numUp + fileList.length; // 总的数量
							if (fileList.length > num2 || totalNum > num2) {
								alert("上传图片数目不可以超过num个，请重新选择"); // 一次选择上传超过num2个
								// 或者是已经上传和这次上传的到的总数也不可以超过num个
								return;
							} else if (numUp < num2) {
								/* $(".file-b").setAttribute('type','file'); */
								fileList = validateUp(fileList);

								for ( var i = 0; i < fileList.length; i++) {
									/*alert(fileList[i]);*/
									
									
									var imgUrl = window.URL.createObjectURL(fileList[i]);
									imgArr.push(imgUrl);
									var $section = $("<div class='up-section fl mui-col-sm-3 mui-col-xs-3 loading' index='"
											+ i + "'>");
									imgContainer.prepend($section);
									var $span = $("<span class='up-span'>");
									$span.appendTo($section);
									var $img0 = $("<img class='close-upimg'>")
											.on("tap", function(event) {
												event.preventDefault();
												event.stopPropagation();
												$(".works-mask").show();
												delParent = $(this).parent();
											});
									$img0.attr("src",
											"static/mobile/images/a7.png")
											.appendTo($section);
									var $img = $("<img class='up-img up-opcity'>");
									$img.attr("src", imgArr[i]);
									$img.appendTo($section);
									var $p = $("<p class='img-name-p'>");
									$p.html(fileList[i].name)
											.appendTo($section);
									var $input = $("<input id='taglocation' name='taglocation' value='' type='hidden'>");
									$input.appendTo($section);
									var $input2 = $("<input id='tags' name='tags' value='' type='hidden'/>");
									$input2.appendTo($section);
									/* $(".file-b").attr('type', 'text'); */
								      ///my

									   photoCompress(fileList[i], {
						                    quality: defaults.quality
						                }, function(base64Codes){
						                	$.ajax({
						                        type : "POST",
						                        url : defaults.uploadApiUrl,
						                        // data : {'img' : imgArr,'type' : imgTypeArr},// 如果是上传多图就用这个方式
						                        data : {'img' : base64Codes,'middlePath' : pathA},
						                        success : function(res){
						                            if(res.code == 0){
						                            	mui.toast("上传成功");
						                            	//var filePathObj = $('#' + fileParamName);
						                            	var photo = $('#' + fileParamNameA).val();
						                            	var paths = res.data;
						                            	var sp = "";
						                            	if(photo != ''){
						                            		sp =",";
						                            	}
						                            	photo = paths.filePaths+sp+photo;
						                            	$('#' + fileParamNameA).val(photo);
						                            	return;
						                            }
						                            mui.alert("上传失败");
						                        }
						                    });
						                });
								/*	 var ready=new FileReader();
							            //    开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.
							                ready.readAsDataURL(fileList[i]);
							                ready.onload=function(){
							                    var re=this.result;
							                	$.ajax({
							                        type : "POST",
							                        url : defaults.uploadApiUrl,
							                        // data : {'img' : imgArr,'type' : imgTypeArr},// 如果是上传多图就用这个方式
							                        data : {'img' : re,'middlePath' : path},
							                        success : function(res){
							                            if(res.code == 0){
							                            	mui.toast("上传成功");
							                            	//var filePathObj = $('#' + fileParamNameA);
							                            	var photo = $('#' + fileParamNameA).val();
							                            	var paths = res.data;
							                            	var sp = "";
							                            	if(photo != ''){
							                            		sp =",";
							                            	}
							                            	photo = paths.filePaths+sp+photo;
							                            	$('#' + fileParamNameA).val(photo);
							                            	return;
							                            }
							                            mui.alert("上传失败");
							                        }
							                    });
							              };*/
									
									
								}

							}
							setTimeout(function() {
								$(".up-section").removeClass("loading");
								$(".up-img").removeClass("up-opcity");
							}, 450);
							numUp = imgContainer.find(".up-section").length;
							/*
							 * names=$(".file-b").attr("name"); alert(names);
							 */
							if (numUp >= num2) {
								$(this).parent().hide();
							}
							// input内容清空
							$(this).val("");
							/* $(".file-b").attr('type', 'file'); */

						});

		function deletePathA(obj) {
			var photo = $('#' + fileParamNameA).val();
			if (photo != '') {
				var ps = photo.split(",");
				var index = $(obj).attr("index");
				var result = "";
				for ( var i = 0; i < ps.length; i++) {
					if (ps.length - i != index + 1) {
						result += ps[i] + ",";
					}
				}
				var last = result.lastIndexOf(",");
				if (last > 0) {
					result = result.substring(0, last);
				}
				$('#' + fileParamNameA).val(result);
			}
		}

		$(".z_photo").delegate(".close-upimg", "tap", function() {
			$(".works-mask").show();
			delParent = $(this).parent();
		});

		$(".wsdel-ok").on("tap",function() {
			$(".works-mask").hide();
			var numUp = delParent.siblings().length;
			if (numUp < num2 + 1) {
				delParent.parent().find(".z_file").show();
			}
			delParent.remove();
			// 删除保存的路径
			deletePathA(delParent);
		});

		$(".wsdel-no").on("tap",function() {
			$(".works-mask").hide();
		});

		function validateUp(files) {
			var arrFiles = [];// 替换的文件数组
			/*alert(files.name);*/
			for ( var i = 0, file; file = files[i]; i++) {
				// 获取文件上传的后缀名
				var newStr = file.name.split("").reverse().join("");
				if (newStr.split(".")[0] != null) {
					var type = newStr.split(".")[0].split("").reverse().join("");
					console.log(type + "===type===");
					if (jQuery.inArray(type, defaults.fileType) > -1) {
						// 类型符合，可以上传
						if (file.size >= defaults.fileSize) {
							alert(file.size);
							alert('您这个"' + file.name + '"文件大小过大');
						} else {
							// 在这里需要判断当前所有文件中
							arrFiles.push(file);
						}
					} else {
						alert('您这个"' + file.name + '"上传类型不符合');
					}
				} else {
					alert('您这个"' + file.name + '"没有类型, 无法识别');
				}
			}
			return arrFiles;
		}

	}	
	
	
	
	 function photoCompress(file,w,objDiv){
         var ready=new FileReader();
         //    开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.
             ready.readAsDataURL(file);
             ready.onload=function(){
                 var re=this.result;
                 if(defaults.isCompress && file.size <= defaults.maxsize){
                 	objDiv(re);
                 	return;
 	        	}
                 canvasDataURL(re,w,objDiv);
             };
     }
     function canvasDataURL(path, obj, callback){
          var img = new Image();
          img.src = path;
          img.onload = function(){
           var that = this;
           // 默认按比例压缩
           var w = that.width,
            h = that.height,
            scale = w / h;
            w = obj.width || w;
            h = obj.height || (w / scale);
           var quality = w.quality || 0.7;  // 默认图片质量为0.7
           //生成canvas
           var canvas = document.createElement('canvas');
           var ctx = canvas.getContext('2d');
           // 创建属性节点
           var anw = document.createAttribute("width");
           anw.nodeValue = w;
           var anh = document.createAttribute("height");
           anh.nodeValue = h;
           canvas.setAttributeNode(anw);
           canvas.setAttributeNode(anh); 
           ctx.drawImage(that, 0, 0, w, h);
           // 图像质量
           if(obj.quality && obj.quality <= 1 && obj.quality > 0){
            quality = obj.quality;
           }
           // quality值越小，所绘制出的图像越模糊
           var base64 = canvas.toDataURL('image/jpeg', quality);
           // 回调函数返回base64的值
           callback(base64);
         }
     }
     /**  
      * 将以base64的图片url数据转换为Blob  
      * @param urlData  
      *            用url方式表示的base64图片数据  
      */  
    function convertBase64UrlToBlob(urlData){  
         var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
             bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
         while(n--){
             u8arr[n] = bstr.charCodeAt(n);
         }
         return new Blob([u8arr], {type:mime});
     }
    
    /*********************my*********************/
    
    var imgTypeArr = new Array();
    var imgArr = new Array();
    var isHand = 0;// 1正在处理图片
    var base64Img = '';
    var nowImgType = "image/jpeg";
    var uploadApiUrl = basePath+"/app/upload/uploadBase64File";
    
    function compress (source_img_obj, imgType) {
        source_img_obj.onload = function() {
            var cvs = document.createElement('canvas');
            var scale = this.height / this.width;
            cvs.width = 640;
            cvs.height = 640 * scale;
            var ctx = cvs.getContext("2d");
            ctx.drawImage(this, 0, 0, cvs.width, cvs.height);
            var newImageData = cvs.toDataURL(imgType, 0.8);
            base64Img = newImageData;
            //预览图
            //var img = new Image();
            //img.src = newImageData;
            //$(img).css('width', 100 + 'px');
            //$(img).css('height', 100 + 'px');
            //$("#canvasDiv").append(img);
            
            isHand = 0;
            catUpload();
        };
    };
    
    function handleFileSelect(evt) {
 	    isHand = 1;
 	    imgArr = [];
 	    imgTypeArr = [];
 	    $("#canvasDiv").html("");
 	    var files = evt.target.files;
 	    for (var i = 0, f; f = files[i]; i++) {
 	        // Only process image files.
 	        if (!f.type.match('image.*')) {
 	            continue;
 	        }
 	        imgTypeArr.push(f.type);
 	        nowImgType = f.type;
 	        var reader = new FileReader();
 	        // Read in the image file as a data URL.
 	        reader.readAsDataURL(f);
 	        // Closure to capture the file information.
 	        reader.onload = (function(theFile) {
 	            return function(e) {
 	                var i = new Image();
 	                i.src = e.target.result;
 	                jic.compress(i, nowImgType);
 	            };
 	        })(f);
 	    }
 	}
    
    function catUpload() {
 	    if (base64Img == "") {
 	        callbackCanvsUpload({'code':-1,'data':'','msc':'您还没有选择图片'});
 	        return false;
 	    }
 	    if (isHand == 1) {
 	        callbackCanvsUpload({'code':-1,'data':'','msc':'请等待图片处理完毕！'});
 	        return;
 	    }
 	    
 	    canvsalert('图片正在处理上传中···');
 	    
 	    $.ajax({
 	        type : "POST",
 	        url : uploadApiUrl,
 	        // data : {'img' : imgArr,'type' : imgTypeArr},// 如果是上传多图就用这个方式
 	        data : {'img' : base64Img,'middlePath' : path},
 	        success : function(res){
 	            callbackCanvsUpload(res);
 	        }
 	    });
 	}
 	function canvsalert(msg) {
 	    var style = "display: block; width: 92%;padding:4%; height: 100%; z-index: 10; position: fixed; text-align: center; top: 0px; background: rgba(1,1,1,0.8); color: #fff; padding-top: 200px; font-size: 1em;line-height:1.5em;left:0;";
 	    var str = '<div id="canvsalertc" style="' + style + '">' + msg + '</div>';
 	    $("body").append(str);
 	    setTimeout('$("#canvsalertc").remove()', 3000);
 	}

});
