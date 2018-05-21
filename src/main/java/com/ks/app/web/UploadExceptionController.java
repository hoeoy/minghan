package com.ks.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.utils.ActionResponse;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;

@Controller
@RequestMapping("/upload")
public class UploadExceptionController {
	@RequestMapping("maxSize.do")
    public @ResponseBody ActionResponse<Object> handleException() {       
		ActionResponse<Object> returnData = new ActionResponse<Object>(); 
    	returnData.setingError("文件应不大于 "+ FileUtils.getFileSizeDisplay(Double.valueOf(Global.getMaxUploadSize()),0));  
        return returnData;  
    } 
}