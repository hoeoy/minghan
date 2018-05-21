package com.thinkgem.jeesite.modules.cmd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.ItemImage;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;
import com.thinkgem.jeesite.modules.cmd.service.ItemService;
import com.thinkgem.jeesite.modules.cmd.service.ItemTypeService;

/**
 * 项目相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cmd/item")
public class ItemController extends BaseController{

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemTypeService itemTypeService;
	
	/**
	 * 保存 修改/新增 项目分类
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"itemTypeSave"})
	public String itemTypeSave(ItemType itemType){
//		if(StringUtils.isBlank(itemType.getId())){//新增
//			itemType.setId(OrderCodeUtil.getNo(Constant.MANAGER_ID));
//		}
		itemTypeService.save(itemType);
		return "redirect:" + adminPath + "/cmd/item/itemTypeList?repage";
	}
	
	/**
	 * 修改/新增 项目分类
	 * @param itemType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"itemTypeForm"})
	public String itemTypeForm(ItemType itemType, Model model){
		itemType = itemTypeService.get(itemType);
		if(itemType == null){
			itemType = new ItemType();
		}
		model.addAttribute("itemType", itemType);
		return "modules/cmd/itemTypeForm";
	}
	
	/**
	 * 逻辑删除项目分类
	 * @param itemType
	 * @return
	 */
	@RequestMapping(value = {"itemTypeDelete"})
	public String itemTypeDelete(ItemType itemType){
		itemTypeService.delete(itemType);
		return "redirect:" + adminPath + "/cmd/item/itemTypeList?repage";
	}
	
	/**
	 * 获取所有项目分类
	 * @param itemType
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"itemTypeList"})
	public String itemTypeList(ItemType itemType, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<ItemType> page = itemTypeService.getItemTypeList(new Page<ItemType>(request, response), itemType); 
		model.addAttribute("page", page);
		model.addAttribute("ItemType", itemType);
		return "modules/cmd/itemTypeList";
	}
	/*-******************************************************-*/
	
	/**
	 * 获取所有项目信息
	 * @param item
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(Item item, ItemType itemType, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Item> page = itemService.getItemList(new Page<Item>(request, response), item); 
		model.addAttribute("page", page);
		model.addAttribute("item", item);
		List<ItemType> types = itemTypeService.getAllItemType();
		model.addAttribute("types", types);
		return "modules/cmd/itemList";
	}
	@RequestMapping(value = {"particularList"})
	public String particularList(Item item, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Item> page = itemService.getItemList(new Page<Item>(request, response), item); 
		model.addAttribute("page", page);
//		model.addAttribute("item", item);
		return "modules/cmd/itemParticularsList";
	}
	
	/**
	 * 删除（改del_flag）
	 * @param item
	 * @return
	 */
	@RequestMapping(value = {"delete"})
	public String delete(Item item){
		itemService.delete(item);
		return "redirect:" + adminPath + "/cmd/item/list?repage";
	}
	
	/**
	 * 保存
	 * @param item
	 * @return
	 */
	@RequestMapping(value = {"save"})
	public String save(Item item, HttpServletRequest request){
		if(StringUtils.isNotBlank(item.getId())){
			String prePath = SystemPath.getRequestPreUrl(request);
			Item obj = itemService.getItemById(item.getId(), prePath, request);
			item.setStatus(obj.getStatus());
			item.setSaleCount(obj.getSaleCount());
			item.setRemarks(obj.getRemarks());
		}else{
			item.setStatus("1");
			item.setSaleCount(0);
		}
		itemService.save(item);
		//删除滚动图
		itemService.deleteImages(item);
		//新增滚动图片并拼接
		itemService.inFrameFotosaveItem(item);
		return "redirect:" + adminPath + "/cmd/item/list?repage";
	}
	
	/**
	 * 添加/修改
	 * @param item
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"form"})
	public String form(Item item, Model model){
		item = itemService.get(item);
		if(item == null){
			item = new Item();
		}else{
			StringBuffer str = new StringBuffer();
			List<ItemImage> cpictu= item.getImages();
			if(cpictu != null && cpictu.size()>0){
				for(int i= 0 ; i<cpictu.size();i++){	
					str.append(cpictu.get(i).getItemImage());
					str.append(",");
				}
				String result = str.toString();
				if(result.endsWith(",")){
					result = result.substring(0,result.length()-1);
				}
				item.setImagesLocation(result);
			}
		}
		//获取所有项目类型
		List<ItemType> types = itemTypeService.getAllItemType();
		item.setTypes(types);
		model.addAttribute("types", types);
		model.addAttribute("item", item);
		return "modules/cmd/itemForm";
	}
	
	/**
	 * 项目 上架/下架
	 * @param item
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"status"})  
	public String status(Item item, Model model){
		item = itemService.get(item);
		if("0".equals(item.getStatus())){
			item.setStatus("1");//上架
		}else {
			item.setStatus("0");//下架
		}
		itemService.save(item);
		return "redirect:" + adminPath + "/cmd/item/list?repage";
	}
	
}

