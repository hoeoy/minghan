package com.ks.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.ADoctor;
import com.ks.app.service.AMallAddressService;
import com.ks.app.service.ADoctorService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.EnumConstant.DocAttestationFlag;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
@Controller
@RequestMapping(value = "/doct")
public class ADoctorController extends BaseController {

	@Autowired
	private ADoctorService doctorService;
	
	@RequestMapping(value="getAll.do")
	public String getAll(HttpServletRequest req){
		List<ADoctor> list = doctorService.getAll();
		if(list==null){
			list = Lists.newArrayList();;
		}
		for(int i = 0;i<list.size();i++){
			String url = list.get(i).getMinPhoto();
			if(StringUtils.isNotBlank(url)){
				list.get(i).setMinPhoto(SystemPath.getRequestPreUrl(req)+url);
  			}
			list.get(i).setAttestationFlag(DocAttestationFlag.docAttestationFlag(list.get(i).getAttestationFlag()));
			if(list.get(i).getIntro().length()>=20){
			String intro = list.get(i).getIntro().substring(0,20)+"......";
			list.get(i).setIntro(intro);
			}
		}
		req.setAttribute("list", list);
		return "mobile/doctorTeam";
	}
	
	@RequestMapping(value="getDoctById")
	public String getDoctById(HttpServletRequest req){
		String id = req.getParameter("id");
		ADoctor doc =doctorService.get(id);
		doc.setAttestationFlag(DocAttestationFlag.docAttestationFlag(doc.getAttestationFlag()));
		req.setAttribute("doc", doc);
		return "mobile/oneDoctor";
	}
}
