package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 上传Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/upload")
public class UploadController extends BaseController {

	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
    public @ResponseBody ActionResponse<Object> uploadFile(String middlePath,String isThumb,String isImg,HttpServletRequest request, HttpServletResponse response){
		ActionResponse<Object> data = new ActionResponse<Object>();
	    try{
	    	//转型为MultipartHttpServletRequest
	    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	    	Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();  
	    	if (fileMap != null && fileMap.size()>0) {
	    		Map<String,String> fileData = new HashMap<String, String>();
	    		middlePath = StringUtils.isNotBlank(middlePath) ? (middlePath+(middlePath.endsWith("//") ? "":"/")):"";
	    		String uploadPath = FileUtils.getUploadPath(middlePath);
	    		boolean tflag = StringUtils.isNotBlank(isThumb) && "1".equals(isThumb) ? true:false;
	    		String filePaths = "";
	    		String fileThumbPaths = "";
	    		for (Iterator<String> it=fileMap.keySet().iterator();it.hasNext();) {
	    			String key = it.next();
	    			MultipartFile file = fileMap.get(key);
	    			if(file != null && !file.isEmpty()){
	    				String after = FileUtils.getFileExtension(file.getOriginalFilename());
	    				String id = IdGen.uuid();
	    				String fileName = id+"."+after;
	    				if("1".equals(isImg)){
	    					String thumbFileName = tflag ? (id+"_sec."+after):null;
	    					String[] paths = FileUtils.fileUpload(file, uploadPath, fileName, thumbFileName,tflag , false, request.getContextPath()+FileUtils.uploadPath);
	    					filePaths = paths[0]+",";
	    					fileThumbPaths = paths[1]+",";
	    				}else{
	    					String path = FileUtils.fileUpload(file, uploadPath, fileName, request.getContextPath()+FileUtils.uploadPath);
	    					filePaths = path+",";
	    				}
	    			}
	    		}
	    		
	    		if(filePaths.endsWith(",")){
	    			filePaths = filePaths.substring(0,filePaths.length()-1);
	    		}
	    		if(fileThumbPaths.endsWith(",")){
	    			fileThumbPaths = fileThumbPaths.substring(0,fileThumbPaths.length()-1);
	    		}
	    		
	    		fileData.put("filePaths", filePaths);
	    		fileData.put("fileThumbPaths", fileThumbPaths);
	    		
	    		data.setData(fileData);
	    	}
	    	data.setCode(Constant.SUCCESS_CODE);
	    	return data;
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}
	    data.setingError("上传失败");
		return data;
    }
	//, method = RequestMethod.POST
	@RequestMapping(value = "/uploadUEFile",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String,Object> uploadUEFile(String middlePath,HttpServletRequest req){
        Map<String,Object> rs = new HashMap<String, Object>();
       /* MultipartHttpServletRequest mReq  =  null;
        MultipartFile file = null;
        InputStream is = null ;
        String fileName = "";
        // 原始文件名   UEDITOR创建页面元素时的alt和title属性
        String originalFileName = "";
        String filePath = "";*/
        try {
        	if(req instanceof MultipartHttpServletRequest){
	        	MultipartHttpServletRequest mReq = (MultipartHttpServletRequest)req;
	            // 从config.json中取得上传文件的ID
	            MultipartFile file = mReq.getFile("upfile");
	            // 取得文件的原始文件名称
	            String fileName = file.getOriginalFilename();
	            String originalFileName = fileName;
	            
	    		middlePath = StringUtils.isNotBlank(middlePath) ? (middlePath+(middlePath.endsWith("//") ? "":"/")):"";
	    		String uploadPath = FileUtils.getUploadPath(middlePath);
	    		String filePaths = "";
				if(file != null && !file.isEmpty()){
					String after = FileUtils.getFileExtension(file.getOriginalFilename());
					String id = IdGen.uuid();
					fileName = id+"."+after;
					String[] paths = FileUtils.fileUpload(file, uploadPath, fileName, null,false , false, req.getContextPath()+FileUtils.uploadPath);
					filePaths = paths[0]+",";
				}
			
	    		if(filePaths.endsWith(",")){
	    			filePaths = filePaths.substring(0,filePaths.length()-1);
	    		}
	    		
	            
	            rs.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
	            rs.put("url",filePaths);         //能访问到你现在图片的路径
	            rs.put("title", originalFileName.lastIndexOf(".")>0 ? originalFileName.substring(0, originalFileName.lastIndexOf(".")):originalFileName);
	            rs.put("original", originalFileName);  
        	}

        } catch (Exception e) {
            //log.error(e.getMessage(),e);
        	e.printStackTrace();
            rs.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
            rs.put("url","");
            rs.put("title", "");
            rs.put("original", "");
        }
        return rs;
    }
}
