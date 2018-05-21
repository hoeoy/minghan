package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.ItemImage;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;
import com.thinkgem.jeesite.modules.sys.entity.MGoods;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsImage;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsType;
import com.thinkgem.jeesite.modules.sys.service.MGoodsService;
import com.thinkgem.jeesite.modules.sys.service.MGoodsTypeService;

@Controller
@RequestMapping(value = "${adminPath}/sys/mgoods")
public class MGoodsController extends BaseController{
	
	@Autowired
	private MGoodsService mGoodsService;
	@Autowired
	private MGoodsTypeService goodsTypeService;
	
	/**
	 * 获取所有分类
	 * @param itemType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"typeList"})
	public String typeList(MGoodsType mGoodsType, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<MGoodsType> page = goodsTypeService.getGoodsTypeList(new Page<MGoodsType>(request, response), mGoodsType); 
		model.addAttribute("page", page);
		model.addAttribute("goodsType", mGoodsType);
		return "modules/sys/goodsTypeList";
	}
	
	/**
	 * 修改/新增 商品分类
	 * @param itemType
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"goodsTypeForm"})
	public String goodsTypeForm(MGoodsType goodsType, Model model){
		goodsType = goodsTypeService.get(goodsType);
		if(goodsType == null){
			goodsType = new MGoodsType();
		}
		model.addAttribute("goodsType", goodsType);
		return "modules/sys/goodsTypeForm";
	}
	
	
	/**
	 * 保存 修改/新增 商品分类
	 * @param item
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"goodsTypeSave"})
	public String goodsTypeSave(MGoodsType goodsType){
//		if(StringUtils.isBlank(itemType.getId())){//新增
//			itemType.setId(OrderCodeUtil.getNo(Constant.MANAGER_ID));
//		}
		goodsTypeService.save(goodsType);
		return "redirect:" + adminPath + "/sys/mgoods/typeList?repage";
	}
	/*********************************************************************/
	
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"list"})
	public String list(MGoods goods,HttpServletRequest request, HttpServletResponse response, Model model){
		Page<MGoods> page = mGoodsService.findPage(new Page<MGoods>(request,response), goods);
		List<MGoods> list = page.getList();
		for(int i = 0;i<list.size();i++){
			String typeid = list.get(i).getType();
			MGoodsType mgt  = goodsTypeService.get(typeid);
			list.get(i).setType(mgt.getTypeName());
		}
		page.setList(list);
		List<MGoodsType> types = goodsTypeService.getAllItemType();
		model.addAttribute("types", types);
		model.addAttribute("page", page);
		model.addAttribute("goods", goods);
		return "modules/sys/goodsList";
	}
	
	
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"form"})
	public String form(MGoods goods, Model model){
		goods = mGoodsService.get(goods);
		if(goods == null){
			goods = new MGoods();
		}else{
			List<MGoodsImage> list = mGoodsService.getImages(goods.getId());
			{
				StringBuffer str = new StringBuffer();
				if(list != null && list.size()>0){
					for(int i= 0 ; i<list.size();i++){	
						str.append(list.get(i).getGoodsImage());
						str.append(",");
					}
					String result = str.toString();
					if(result.endsWith(",")){
						result = result.substring(0,result.length()-1);
					}
					goods.setImagesLocation(result);
				}
			}
			goods.setImages(list);
		}
		//获取所有项目类型
		List<MGoodsType> types = goodsTypeService.getAllItemType();
		goods.setTypes(types);
		model.addAttribute("types", types);
		model.addAttribute("goods", goods);
		return "modules/sys/goodsForm";
	}
	
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"save"})
	public String save(MGoods goods, HttpServletRequest request,RedirectAttributes redirectAttributes){
		mGoodsService.save(goods);
		//删除原始图片
		mGoodsService.deleteImages(goods.getId());
		//新增滚动图片并拼接
		mGoodsService.inFrameFotosaveGoods(goods);
		addMessage(redirectAttributes, "保存商品'" + goods.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/mgoods/list?repage";
	}
	
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"delete"})
	public String delete(MGoods goods,RedirectAttributes redirectAttributes){
		mGoodsService.delete(goods);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:" + adminPath + "/sys/mgoods/list?repage";
	}
	
	@RequiresPermissions("sys:mgoods:edit")
	@RequestMapping(value = {"status"})  
	public String status(MGoods goods, Model model){
		goods = mGoodsService.get(goods);
		if("0".equals(goods.getPutFlag())){
			goods.setPutFlag("1");//上架
		}else {
			goods.setPutFlag("0");//下架
		}
		mGoodsService.save(goods);
		return "redirect:" + adminPath + "/sys/mgoods/list?repage";
	}
	

}
