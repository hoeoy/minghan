package com.thinkgem.jeesite.modules.cmd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Combo;
import com.thinkgem.jeesite.modules.cmd.entity.ComboContract;
import com.thinkgem.jeesite.modules.cmd.entity.ComboImage;
import com.thinkgem.jeesite.modules.cmd.service.ComboService;

/**
 * 套餐相关Controller
 * @author pc-20170905
 *
 */
@Controller
@Transactional(rollbackFor=Exception.class)
@RequestMapping(value = "${adminPath}/cmd/combo")
public class ComboController extends BaseController{

	@Autowired
	private ComboService comboService;
	
	/**
	 * 获取所有套餐信息
	 * @param combo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(Combo combo, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Combo> page = comboService.getComboList(new Page<Combo>(request, response), combo); 
		model.addAttribute("page", page);
		model.addAttribute("combo", combo);
		return "modules/cmd/comboList";
	}
	@RequestMapping(value = {"particularList"})
	public String particularList(Combo combo, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Combo> page = comboService.getComboList(new Page<Combo>(request, response), combo);
		model.addAttribute("page", page);
		return "modules/cmd/comboParticularsList";
	}
	
	/**
	 * 删除（改del_flag）
	 * @param combo
	 * @return
	 */
	@RequestMapping(value = {"delete"})
	public String delete(Combo combo){
		comboService.delete(combo);
		return "redirect:" + adminPath + "/cmd/combo/list?repage";
	}
	
	/**
	 * 添加/修改
	 * @param combo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"form"})
	public String form(Combo combo, Model model){
		combo = comboService.get(combo);
		if(combo == null){
			combo = new Combo();
		}else{
			//获取滚动图及合同图
			combo =comboService.getComboImageAndContract(combo);
			StringBuffer str = new StringBuffer();
			List<ComboImage> images = combo.getImages();
			List<ComboContract> contracts = combo.getContracts();
			if(images != null && images.size() > 0){
				for(int i=0; i<images.size(); i++){
					str.append(images.get(i).getImage());
					str.append(",");
				}
				String result = str.toString();
				if(result.endsWith(",")){
					result = result.substring(0,result.length()-1);
				}
				combo.setImagesLocation(result);
			}
			//将str清空
			str.setLength(0);
			if(contracts != null && contracts.size() > 0){
				for(int i=0; i<contracts.size(); i++){
					str.append(contracts.get(i).getContract());
					str.append(",");
				}
				String result = str.toString();
				if(result.endsWith(",")){
					result = result.substring(0,result.length()-1);
				}
				combo.setContractsLocation(result);
			}
		}
		model.addAttribute("combo", combo);
		return "modules/cmd/comboForm";
	}
	
	/**
	 * 保存
	 * @param combo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"save"})
	public String save(Combo combo, HttpServletRequest request){
		if(StringUtils.isNotBlank(combo.getId())){
			String prePath = SystemPath.getRequestPreUrl(request);
			Combo obj = comboService.getComboById(combo.getId(), prePath, request);
			if(obj != null){
				combo.setStatus(obj.getStatus());
				combo.setSaleCount(obj.getSaleCount());
				combo.setRemarks(obj.getRemarks());
				combo.setDiscount(obj.getDiscount());
				combo.setScore(obj.getScore());
			}
		}else{
			combo.setStatus("1");
			combo.setSaleCount(0);
			combo.setDiscount(1.0);
			combo.setScore(Long.parseLong(combo.getCurrentPrice().toString()));
		}
		comboService.save(combo);
		//删除滚动图和合同图
		comboService.deleteImageAndContract(combo);
		//新增滚动图片和合同图拼接
		comboService.inFrameFotosaveCombo(combo);
		return "redirect:" + adminPath + "/cmd/combo/list?repage";
	}
	
	/**
	 * 购买套餐
	 * @param session
	 * @param comboId 套餐id
	 * @return
	 */
//	@RequestMapping("/buyCombo.do")
//	@ResponseBody
//	public ActionResponse<Object> buyCombo(HttpSession session, String comboId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = wComboService.buyCombo(session, comboId);
//		if(row == 1){
//			returnData.setingSuccess("购买成功");
//		}else if(row == 2){
//			returnData.setingError("您已购买过套餐，每人限购1次");
//		}else if(row == 3){
//			returnData.setingError("您的余额不足，请充值后购买");
//		}else{
//			returnData.setingError("操作失败，请稍后重试");
//		}
//		return returnData;
//	}
	
	/**
	 * 获取所有形象大使信息
	 * @param request
	 * @return
	 */
//	@RequestMapping("/getComboList.do")
//	@ResponseBody
//	public ActionResponse<Object> getWComboList(HttpServletRequest request){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		String prePath = SystemPath.getRequestPreUrl(request);
//		List<WCombo> comboList = wComboService.getComboList(prePath,request);
//		if(comboList != null){
//			returnData.setData(comboList);
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
//		}
//		return returnData;
//	}
	
	/**
	 * 获取套餐详细信息
	 * @param id
	 * @return
	 */
//	@RequestMapping("/getComboById.do")
//	@ResponseBody
//	public ActionResponse<Object> getWComboById(String id,HttpServletRequest request){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		String prePath = SystemPath.getRequestPreUrl(request);
//		WCombo combo = wComboService.getComboById(id,prePath,request);
//		if(combo != null){
//			returnData.setData(combo);
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
//		}
//		return returnData;
//	}
	
	/**
	 * 获取套餐合同详细信息
	 * @param id
	 * @return
	 */
//	@RequestMapping("/getComboContratsByComboId.do")
//	@ResponseBody
//	public ActionResponse<Object> getComboContratsByComboId(String comboId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		List<WComboContract> contracts = wComboService.getComboContratsByComboId(comboId);
//		if(contracts != null){
//			returnData.setData(contracts);
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
//		}
//		return returnData;
//	}
	
	
}


