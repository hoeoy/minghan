package com.thinkgem.jeesite.modules.cmd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.ItemDao;
import com.thinkgem.jeesite.modules.cmd.dao.ItemTypeDao;
import com.thinkgem.jeesite.modules.cmd.dao.ShopDao;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.ItemImage;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;
import com.thinkgem.jeesite.modules.cmd.entity.Shop;

@Service
@Transactional(rollbackFor=Exception.class)
public class ItemService extends CrudService<ItemDao, Item>{

	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ItemTypeDao itemTypeDao;
	
	//商品介绍单独一个接口
	private String css = "<style type='text/css'>p{margin:0px;}</style>";
	public String changePathImg(String content,String prePath,String contextPath) {   
		String imgReg = "<img[^>]*src=['\"]([^'\"]+)[^>]*>";  
		Pattern imgPattern = Pattern.compile(imgReg);  
		Matcher m = imgPattern.matcher(content);   
		while (m.find()) {     
			String img = m.group();
			String src = img.replaceAll("<img[^>]*src=['\"]", "");
			src = src.replaceAll("['\"][^>]*>", "");   
			if(src.contains("https://")||src.contains("http://")){	
				
			}else {
				String newImg = img.replace(src, prePath+"/"+ src);  
				content = content.replace(img, newImg); 
			}
		}   
		return content; 
	}
	
	/**
	 * 分页查询项目信息
	 * @param page
	 * @param size 
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public List<Item> getItemByPage(Integer page, Integer size, String prePath, HttpServletRequest request){
		int startPage = (page-1)*size;
		List<Item> items = itemDao.getItemByPage(startPage, size);
		if(items != null){
			for(Item item : items){
				String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(item.getDescription()),prePath,request.getContextPath());
				item.setDescription(description);
			}
		}
		return items == null ? new ArrayList<Item>():items;
	}
	
	public Item get(Item entiy){
		Item item = super.get(entiy);
		if(item != null){
			List<ItemImage> images = itemDao.getImagesById(item.getId());
			if(images != null)
				item.setImages(images);
			List<String> shopsId = shopDao.getShopByItemId(item.getId());
			List<Shop> shops = new ArrayList<Shop>();
			if(shopsId != null && !shopsId.isEmpty()){
				for(String id : shopsId){
					Shop shop = shopDao.getShopById(id);
					shops.add(shop);
				}
			}
			item.setShops(shops);
		}
		return item == null ? new Item():item;
	}

	/**
	 * 查询项目详情
	 * @param id
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public Item getItemById(String id, String prePath, HttpServletRequest request) {
		Item item = itemDao.getItemById(id);
		if(item != null){
			String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(item.getDescription()),prePath,request.getContextPath());
			item.setDescription(description);
		}
		return item == null ? new Item():item;
	}

	public Page<Item> getItemList(Page<Item> page, Item item) {
		Page<Item> itemPage = super.findPage(page, item);
		List<Item> list = itemPage.getList();
		for(Item i : list){
			//查询滚动图
			List<ItemImage> image = itemDao.getImagesById(i.getId());
			if(image != null){
				i.setImages(image);
			}
			//分类id对应的分类名
			if(StringUtils.isNotBlank(i.getType())){
				ItemType type = itemTypeDao.get(i.getType());
				if(type != null && StringUtils.isNotBlank(type.getTypeName())){
					i.setTypeName(type.getTypeName());
				}
			}
		}
		return itemPage;
	}

	/**
	 * 删除商品的关联图片
	 * @param item
	 */
	public void deleteImages(Item item) {
		itemDao.deleteItemImages(item.getId());
	}

	public void inFrameFotosaveItem(Item item) {
		if(StringUtils.isNotBlank(item.getImagesLocation())){
			//拼接图片的地址到list
			String[] icp = item.getImagesLocation().split(",");
			List<ItemImage> images = new ArrayList<ItemImage>();
			for (int i = 0; i < icp.length; i++) {
				if(StringUtils.isNotBlank(icp[i])){
					ItemImage image = new ItemImage();
					image.setItemId(item.getId());
					image.setItemImage(icp[i]);
//					image.setApp(true);
//					image.setCurUserId("1");
					image.preInsert();
//					image.preUpdate();
					images.add(image);
				}
			}
			itemDao.insertItemImage(images);
		}
	}

}
