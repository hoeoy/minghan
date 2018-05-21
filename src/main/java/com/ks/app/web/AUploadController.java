package com.ks.app.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Config;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Controller
@RequestMapping("/app/upload")
public class AUploadController {
	@Autowired
	private AUserService aUserService;
	@RequestMapping(value="/uploadBase64File")
	public @ResponseBody ActionResponse<Object> uploadBase64File(String middlePath,String isThumb,HttpServletRequest request, HttpServletResponse response){
		ActionResponse<Object> data = new ActionResponse<Object>();
		try{
			FileData fileData = new FileData(request.getParameter("img"));
			if (fileData.isError()) {
				//格式化并转换String类型 
				//FileUtils.forceMkdir(new File(fileData.createNewDateFilePath()));
				
				middlePath = StringUtils.isNotBlank(middlePath) ? (middlePath+(middlePath.endsWith("//") ? "":"/")):"";
				String uploadPath = FileUtils.getUploadPath(middlePath);
				
				String savePath = Config.getUploadBasepath() + uploadPath;
				
				String basePath = request.getContextPath()+FileUtils.uploadPath;
				
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				String id = IdGen.uuid();
				String after = fileData.getSuffix();
				String fileName = id+"."+after;
				
				/*boolean tflag = StringUtils.isNotBlank(isThumb) && "1".equals(isThumb) ? true:false;
				String thumbFileName = tflag ? (id+"_sec."+after):null;*/
				
				Base64ImageUtil.convertByteToImage(fileData.getData(),savePath+"/"+fileName,after);  
				
//				String[] paths = FileUtils.fileUpload(file, uploadPath, fileName, thumbFileName,tflag , false, request.getContextPath()+FileUtils.uploadPath);
//				return new String[]{basePath+uploadPath+"/"+fileName,basePath+uploadPath+"/"+thumbFileName};
				String filePaths = basePath+uploadPath+"/"+fileName;
				String fileThumbPaths = "";
				
				/*if(filePaths.endsWith(",")){
					filePaths = filePaths.substring(0,filePaths.length()-1);
				}
				if(fileThumbPaths.endsWith(",")){
					fileThumbPaths = fileThumbPaths.substring(0,fileThumbPaths.length()-1);
				}*/
				Map<String,String> map = new HashMap<String, String>();
				map.put("filePaths", filePaths);
				map.put("fileThumbPaths", fileThumbPaths);
				
				data.setData(map);
			}
			data.setCode(Constant.SUCCESS_CODE);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setingError("上传失败");
		return data;
	}
	@RequestMapping(value="/uploadWxjsFile")
    public @ResponseBody ActionResponse<Object> uploadWxjsFile(String wxfileId,String middlePath,HttpServletRequest request, HttpServletResponse response){
		ActionResponse<Object> data = new ActionResponse<Object>();
	    try{
	    	if (StringUtils.isNotBlank(wxfileId)) {
	    		Map<String,String> fileData = new HashMap<String, String>();
	    		middlePath = StringUtils.isNotBlank(middlePath) ? (middlePath+(middlePath.endsWith("//") ? "":"/")):"";
	    		String uploadPath = FileUtils.getUploadPath(middlePath);
	    		String filePaths = "";
	    		String fileThumbPaths = "";
				String id = IdGen.uuid();
				
				String accessToken = aUserService.getToken();
				if(StringUtils.isNotBlank(accessToken)){
					String[] paths = WeixinUtil.saveImageToDisk(accessToken, wxfileId, uploadPath, id, request.getContextPath()+FileUtils.uploadPath);
					if(paths != null){
						filePaths = paths[0]+",";
						fileThumbPaths = paths[1]+",";
					}
					
					if(filePaths.endsWith(",")){
						filePaths = filePaths.substring(0,filePaths.length()-1);
					}
					if(fileThumbPaths.endsWith(",")){
						fileThumbPaths = fileThumbPaths.substring(0,fileThumbPaths.length()-1);
					}
				}
	    		
	    		fileData.put("filePaths", filePaths);
	    		fileData.put("fileThumbPaths", fileThumbPaths);
	    		
	    		data.setData(fileData);
	    		data.setCode(Constant.SUCCESS_CODE);
	    		return data;
	    	}
	    }catch (Exception e) {
	    	e.printStackTrace();
		}
	    data.setingError("上传失败");
		return data;
	}
}