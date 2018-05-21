<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="false" description="files、images、flash、thumb,upload"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%-- <%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%> --%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<%@ attribute name="containerWidth" type="java.lang.String" required="false" description="容器宽度"%>
<%@ attribute name="isImg" type="java.lang.Boolean" required="false" description="是否是图片"%>
<%@ attribute name="maxCount" type="java.lang.String" required="false" description="图片最大个数"%>
<%@ attribute name="fileSingleSizeLimit" type="java.lang.Integer" required="false" description="单个图片最大size"%>
<%@ attribute name="fileType" type="java.lang.String" required="false" description="文件类型"%>


    <%-- <link rel="stylesheet" type="text/css" href="${ctxStatic}/uploader/syntax.css"> --%>
    <%-- <link rel="stylesheet" type="text/css" href="${ctxStatic}/uploader/style.css"> --%>
    
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/uploader/webuploader.css">
    
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/uploader/demo.css">
    <script type="text/javascript" src="${ctxStatic}/uploader/webuploader.js"></script>
    <%-- <script type="text/javascript" src="${ctxStatic}/uploader/global.js"></script> --%>

    <div id="uploader_${input}" class="wu-example" style="width:${empty containerWidth ? 300:containerWidth}px">
    <div class="queueList">
        <div id="dndArea_${input}" class="placeholder" style="${not empty fileType && fileType eq 'apk' ? 'background:none;':''}">
            <div id="filePicker_${input}" class="webuploader-container" style="width:76px;margin:auto;"><div class="webuploader-pick">选择</div><div id="rt_rt_1bl9k9vpn198v1c2e1n4h1omi2f1" style="position: absolute; top: 0px; left: 448px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file_${input}" class="webuploader-element-invisible" multiple="multiple"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></div>
             <!-- <div class="index">或将文件拖到这里，单次最多可选300张</div> -->
        </div>
    <!-- <ul class="filelist"></ul> --></div>
    <div class="statusBar" style="display:none;">
        <div class="btns">
            <div id="filePicker2_${input}" class="webuploader-container-null"><div class="webuploader-pick selectFile" style="padding: 0 10px;">选择</div><div id="rt_rt_1bl9k9vpsoh71g6k2to13s3t4f6" style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;"><input type="file" name="file_${input}" class="webuploader-element-invisible" multiple="multiple"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></div><div class="deleteBtn state-pedding " style="padding: 0 10px;">删除</div>
        </div>
        <div class="progress" style="display: none;">
            <span class="text">0%</span>
            <span class="percentage" style="width: 0%;"></span>
        </div>
        <!-- <div class="info">共0个文件（0B），已上传0个文件</div> -->
    </div>
 </div>

<div class="modal fade" id="modalPic_${input }" tabindex="-1" role="dialog"
	aria-labelledby="modalssLabel_${input }" aria-hidden="true" style="z-index:99999;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"
					onclick="$('#modalPic_${input }').modal('hide');">×</button>
				<h4 class="modal-title" id="modalssLabel_${input }">预览图</h4>
			</div>
			<div class="modal-body" style="text-align:center;">
				<img id="imgPicture_${input }" />
			</div>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->

<script type="text/javascript">
	jQuery(function() {
    //var $ = jQuery,    // just in case. Make sure it's not an other libaray.
    var $wrap_${input} = $('#uploader_${input}'),

        // 图片容器
        $queue_${input} = $('<ul class="filelist"></ul>')
            .appendTo( $wrap_${input}.find('.queueList') ),

        // 状态栏，包括进度和控制按钮
        $statusBar_${input} = $wrap_${input}.find('.statusBar'),

        // 文件总体选择信息。
        $info_${input} = $statusBar_${input}.find('.info'),

        // 删除按钮
        $delete_${input} = $wrap_${input}.find('.deleteBtn'),
        // 上传按钮
        //$upload_${input} = $wrap_${input}.find('.uploadBtn'),
        // 选择按钮
        //$select_${input} = $wrap_${input}.find('.selectFile'),

        // 没选择文件之前的内容。
        $placeHolder_${input} = $wrap_${input}.find('.placeholder'),

        // 总体进度条
        $progress_${input} = $statusBar_${input}.find('.progress').hide(),

        // 添加的文件数量
        fileCount_${input} = 0,

        // 添加的文件总大小
        fileSize_${input} = 0,

        // 优化retina, 在retina下这个值是2
        ratio_${input} = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth_${input} = ${empty maxWidth ? 120:maxWidth} * ratio_${input},
        thumbnailHeight_${input} = ${empty maxHeight ? 120:maxHeight} * ratio_${input},

        // 可能有pedding, ready, uploading, confirm, done.
        state_${input} = 'pedding',

        // 所有文件的进度信息，key为file id
        percentages_${input} = {},

        supportTransition_${input} = (function(){
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                      'WebkitTransition' in s ||
                      'MozTransition' in s ||
                      'msTransition' in s ||
                      'OTransition' in s;
            s = null;
            return r;
        })(),

        // WebUploader实例
        uploader_${input};
   //初始化图片容器宽度
  // $wrap_${input}.css('width',${empty containerWidth ? 300:containerWidth}+'px');

    if ( !WebUploader.Uploader.support() ) {
        alertx( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
        throw new Error( 'WebUploader does not support the browser you are using.' );
    }
  
  	var is_thumb_${input} = '';
  	if($("#${input}_min").length>0){
  		is_thumb_${input} = '1';
  	}
  	
  	var multiple_${input} = ${selectMultiple};
  	/* //<c:if test="${selectMultiple}">
  		multiple_${input} = ${selectMultiple};
  	//</c:if> */
  	
  	//当前文件个数
  	var curCount_${input} = 0;
  	if($("#${input}").val() !=''){
  		curCount_${input} = $("#${input}").val().split(',').length;
  	}
  	
  	//最大文件个数
  	var maxCount_${input} = ${empty maxCount ? 100:maxCount}-curCount_${input};
  	
  	//被重置的文件id集合
  	var reset_${input} = [];
  	
	//是否是图片
  	var isImg_${input} = ('${isImg}' == 'false' ? '0':'1');
  	
  	//alert(maxCount_${input});
  	var accept_${input} = {
            title: 'All',
            extensions: 'gif,jpg,jpeg,bmp,png,pdf,doc,docx,txt,xls,xlsx,ppt,pptx,zip,mp3,mp4,text,csv,css,html',
            mimeTypes: 'image/*,text/*,.css,.html'
                //word
            +',application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                //excel
            +',application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                //ppt
            +',application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation'
            +',application/pdf'
            +',application/zip'
            +',application/csv'};
        //<c:choose><c:when test="${isImg}">
        accept_${input} = {title: 'Images',
	            extensions: 'jpg,jpeg,bmp,png',
	            mimeTypes: 'image/*'
        	};
        //</c:when><c:when test="${not empty fileType && fileType eq 'apk'}">
        accept_${input} = {title: 'Apk',
	            extensions: 'apk',
	            mimeTypes: 'application/vnd.android.package-archive'
        	};
        //</c:when></c:choose>
    // 实例化
    uploader_${input} = WebUploader.create({
        pick: {
            id: '#filePicker_${input}',
            label: '选择',
            multiple:multiple_${input}
        },
        //dnd: '#uploader_${input} .queueList',
        //paste: document.body,

        accept: accept_${input},
        thumb: {
        	    width: thumbnailWidth_${input},
        	    height: thumbnailHeight_${input},

        	    // 图片质量，只有type为`image/jpeg`的时候才有效。
        	    quality: 100,

        	    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
        	    allowMagnify: false,

        	    // 是否允许裁剪。
        	    crop: true,

        	    // 为空的话则保留原有图片格式。
        	    // 否则强制转换成指定的类型。image/jpeg
        	    type: ''
        },
        // swf文件路径
        swf: ctxStatic + '/uploader/Uploader.swf',

        disableGlobalDnd: false,//是否禁掉整个页面的拖拽功能

        chunked: false,//分片处理
        // server: 'http://webuploader.duapp.com/server/fileupload.php',
//        server: 'http://localhost:8080/yinqian/a/upload/uploadFile',
        //server: ctx  + "/upload/uploadFile?middlePath=${empty uploadPath ?'':uploadPath}&isThumb="+is_thumb_${input},
        server: ctx  + "/upload/uploadFile",
        formData:{middlePath:'${empty uploadPath ?"":uploadPath}',isThumb:is_thumb_${input},isImg:isImg_${input}},//文件上传请求的参数表
        fileNumLimit: maxCount_${input},
        fileSizeLimit: 100 * 1024 * 1024,    // 100 M
        fileSingleSizeLimit: (${fileSingleSizeLimit ==0 || fileSingleSizeLimit==null ? 1:fileSingleSizeLimit}) * 1024 * 1024    // 1 M
        ,compress:false//配置压缩的图片的选项。如果此选项为false, 则图片在上传前不进行压缩
        ,duplicate :true//duplicate {Boolean} [可选] [默认值：undefined] 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
        ,fileVal:'file_${input}'//设置文件上传域的name
        ,auto:true//有文件选择即开始上传
        //uploadSuccess
       /* ,uploadAccept:function(obj ,response ){
        	//file {File}File对象response {Object}服务端返回的数据
        	var code = response.code;
        	if(code == 0){//成功
        		${input}Select(response.data);
        	}
        }*/
    });

    // 添加“添加文件”的按钮，
    uploader_${input}.addButton({
        id: '#filePicker2_${input}',
        label: '选择'
    });
    //上传成功事件
    uploader_${input}.on('uploadSuccess', function (file, response) {
        var code = response.code;
      	if(code == 0){//成功
      		var $li = $('#'+file.id+'_${input}');
      		
      		${input}Select(response.data,$li);
      		
      		var $btns = $li.find('.file-panel').first();
      		$btns.show();
      	}
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile_${input}( file ) {
	    /* if($upload_${input}.is(":hidden")){
	    	$upload_${input}.show();
	    } */
        var $li = $( '<li id="' + file.id + '_${input}">' +
                '<p class="imgWrap" style="width:${empty maxWidth ? 200:maxWidth}px;height:${empty maxHeight ? 200:maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;margin:0px;"></p>'+
                //'<p class="title" style="height:20px;line-height:20px;margin:0px;margin-top：10px;">' + file.name + '</p>' +
                '<p class="progress"><span></span></p>' +
                '</li>' ),

            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '<span class="view">预览</span>'+
                
                //'<span class="rotateRight">向右旋转</span>' +
                //'<span class="rotateLeft">向左旋转</span>'+
                '</div>').appendTo( $li ),
            $prgress_${input} = $li.find('p.progress span'),
            $wrap_${input} = $li.find( 'p.imgWrap' ),
            $info_${input} = $('<p class="error"></p>'),
            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;

                    case 'interrupt':
                        text = '上传暂停';
                        break;
                    case 'Q_TYPE_DENIED':
                        text = '类型不匹配';
                        break;
                    case 'Q_EXCEED_NUM_LIMIT':
                        text = '文件限制了个数';
                        break;
					case 'F_DUPLICATE':
		                 text = '抱歉，文件出现重复';
		                 break;
                    default:
                        text = '上传失败，请重试';
                        break;
                }

                $info_${input}.text( text ).appendTo( $li );
                
                $btns.show();
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap_${input}.text( '预览中' );
            uploader_${input}.makeThumb( file, function( error, src ) {
            /*   var img = $('<img src="'+src+'">');*/
                if ( error ) {
                	  //$wrap_${input}.empty().append($('<img src="'+ctxStatic +'/images/file.jpg" style="width:110px;height:90px;margin-top:20px;background-color:#fefefe;">'));
                	  //$wrap_${input}.empty().append($('<img src="'+ctxStatic +'/images/file.jpg" style="width:110px;height:100px;margin:0px;background-color:#fefefe;" class="uploadedImg">'));
                      var other =$('<a class="uploadedImg" style="font-size:13px;" href="'+src+'" target="_blank">'+file.name+'</a>');
                      $wrap_${input}.empty().append(other);
                      $wrap_${input}.css('text-align','center').css('line-height','100px');
                      $li.find('.file-panel .view').remove();
                    return;
                }

            // var img = $('<img src="'+src+'" style="width:110px;height:90px;margin-top:20px;">');
             var img = $('<img src="'+src+'" style="max-width:${empty maxWidth ? 200:maxWidth}px;max-height:${empty maxHeight ? 100:maxHeight}px;_height:${empty maxHeight ? 100 : maxHeight}px;" class="uploadedImg">');
                $wrap_${input}.empty().append( img );
                
            }, thumbnailWidth_${input}, thumbnailHeight_${input} );

            percentages_${input}[ file.id+'_${input}' ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress_${input}.hide().width(0);
            } else if ( prev === 'queued' ) {
                //$li.off( 'mouseenter mouseleave' );
                //$btns.remove();
                $btns.hide();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                //console.log( file.statusText );
                showError( file.statusText );
                percentages_${input}[ file.id+'_${input}' ][ 1 ] = 1;
                return false;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages_${input}[ file.id+'_${input}' ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info_${input}.remove();
                $prgress_${input}.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader_${input}.removeFile( file );
                    
                    return;
                case 1:
                    viewFile_${input}($li);
                    
                    return;

                case 2:
                    file.rotation += 90;
                    break;

                case 3:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition_${input} ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap_${input}.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap_${input}.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                // use jquery animate to rotation
                // $({
                //     rotation: rotation
                // }).animate({
                //     rotation: file.rotation
                // }, {
                //     easing: 'linear',
                //     step: function( now ) {
                //         now = now * Math.PI / 180;

                //         var cos = Math.cos( now ),
                //             sin = Math.sin( now );

                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                //     }
                // });
            }


        });

        $li.appendTo( $queue_${input} );
    }

    // 负责view的销毁
    function removeFile_${input}( file,fileId ) {
    	var id = file ? file.id:fileId;
        var $li = $('#'+id+'_${input}');

        ${input}Del($li);
		if(file){
	        delete percentages_${input}[ id+'_${input}' ];
	        updateTotalProgress_${input}();
		}
		//uploader_${input}.reset();
        //$li.off().find('.file-panel').off().end().remove();
        
    }
    function viewFile_${input}(obj) {
    	//更新img标签的地址
		var $img = $(obj).find(".uploadedImg").first();
		if($img && $img.length>0){
			//$img.attr('url',url);
			//$img.attr('minurl',minUrl);
			$('#imgPicture_${input}').attr('src', $img.attr('url'));
			$('#modalPic_${input}').modal('show');
		}
    }
    function updateTotalProgress_${input}() {
        var loaded = 0,
            total = 0,
            spans = $progress_${input}.children(),
            percent;

        $.each( percentages_${input}, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus_${input}();
    }

    function updateStatus_${input}() {
        var text = '', stats;

        if ( state_${input} === 'ready' ) {
            text = '选中' + fileCount_${input} + '张图片，共' +
                    WebUploader.formatSize( fileSize_${input} ) + '。';
        } else if ( state_${input} === 'confirm' ) {
            stats = uploader_${input}.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ '张照片'+
                    stats.uploadFailNum + '张照片上传失败';
            }

        } else {
            stats = uploader_${input}.getStats();
            text = '共' + fileCount_${input} + '张（' +
                    WebUploader.formatSize( fileSize_${input} )  +
                    '），已上传' + stats.successNum + '张';

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + '张';
            }
        }

        $info_${input}.html( text );
    }

    function setState_${input}( val ) {
        var file, stats;

        if ( val === state_${input} ) {
            return;
        }

        //$upload_${input}.removeClass( 'state-' + state_${input} );
        //$upload_${input}.addClass( 'state-' + val );
        $delete_${input}.removeClass( 'state-' + state_${input} );
        $delete_${input}.addClass( 'state-' + val );
        state_${input} = val;

        switch ( state_${input} ) {
            case 'pedding':
                var inputFileValue = $("#${input}").val();
                if(inputFileValue && inputFileValue !=''){}
                else{
	                $placeHolder_${input}.removeClass( 'element-invisible' );
	                $queue_${input}.parent().removeClass('filled');
	                $queue_${input}.hide();
	                $statusBar_${input}.addClass( 'element-invisible' );
                }
                uploader_${input}.refresh();
                break;

            case 'ready':
                $placeHolder_${input}.addClass( 'element-invisible' );
                //$( '#filePicker2_${input}' ).removeClass( 'element-invisible');
                $queue_${input}.parent().addClass('filled');
                $queue_${input}.show();
                $statusBar_${input}.removeClass('element-invisible');
                uploader_${input}.refresh();
                break;

            case 'uploading':
                //$( '#filePicker2_${input}' ).addClass( 'element-invisible' );
                //$progress_${input}.show();
                //$upload_${input}.text( '暂停' );
                break;

            case 'paused':
                //$progress_${input}.show();
                //$upload_${input}.text( '选择' );
                break;

            case 'confirm':
                //$progress_${input}.hide();
                //$upload_${input}.text( '上传' ).addClass( 'disabled' );
               // $upload_${input}.text( '选择' );

                $delete_${input}.addClass( 'disabled' );
                stats = uploader_${input}.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState_${input}( 'finish' );
                    return;
                }
                break;
            case 'finish':
                stats = uploader_${input}.getStats();
                if ( stats.successNum ) {
                   	//alert( '上传成功' );
                    //<c:if test="${empty maxCount || (not empty maxCount && maxCount>1)}">
                    //	$upload_${input}.text( '上传' ).removeClass( 'disabled' );
                    //	$select_${input}.removeClass( 'element-invisible' );
                    //</c:if>
                } else {
                    // 没有成功的图片，重设
                    state_${input} = 'done';
                    //location.reload();
                }
                break;
        }

        updateStatus_${input}();
    }

    uploader_${input}.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id+'_${input}'),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages_${input}[ file.id+'_${input}' ][ 1 ] = percentage;
        updateTotalProgress_${input}();
    };
//uploader.option( 'compress', {
//    width: 1600,
//    height: 1600
//});
    uploader_${input}.onFileQueued = function( file ) {
        fileCount_${input}++;
        fileSize_${input} += file.size;
        
        if ( fileCount_${input} === 1 ) {
            $placeHolder_${input}.addClass( 'element-invisible' );
            $statusBar_${input}.show();
        }

        addFile_${input}( file );
        setState_${input}( 'ready' );
        updateTotalProgress_${input}();
        
       // var maxCount = parseInt( uploader_${input}.option('fileNumLimit'), 10 )-1;
       // uploader_${input}.option('fileNumLimit',maxCount)
        //reset_${input}.push(file.id);
    };

    uploader_${input}.onFileDequeued = function( file ) {
        fileCount_${input}--;
        fileSize_${input} -= file.size;
        
        if ( !fileCount_${input} ) {
            setState_${input}( 'pedding' );
        }

        removeFile_${input}( file );
        updateTotalProgress_${input}();

    };
    uploader_${input}.onBeforeFileQueued = function( file ) {
    	var flag = true;
    	var maxCount = parseInt( uploader_${input}.option('fileNumLimit'), 10 );
        if ( fileCount_${input} >= maxCount && flag ) {
            flag = false;
            //uploader_${input}.reset();
            uploader_${input}.trigger( 'error', 'Q_EXCEED_NUM_LIMIT', ${empty maxCount ? 100:maxCount}, file );
            setTimeout(function() {
                flag = true;
            }, 1 );
        }
        return fileCount_${input} >= maxCount ? false : true;
        
	    if (multiple_${input}) {
	    	uploader_${input}.sort(function( a, b ) {
	           if ( a.name < b.name )
	             return -1;
	           if ( a.name > b.name )
	             return 1;
	           return 0;
	       });
	    }
	    
    };
    uploader_${input}.onReset = function() {
    	/*if(reset_${input}.length>0){
    		for(var i=0;i<reset_${input}.length;i++){
	    		removeFile_${input}(null,reset_${input}[i]);
    		}
    	}*/
    };
    
    uploader_${input}.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState_${input}( 'confirm' );
                
                var maxCount = parseInt( uploader_${input}.option('fileNumLimit'), 10 );
                //提示
                if(fileCount_${input} > 0 && fileCount_${input}<maxCount)
                	alertx( '上传成功' );
                	
                $delete_${input}.removeClass('disabled');
                //reset_${input} = [];
                //uploader_${input}.reset();
                break;

            case 'startUpload':
                setState_${input}( 'uploading' );
                break;

            case 'stopUpload':
                setState_${input}( 'paused' );
                break;
        }
    });

    uploader_${input}.onError = function( code ) {
        //alert( 'Eroor: ' + code );
        var text = "";
         switch( code ) {
             case 'exceed_size':
                 text = '文件大小超出';
                 break;
             case 'F_EXCEED_SIZE':
                 text = '文件大小超出';
                 break;

             case 'interrupt':
                 text = '上传暂停';
                 break;
             case 'Q_TYPE_DENIED':
                 text = '类型不匹配';
                 break;
             case 'Q_EXCEED_NUM_LIMIT':
                 text = '抱歉，超过上传数量图片限制';
                 break;
             case 'F_DUPLICATE':
                 text = '抱歉，文件出现重复';
                 break;

             default:
                 text = '上传失败，请重试';
                 break;
         }
         alertx(text);
    };

    /*$upload_${input}.on('click', function() {
        if ( $(this).hasClass( 'disabled' ) ) {
            return false;
        }

        if ( state_${input} === 'ready' ) {
            uploader_${input}.upload();
        } else if ( state_${input} === 'paused' ) {
            uploader_${input}.upload();
        } else if ( state_${input} === 'uploading' ) {
            uploader_${input}.stop();
        }
    });*/
    $delete_${input}.on('click', function() {
        if ( $(this).hasClass( 'disabled' ) ) {
            return false;
        }
        var fileList = $wrap_${input}.find(".filelist>li");
        if(fileList && fileList.length>0){
        	fileCount_${input} = 0;
        	fileSize_${input} = 0;
        	var num = parseInt( uploader_${input}.option('fileNumLimit'), 10 );
        	uploader_${input}.option('fileNumLimit',parseInt(${empty maxCount ? 100:maxCount}, 10 ));
        	//num =  parseInt( uploader_${input}.option('fileNumLimit'), 10 );
        	
        	uploader_${input}.reset();
        	for(var i=0;i<fileList.length;i++){
        		${input}Del(fileList[i]);
        	}
        	//reset_${input} = [];
        }
    });

    $info_${input}.on( 'click', '.retry', function() {
        uploader_${input}.retry();
    } );

    $info_${input}.on( 'click', '.ignore', function() {
        alertx( 'todo' );
    } );

    //$upload_${input}.addClass( 'state-' + state_${input} );
    updateTotalProgress_${input}();
    
    
    function ${input}Preview(){
		var li, urls = $("#${input}").val().split(",");
		var minurls = $("#${input}").val().split(",");
		var minInput = $("#${input}_min");
		var minurls = [];
		if(minInput && minInput.length>0){
			minurls = $("#${input}_min").val().split(",");
		}
		if(urls == ''){
			return;
		}
		for (var i=0; i<urls.length; i++){
			var $li = $( '<li id="UP_FILE_' + i + '_${input}">' +
                '<p class="imgWrap" style="width:${empty maxWidth ? 200:maxWidth}px;height:${empty maxHeight ? 200:maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;margin:0px;"></p>'+
                '</li>' );
            var $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>'+
                '<span class="view">预览</span>'
                ).appendTo( $li ),
            $wrap_${input} = $li.find( 'p.imgWrap' ),
            $info_${input} = $('<p class="error"></p>');
			//<c:choose><c:when test="${isImg}">
            var img = $('<img src="'+urls[i]+'" style="width:${empty maxWidth ? 200:maxWidth}px;height:${empty maxHeight ? 200:maxHeight}px;"'
            	+'url="'+urls[i]+'" minurl="'+(minurls.length == 0 ? "":minurls[i])+'" class="uploadedImg">');
            	//  onload="this.src=\"${ctxStatic}/images/pic.jpg\"" onerror="this.src=\"${ctxStatic}/images/pic.jpg\""
            $wrap_${input}.empty().append( img );
			//</c:when><c:otherwise>
			$wrap_${input}.css('text-align','center').css('line-height','100px');
            var other =$('<a class="uploadedImg" style="font-size:13px;" href="'+urls[i]+'" url="'+urls[i]+'" minurl="'+(minurls.length == 0 ? "":minurls[i])+'" target="_blank">'+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+'</a>');
            $wrap_${input}.empty().append( other );
            $li.find('.file-panel .view').remove();
            //</c:otherwise></c:choose>
			$li.appendTo( $queue_${input} );
		}
		$('#dndArea_${input}').addClass("element-invisible");
		$wrap_${input}.find('.queueList').css('border','none');
		$statusBar_${input}.show();
		$queue_${input}.show();
		event_${input}();
	}
	
	function event_${input}(){
		$wrap_${input}.find(".filelist>li").on( 'mouseenter', function() {
            $(this).find(".file-panel").stop().animate({height: 30});
        });
	
       $wrap_${input}.find(".filelist>li").on( 'mouseleave', function() {
            $(this).find(".file-panel").stop().animate({height: 0});
        });
	
        $wrap_${input}.find(".filelist>li .file-panel").on( 'click', 'span', function() {
            var index = $(this).index();
			var $li = $(this).parent().parent();
            switch ( index ) {
                case 0:
                    ${input}Del($li,true);
                    return;

                case 1:
                    viewFile_${input}($li);
                    break;
            }
        });
	}
	function ${input}Del(obj,flag){
        var $li = $(obj);
        $li.off().find('.file-panel').off().end().remove();
	
		var img = $li.find(".uploadedImg");
		var url = $(img).attr("url");
		var minurl = $(img).attr("minurl");
		$("#${input}").val($("#${input}").val().replace(","+url,"","").replace(url+",","","").replace(url,"",""));
		var minInput = $("#${input}_min");
		if(minInput && minInput.length>0){
			$("#${input}_min").val($("#${input}_min").val().replace(","+minurl,"","").replace(minurl+",","","").replace(minurl,"",""));
		}
		
		if(flag !=undefined && flag){
			//重新设置限制文件个数
			var maxCount =  parseInt( uploader_${input}.option('fileNumLimit'), 10 )+1;
			uploader_${input}.option('fileNumLimit',maxCount);
		}
		if($("#${input}").val() == ''){
			$placeHolder_${input}.removeClass( 'element-invisible' );
            $queue_${input}.parent().removeClass('filled');
            $queue_${input}.hide();
            $statusBar_${input}.addClass( 'element-invisible' );
               
			uploader_${input}.refresh();
			//$wrap_${input}.find('.queueList').css('min-height','120px');
		}
	}
	function ${input}Select(data,obj){
		if(!data)
		return;
		var url="";
		var minUrl = "";
		var dataUrl = data.filePaths ? data.filePaths.split(','):[];
		var dataMinUrl = data.fileThumbPaths? data.fileThumbPaths.split(','):[];
		for(var i=0; i<dataUrl.length; i++){//<c:if test="${type eq 'thumb'}">
			url += dataUrl[i];//</c:if><c:if test="${type ne 'thumb'}">
			//alert(files[i].getThumbnailUrl());//nameImage_min
			if(dataMinUrl.length>0){
				minUrl +=dataMinUrl[i];
			}
			url += dataUrl[i];//</c:if>
			if (i<dataUrl.length-1){
			 	url+=",";
			 	if(dataMinUrl.length>0){
			 		minUrl+=",";
		 		}
			 }
		}
		var minInput = $("#${input}_min");
		//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val()==""?url:","+url));
		if(minInput && minInput.length>0){
			$("#${input}_min").val($("#${input}_min").val()+($("#${input}_min").val()==""?minUrl:","+minUrl));
		}
		//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);
		if(minInput && minInput.length>0){
			$("#${input}_min").val(minUrl);
		}
		//</c:if>
		
		//更新img标签的地址
		var $img = $(obj).find(".uploadedImg").first();
		if($img && $img.length>0){
			$img.attr('url',url);
			$img.attr('minurl',minUrl);
			
			if($img[0].hasAttribute("href")){
				$img.attr('href',url);
			}
		}
	}
	${input}Preview();
});
</script>